/***
データの取得と送信クラスの呼び出しを行う


 */

public class SendData extends Thread{

    private static final String SERVER_STOP = "server_stop";
    private static final String STOP_REQUEST = "5,stop_server";
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
	
	//標準入力からquitが入力されたときループを抜ける。
	//標準入力への入力はSendServerクラスで行い、値がこのクラスのfinishに入力される。
	//selectがサーバ停止命令の場合、サーバ停止用の目入れを送る


	if(select.equals(SERVER_STOP)){

	    ConnectServer con = new ConnectServer(stop_port);

	    if(con.send(STOP_REQUEST));
	

	}else{

	    ConnectServer con = new ConnectServer(port);


	    
	    while(!finish){

		//test用。出力がぐちゃっとなるからお勧めしない
		//System.out.println("senddata now!");
	    

		//取得したデータを送信クラスに送る
		if(!con.send(sampledata)) break;
	    

		try{
		    sleep(1000);
		}catch(InterruptedException e){
		    finish = true;

		}
		
	    }

	    con.send("0,test");

	    con.finish();
	}


    }
    

    //SendServerクラスから終了命令が出た場合に
    public synchronized void finish(){

	finish = true;

    }



}
