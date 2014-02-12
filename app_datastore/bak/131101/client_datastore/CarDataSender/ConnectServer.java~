package CarDataSender;


import java.net.*;
import java.io.*;


public class ConnectServer{


    //private final int port = 5555;
    //private final int stop_server = 5556;
    //private String host = "db1.ertl.jp";
    
    private Socket socket;
    private BufferedWriter osStr;
    private InputStream is;
    private BufferedReader irStr;
    

    private boolean loop = true;

    //送信するSQLを格納しておく
    //private String senddata = "";


    public ConnectServer(String url,int port){

	try{
	    //socketの初期化

	    socket = new Socket(url,port);	    

	}catch(UnknownHostException e){

	}catch(IOException e){

	}

    }


    //finishが呼ばれるまで回っているだけ。こんな実装でいいんだろうか
    public boolean send(String sql){


	if(socket == null){
	    return false;
	}

	try{


	    //出力ストリームを取得
	    OutputStreamWriter osBuf = new OutputStreamWriter(socket.getOutputStream());
	    osStr = new BufferedWriter(osBuf);

	    //入力ストリームを取得
	    is = socket.getInputStream();
	    irStr = new BufferedReader(new InputStreamReader(is));
	
	    //System.out.println("before datasend");

	    osStr.write(sql);
	    osStr.newLine();
	    osStr.flush();

	    //System.out.println("after senddata");

	    //返信が来るまで回ってる
	    while(is.available() == 0);

	    //読み込んだデータをdataに格納する
	    while(is.available() != 0){
		//byte[] data = new byte[is.available()];
		//irStr.read(data, 0, is.available());
		String data = irStr.readLine();

		//System.out.print(data);

	    }
	    //System.out.println("");
	    


	}catch(UnknownHostException e){
	    return false;
	}catch(IOException e){
	    return false;
	}

	return true;

    }


    public void finish(){

	try{
	    osStr.close();
	    irStr.close();
	
	    socket.close();
	}catch(IOException e){

	}finally{
	    //loop = false;
	}

    }

}
