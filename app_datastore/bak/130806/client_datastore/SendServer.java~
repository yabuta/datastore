/*****
送信するデータの選択を行いデータの取得、送信を行うクラスを呼び出す。
標準入出力はこのクラスしかできないようにする。後々めんどいから


 */

import java.io.*;


public class SendServer{


    public SendServer(){

    }



    public void selectdata(){


	while(true){
	    //選択肢の中から送信するデータを選択する    
	    System.out.println("select your request following selection");
	    System.out.println("1,time");
	    System.out.println("2,sample data");
	    System.out.println("3,server stop");
	    System.out.println("4,quit");


	    BufferedReader r = new BufferedReader(new InputStreamReader(System.in), 1);
	    System.out.print(">");
	    System.out.flush();
	    String select;
	    try{
	    select = r.readLine();
	    }catch(IOException e){
		return;
	    }
	    System.out.println(select);

	    //入力されたデータの源泉な調査を行う
	    //選ばれた入力のみがデータを送信する権利を得るのだ
	    if(getSelection(select) == null){
		System.out.println("select right selection");

	    }else if(getSelection(select).equals("quit")){
		System.out.println("finish selection");
		break;

	    }else{

		System.out.println("sending data is started");
		System.out.println("if finish, input \"quit\"");


		SendData data = new SendData(getSelection(select));
		//データを取得して送信する処理
		/********************************/
		data.start();


		/********************************/


		//quitが入力されたら終了する
		while(true){
		    //System.out.print(">");
		    //System.out.flush();
		    
		    String quit;
		    try{
			quit = r.readLine();
		    }catch(IOException e){
			return;
		    }

		    if(quit.equals("quit")){
		
			data.finish();
			System.out.println("finish to send data.");
			break;
		    }else{
			System.out.println("not correspond");

		    }
		}
	    }

	}



    }


    //selectの値から処理を抽出する。numとか使って管理したほうがいいかも
    private String getSelection(String num){


	//selectに沿った返信を行う

	switch (num) {

	case "1":
	    return "time";
	case "2":
	    return "sample";
	case "3":
	    return "server_stop";
	case "4":
	    return "quit";
	default:
	    return null;
	}
	

    }





}
