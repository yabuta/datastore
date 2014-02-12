import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ClientAcceptThread extends Thread {
    private static final int CONNECTION_MAX  = 8; //最大接続数	
    private int portNumber; //接続待ちポート
    private ClientCommunicateThread cct;

    //ループ用。サーバ停止の時にfalseになる
    private boolean loop = true;
    
    public ClientAcceptThread(int port)
    {
	portNumber = port;
    }
			
	
    @Override
    public void run()
    {
	try{
	    //サーバーソケットの作成
	    ServerSocket svsock = new ServerSocket(portNumber, CONNECTION_MAX);
	    while(loop){
		try{
		    //受信待ち
		    Socket sock = new Socket();
		    sock = svsock.accept();
		    System.out.println("接続受信");
		    
		    //繋がったら通信用スレッドに処理を投げる
		    cct = new ClientCommunicateThread(sock);
		    cct.start();
		}
		catch(IOException e){
		    e.printStackTrace();
		}
	    }
	    for(int i= 0; i<CONNECTION_MAX ;i++){


	    }
	}
	catch(IOException e){
	    e.printStackTrace();
	}
    }


    //親スレッドからサーバ終了時に呼び出される
    //ループを停止してスレッドを終了する
    public void finish(){
	loop = false;


    }
	
}
