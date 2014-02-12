package stopServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Timestamp;

//import postgresql.Create;
//import postgresql.DbBase;
//import postgresql.Insert;


public class StopServer{
    private Socket sock = new Socket();
    
    
    public StopServer(Socket s)
    {
	sock = s;
	

	
    }
    
    
    public void stop()
    {
	try {
	    InputStream in = sock.getInputStream();
	    BufferedReader readBuf = new BufferedReader(new InputStreamReader(in));
	    BufferedWriter writeBuf = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            
            while(in.available() >= 0){
            	if(in.available() == 0) continue;
            	String str = readBuf.readLine();
            	//System.out.println("Receive finish reques");

            	writeBuf.write("accept");
            	writeBuf.newLine();
            	writeBuf.flush();

		//","で分割
		String[] s = str.split(",");
		
		//命令番号を取得
		int instNum = Integer.parseInt(s[0]);
    	
		//接続終了要求の場合
		if(instNum == ClientInstruction.INST_STOP.getNum()){
		    break;

		}
            }
        }
    	catch (IOException e) {
        	e.printStackTrace();
        }
        finally{
        	//終了処理
        	try{
        		Thread.sleep(100);
	        	if(sock.isConnected()){
	        		sock.close();
	        		System.out.println("接続終了");
	        		sock = null;
	        	}
        	}
        	catch(IOException e){
        		e.printStackTrace();
        	}
        	catch(InterruptedException e){
        		e.printStackTrace();
        	}

        }

    }
    
	
}
