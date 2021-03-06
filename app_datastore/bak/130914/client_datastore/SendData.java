/***
データの取得と送信クラスの呼び出しを行う


 */
//package CarDataSender;

import CarDataSender.CarDataSend;
import java.sql.Timestamp;
//import java.util.Date;

public class SendData extends Thread{

    private static final String SERVER_STOP = "server_stop";
    private static final String STOP_REQUEST = "4,stop_server";
    private static final String SERVER_URL = "db1.ertl.jp";
    private static final int port = 5555;
    private static final int stop_port = 5556;

    private String select = "";
    private boolean finish = false;

    private String sampledata = "1,android_data_store(tm,xlocation,ylocation,velocity) VALUES ('2013-8-1 12:31:50',35.15507021,136.9667089,0.0);";



    public SendData(String select){
	
	//selectの値によって何のデータを送るかを選択する。
	this.select=select;

    }

    
    public void run(){
	
	/*標準入力からquitが入力されたときループを抜ける。
	標準入力への入力はSendServerクラスで行い、値がこのクラスのfinishに入力される。
	
	selectがサーバ停止命令の場合、サーバ停止用の目入れを送る
	まだ、実験段階。
	*/

	if(select.equals(SERVER_STOP)){


	    //いったん凍結
	    //ConnectServer con = new ConnectServer(stop_port);

	    //if(con.send(STOP_REQUEST));
	    //con.send("0,test");
	    //con.finish();
	

	}else{

	    /**
	       CarDataSendクラスの使い方
	       インスタンス作成の時に接続するサーバのURLを渡す（ポートは5555固定）
	       SendQueryに要求を送る。（命令、格納する列、データ × n）
	       最後にfinishをしてサーバとの接続を切る

	     */
	    
	    CarDataSender.CarDataSend car = new CarDataSender.CarDataSend();
	    if(car.init(SERVER_URL)){

	    
		while(!finish){
		    
		    //test用。出力がぐちゃっとなるからお勧めしない
		    //System.out.println("senddata now!");
	    

		    //取得したデータを送信クラスに送る
		    String InsertDataType = "tm,CarSpeed,Battery";
		    Timestamp time = new Timestamp(System.currentTimeMillis());
		    System.out.println(time.toString());
		    //String StTime = time.toString().split("\\.")[0];
		    String InsertData =  "'" + time.toString() + "'" + ",300,200";
		    //System.out.println(InsertData);


		    if(!car.SendData("INSERT",InsertDataType,InsertData).equals(CarDataSend.INSERT_SUCCESS)) break;
	    

		    try{
			sleep(1000);
		    }catch(InterruptedException e){
			finish = true;
			
		    }
		
		}
		
		car.finish();
	    }else{
		System.out.println("database access failed");
	    }
	}

    }
    

    //SendServerクラスから終了命令が出た場合に
    public synchronized void finish(){

	finish = true;

    }



}
