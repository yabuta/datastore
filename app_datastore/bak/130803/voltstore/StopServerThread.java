/*
サーバを停止する命令を受け取るクラス。
クライアントと接続するクラスとほぼ同じ
*/


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class StopServerThread extends Thread {
    private static final int CONNECTION_MAX  = 1; //最大接続数	
    private int portNumber; //接続待ちポート
    
    public StopServerThread(int port)
    {
	portNumber = port;
    }
			
	
    @Override
    public void run()
    {
	try{
	    //サーバーソケットの作成
	    ServerSocket svsock = new ServerSocket(portNumber, CONNECTION_MAX);	
	    try{
		//受信待ち
		Socket sock = new Socket();
		sock = svsock.accept();
		System.out.println("接続受信");
		
		//繋がったら通信クラスに処理を投げる
		StopServer cct = new StopServer(sock);
		cct.stop();
	    }
	    catch(IOException e){
		e.printStackTrace();
	    }
	}
	catch(IOException e){
	    e.printStackTrace();
	}
    }
    
}
