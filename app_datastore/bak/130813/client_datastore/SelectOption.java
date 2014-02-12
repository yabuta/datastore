/*****
送信するデータの選択を行いデータの取得、送信を行うクラスを呼び出す。
標準入出力はこのクラスしかできないようにする。後々めんどいから
selectデータのみ

 */

import java.io.*;
//import CarDataSender.SendData;
//import CarDataSender.GetData;
//import CarDataSender.GetPositionData;

public class SelectOption{


    public SelectOption(){

    }



    public void selectdata(){


	while(true){
	    //選択肢の中から送信するデータを選択する    
	    System.out.println("select your request following option by number");
	    //System.out.println("1,time");
	    System.out.println("1,sample data insert");
	    System.out.println("2,sample data select");
	    System.out.println("3,get Position data");
	    System.out.println("4,quit");


	    BufferedReader r = new BufferedReader(new InputStreamReader(System.in), 1);
	    System.out.print(">");
	    System.out.flush();
	    String option;
	    try{
	    option = r.readLine();
	    }catch(IOException e){
		return;
	    }
	    //System.out.println(option);

	    //入力されたデータの源泉な調査を行う
	    //選ばれた入力のみがデータを送信する権利を得るのだ
	    if(getSelection(option) == null){
		System.out.println("select exact option");

	    }else if(getSelection(option).equals("quit")){
		System.out.println("finish selection");
		break;

	    }else if(getSelection(option).equals("insert")){

		System.out.println("sending data is started");
		System.out.println("if finish, input \"quit\"");


		SendData data = new SendData(getSelection(option));
		//データを取得して送信する処理
		/********************************/
		data.start();


		/********************************/


		//quitが入力されたら終了する
		while(true){
		    System.out.print(">");
		    System.out.flush();
		    
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
			System.out.println("not correspond.");

		    }
		}
	    }else if(getSelection(option).equals("select")){

		String server_name = "";
		/*System.out.println("Server Select.");
		System.out.println("1,db1.ertl.jp");
		System.out.println("2,other");
		System.out.println("3,quit");
		*/
		/*String server_option = "";
		try{
		    server_option = r.readLine();
		}catch(IOException e){

		}
		if(server_option.split("\\,")[0].equals("1")){
		    server_name = "db1.ertl.jp";

		}else{// if(server_option.split("\\,")[0].equals("2")){

		    System.out.println("input server name.");
		    System.out.println(">");
		    System.out.flush();

		    try{
			server_name = r.readLine();
		    }catch(IOException e){
			

		    }
		    }*/


		//!をつける
		if(server_name.equals("")){
		    System.out.println("getting data now");
		    //System.out.println("if finish, input \"quit\"");


		    GetData data = new GetData(getSelection(option));
		    //データを取得して送信する処理
		    /********************************/
		    data.start();
		    try{
			data.join();
		    }catch(InterruptedException e){
			

		    }
		    /********************************/
		}
	    }else if(getSelection(option).equals("Position")){

		String server_name = "";

		System.out.println("getting data now");
		//System.out.println("if finish, input \"quit\"");


		GetPositionData data = new GetPositionData();
		//データを取得して送信する処理
		/********************************/
		data.start();
		try{
		    data.join();
		}catch(InterruptedException e){
		    

		}
		/********************************/
		
		
	    }

	}



    }


    //selectの値から処理を抽出する。numとか使って管理したほうがいいかも
    private String getSelection(String num){


	//selectに沿った返信を行う

	switch (num) {

	case "1":
	    return "insert";
	case "2":
	    return "select";
	case "3":
	    return "Position";	    
	case "4":
	    return "quit";
	default:
	    return null;
	}
	

    }





}
