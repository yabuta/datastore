import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ClientAcceptThread extends Thread {
	private static final int CONNECTION_MAX  = 8; //最大接続数	
	private int portNumber; //接続待ちポート
	
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
			while(true){
				try{
					//受信待ち
			        Socket sock = new Socket();
			        sock = svsock.accept();
			        System.out.println("接続受信");
			        
			        //繋がったら通信用スレッドに処理を投げる
			        ClientCommunicateThread cct = new ClientCommunicateThread(sock);
					cct.run();
				}
				catch(IOException e){
					e.printStackTrace();
				}
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
}
