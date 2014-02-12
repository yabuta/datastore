#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <string.h>
#include <sys/fcntl.h>
#include <unistd.h>
#include <time.h>
#include <string>
#include <sys/time.h>
#include <stdlib.h>

void http_client(int sock){

  char senddata[1000];
  char recvdata[20];
  char time_n[30];

  int i,n;
  struct timeval start_timeval,end_timeval;
  double duration;

  for(i=0;i<100;i++){

    gettimeofday(&start_timeval,NULL);
    
    time_t now = time(NULL);
    struct tm *pnow = localtime(&now);    
    sprintf(time_n,"%2d-%02d-%02d %02d:%02d:%02d", pnow->tm_year+1900, pnow->tm_mon + 1, pnow->tm_mday, pnow->tm_hour, pnow->tm_min, pnow->tm_sec);
    
    memset(senddata, 0, sizeof(senddata));
    snprintf(senddata, sizeof(senddata),"1,android_data_store(tm,xlocation,ylocation,velocity) VALUES('");
    strcat(senddata,time_n);
    strcat(senddata,"',100,100,100);/\n");
    
    //printf("%s\n",senddata);


    n = send(sock, senddata, (int)strlen(senddata),0);
    if (n < 0) {
      perror("write");
      exit(-1);
    }
    
    //while (n > 0) {
    memset(recvdata, 0, sizeof(recvdata));
    n = recv(sock, recvdata, sizeof(recvdata),0);
    if (n < 0) {
      perror("read");
      exit(-1);
    }

    gettimeofday(&end_timeval,NULL);
    
    duration = (double)(end_timeval.tv_usec - start_timeval.tv_usec);
    //printf("%f,%f\n", (double)end_timeval.tv_usec, (double)start_timeval.tv_usec);
    printf("%f\n",duration);
    
    //usleep(1000*1000);
    //}
  }


  memset(senddata, 0, sizeof(senddata));
  memset(recvdata, 0, sizeof(recvdata));
  snprintf(senddata, sizeof(senddata),"0,exit\n");

  n = send(sock,senddata,(int)strlen(senddata),0);
  n = recv(sock,recvdata,sizeof(recvdata),0);



}


int main(int argc, char *argv[])
{

  struct sockaddr_in server;
  struct timeval start_time,end_time,mud_time;
  int sock;
  char deststr[80] = "db1.ertl.jp";
  unsigned int **addrptr;

  gettimeofday(&start_time,NULL);

  sock = socket(AF_INET, SOCK_STREAM, 0);
  if (sock < 0) {
    perror("socket");
    return 1;
  }

  server.sin_family = AF_INET;
  server.sin_port = htons(5555); /* HTTPのポートは80番です */

  server.sin_addr.s_addr = inet_addr(deststr);
  if (server.sin_addr.s_addr == 0xffffffff) {
    struct hostent *host;

    host = gethostbyname(deststr);
    if (host == NULL) {
      if (h_errno == HOST_NOT_FOUND) {
	/* h_errnoはexternで宣言されています */
	printf("host not found : %s\n", deststr);
      } else {
	/*
	  HOST_NOT_FOUNDだけ特別扱いする必要はないですが、
	  とりあえず例として分けてみました
	*/
	printf("%s : %s\n", hstrerror(h_errno), deststr);
      }
      return 1;
    }

    addrptr = (unsigned int **)host->h_addr_list;

    while (*addrptr != NULL) {
      server.sin_addr.s_addr = *(*addrptr);

      /* connect()が成功したらloopを抜けます */
      if (connect(sock,
		  (struct sockaddr *)&server,
		  sizeof(server)) == 0) {
	break;
      }

      addrptr++;
      /* connectが失敗したら次のアドレスで試します */
    }

    /* connectが全て失敗した場合 */
    if (*addrptr == NULL) {
      perror("connect");
      return 1;
    }
  } else {
    if (connect(sock,
		(struct sockaddr *)&server, sizeof(server)) != 0) {
      perror("connect");
      return 1;
    }
  }

  gettimeofday(&mud_time,NULL);

  http_client(sock);

  close(sock);

  gettimeofday(&end_time,NULL);

  printf("%f\n",(double)(mud_time.tv_usec-start_time.tv_usec));
  printf("%f\n",(double)(end_time.tv_usec-start_time.tv_usec));


  return 0;
}

