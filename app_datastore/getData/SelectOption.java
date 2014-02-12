/*****
送信するデータの選択を行いデータの取得、送信を行うクラスを呼び出す。
標準入出力はこのクラスしかできないようにする。後々めんどいから
selectデータのみ

 */

import java.io.*;
import CarDataSender.SendData;
import CarDataSender.GetData;

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
	    //System.out.println("3,server stop");
	    System.out.println("3,quit");


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


		CarDataSender.SendData data = new CarDataSender.SendData(getSelection(option));
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
			System.out.println("not correspond");

		    }
		}
	    }else if(getSelection(option).equals("select")){
		System.out.println("getting data now");
		System.out.println("if finish, input \"quit\"");


		CarDataSender.GetData data = new CarDataSender.GetData(getSelection(option));
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
	    /*case "3":
	    return "server_stop";
	    */
	case "3":
	    return "quit";
	default:
	    return null;
	}
	

    }





}
