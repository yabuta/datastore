import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import postgresql.DbBase;
import postgresql.Insert;

public class ServerMain {
	private static final int CONNECTION_PORT = 5555;
	private static ClientAcceptThread cat;
	
    /**
     * @param args
     */
    public static void main(String[] args)
    {
    	//データベースに接続できなかったら終了
    	if(!DbBase.getInstance().connect()) return;
    	
    	//クライアントからの接続を受信するスレッドの生成
    	createAcceptThread();

    	
    	//データベースとの接続を終了
    	DbBase.getInstance().disConnect();
    }

    
    /**
     * クライアントからの接続を受け付けるスレッドを生成
     */
    private static void createAcceptThread()
    {
    	cat = new ClientAcceptThread(CONNECTION_PORT);
    	cat.run();
    }
    
    private static void stopAcceptThread()
    {
    }
    
    
    private static void serv()
    {
    	int port;
    	try {
	        port = 5555;
	
	        ServerSocket svsock = new ServerSocket(port);	
            Socket sock = svsock.accept();
            System.out.println("OK");
            InputStream in = sock.getInputStream();
            BufferedReader readBuf = new BufferedReader(new InputStreamReader(in));
            BufferedWriter writeBuf = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            
            while(in.available() >= 0){
            	if(in.available() == 0) continue;
            	System.out.println("acc");
            	String data = "";
            	data = readBuf.readLine();
            	System.out.println("accept"+data);
            		
            	writeBuf.write("accept");
            	writeBuf.newLine();
            	writeBuf.flush();
            	System.out.println("send");

            	String[] s = data.split(",");
            	Insert.insertToTest(s[0], s[1]);
            	
            	
            }
            
            
        } catch (IOException e) {
        }
        finally{
        	System.out.println("End");
        }
        
    }
    
    
    
}
