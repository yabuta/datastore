/* $Id: http-client.c,v 1.6 2013/01/23 06:57:19 68user Exp $ */

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <netinet/in.h>
#include <sys/param.h>
#include <sys/uio.h>
#include <unistd.h>

#define BUF_LEN 256                      /* �Хåե��Υ����� */

int sendData(char *sql){
    int s;                               /* �����åȤΤ���Υե�����ǥ�������ץ� */
    struct hostent *servhost;            /* �ۥ���̾�� IP ���ɥ쥹�򰷤�����ι�¤�� */
    struct sockaddr_in server;           /* �����åȤ򰷤�����ι�¤�� */
    struct servent *service;             /* �����ӥ� (http �ʤ�) �򰷤�����ι�¤�� */

    char send_buf[BUF_LEN];              /* �����Ф����� HTTP �ץ�ȥ����ѥХåե� */
    char host[BUF_LEN] = "db1.ertl.jp";    /* ��³����ۥ���̾ */
    char path[BUF_LEN] = "/tomcat/poSQL/exeSQL";            /* �׵᤹��ѥ� */
    unsigned short port = 80;            /* ��³����ݡ����ֹ� */


    /*
    if ( argc > 1 ){                     /* URL�����ꤵ��Ƥ����� 
        char host_path[BUF_LEN];

        if ( strlen(argv[1]) > BUF_LEN-1 ){
            fprintf(stderr, "URL ��Ĺ�����ޤ���\n");
            return 1;
        }
	// http:// ����Ϥޤ�ʸ����� 
	// sscanf ���������� 
	// http:// �θ�˲���ʸ����¸�ߤ���ʤ� 
        if ( strstr(argv[1], "http://") &&
             sscanf(argv[1], "http://%s", host_path) &&
             strcmp(argv[1], "http://" ) ){
            char *p;

            p = strchr(host_path, '/');  // �ۥ��Ȥȥѥ��ζ��ڤ� "/" ��Ĵ�٤� 
            if ( p != NULL ){
	      strcpy(path, p);        // "/"�ʹߤ�ʸ����� path �˥��ԡ� 
                *p = '\0';
                strcpy(host, host_path); // "/"�������ʸ����� host �˥��ԡ� 
		} else {                     // "/"���ʤ��ʤ��http://host �Ȥ��������ʤ� 
	      strcpy(host, host_path); // ʸ�������Τ� host �˥��ԡ� 
            }

            p = strchr(host, ':');       // �ۥ���̾����ʬ�� ":" ���ޤޤ�Ƥ�����
            if ( p != NULL ){
	      port = atoi(p+1);        // �ݡ����ֹ����� 
	      if ( port <= 0 ){        // �����Ǥʤ� (atoi ������) ����0 ���ä��� 
		port = 80;           // �ݡ����ֹ�� 80 �˷���Ǥ� 
                }
                *p = '\0';
            }
        } else {
            fprintf(stderr, "URL �� http://host/path �η����ǻ��ꤷ�Ƥ���������\n");
            return 1;
        }
    }
    */

    printf("http://%s%s ��������ޤ���\n\n", host, path);

                                /* �ۥ��Ȥξ���(IP���ɥ쥹�ʤ�)����� */
    servhost = gethostbyname(host);
    if ( servhost == NULL ){
        fprintf(stderr, "[%s] ���� IP ���ɥ쥹�ؤ��Ѵ��˼��Ԥ��ޤ�����\n", host);
        return 0;
    }

    bzero(&server, sizeof(server));            /* ��¤�Τ򥼥��ꥢ */

    server.sin_family = AF_INET;

                                               /* IP���ɥ쥹�򼨤���¤�Τ򥳥ԡ� */
    bcopy(servhost->h_addr, &server.sin_addr, servhost->h_length);

    if ( port != 0 ){                          /* �����ǥݡ����ֹ椬���ꤵ��Ƥ����� */
        server.sin_port = htons(port);
    } else {                                   /* �����Ǥʤ��ʤ� getservbyname �ǥݡ����ֹ����� */
        service = getservbyname("http", "tcp");
        if ( service != NULL ){                /* ����������ݡ����ֹ�򥳥ԡ� */
            server.sin_port = service->s_port;
        } else {                               /* ���Ԥ����� 80 �֤˷���Ǥ� */
            server.sin_port = htons(80);
        }
    }
                                /* �����å����� */
    if ( ( s = socket(AF_INET, SOCK_STREAM, 0) ) < 0 ){
        fprintf(stderr, "�����åȤ������˼��Ԥ��ޤ�����\n");
        return 1;
    }
                                /* �����Ф���³ */
    if ( connect(s, (struct sockaddr *)&server, sizeof(server)) == -1 ){
        fprintf(stderr, "connect �˼��Ԥ��ޤ�����\n");
        return 1;
    }

                                /* HTTP �ץ�ȥ������� & �����Ф����� */
    sprintf(send_buf, "POST %s HTTP/1.0\r\n", path);
    write(s, send_buf, strlen(send_buf));
    printf("%s",send_buf);

    sprintf(send_buf, "Content-Length: %d\r\n", strlen(sql) + 4);
    write(s, send_buf, strlen(send_buf));
    printf("%s",send_buf);

    sprintf(send_buf, "\r\n");
    write(s, send_buf, strlen(send_buf));
    printf("%s",send_buf);

    sprintf(send_buf, "sql=%s\r\n", sql);
    write(s, send_buf, strlen(send_buf));
    printf("%s",send_buf);

    /*
    sprintf(send_buf, "\r\n");
    write(s, send_buf, strlen(send_buf));
    printf("%s",send_buf);
    */

                                /* ���Ȥϼ������ơ�ɽ��������� */
    while (1){
        char buf[BUF_LEN];
        int read_size;
        read_size = read(s, buf, BUF_LEN);
        if ( read_size > 0 ){
            write(1, buf, read_size);
        } else {
            break;
        }
    }
                                /* ����� */
    close(s);

}


int main(int argc, char *argv[]){


  char sql_buf[256] = "select * from test_data;";

  if(argc == 2){
    sprintf(sql_buf,"",argv[1]);

  }

  printf("sql=%s\n",sql_buf);

  sendData(sql_buf);

  return 0;
}
