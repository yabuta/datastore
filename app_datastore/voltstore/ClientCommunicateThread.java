import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Timestamp;

import postgresql.Create;
import postgresql.DbBase;
import postgresql.Insert;


public class ClientCommunicateThread extends Thread {
    private Socket sock = new Socket();


	public ClientCommunicateThread(Socket s)
	{
		sock = s;


	}
	
	
	@Override
	public void run()
	{
    	try {
            InputStream in = sock.getInputStream();
            BufferedReader readBuf = new BufferedReader(new InputStreamReader(in));
            BufferedWriter writeBuf = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            
            while(in.available() >= 0){
            	if(in.available() == 0) continue;
            	String data = readBuf.readLine();
            	System.out.println("ACCEPT: "+data);

            	writeBuf.write("accept");
            	writeBuf.newLine();
            	writeBuf.flush();

            	//終了要求を受信した場合ループを抜ける
            	if( !switcher(data) ) break;
            }
        }
    	catch (IOException e) {
        	e.printStackTrace();
        }
        finally{
        	//終了処理
        	try{
        		sleep(100);
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

	/**
	 * クライアントから送られてきた文字列に応じて処理を振り分ける
	 * @param str
	 * @return	true	接続続行
	 * 			false	接続終了
	 */
	private boolean switcher(String str)
	{
		//","で分割
    	String[] s = str.split(",");
    	
    	//命令番号を取得
    	int instNum = Integer.parseInt(s[0]);
    	
    	//接続終了要求の場合
    	if(instNum == ClientInstruction.INST_QUIT.getNum()){
    		return false;
    	}
    	//レコード追加要求の場合
    	else if(instNum == ClientInstruction.INST_INSERT.getNum()){
    		executeInsert(str);
    	}
    	//テーブル作成要求の場合
    	else if(instNum == ClientInstruction.INST_CREATE.getNum()){
    		executeTableCreate(str);
    	}
    	//テーブル削除要求の場合
    	else if(instNum == ClientInstruction.INST_DROP.getNum()){
    		executeTableDrop(str);
    	}
	else if(instNum == ClientInstruction.INST_STOP.getNum()){
	    return false;
	}
    	//登録されているもの以外
    	else{
    		System.err.println("命令エラー： " + str);
    	}
    	
    	return true;
 	}
	
	
	/**
	 * レコード追加要求の場合
	 * @param inst
	 */
	private void executeInsert(String inst)
	{


	    //System.out.println("inst=" + inst);
	    //String[] s = inst.split("/");
	    //System.out.println("split=" + s[0]);
	    System.out.println(inst);

	    String temp = inst.substring(2);
	    String[] temp2 = temp.split("table=",0);
	    String table = temp2[1].split("column=",0)[0];
	    String column = temp2[1].split("column=",0)[1].split("values=",0)[0];
	    String values = temp2[1].split("column=",0)[1].split("values=",0)[1];
	    String sql = table + "(" + column +  ")" + " VALUES(" + values + ");";


	    System.out.println(sql);

	    Insert.insertToTable(sql);
	

	    //voltdbにもjdbcを使ってデータを格納する。
	    //voltjdbc jd = new voltjdbc();
	    //jd.jdbc(tb);
	    
	    //VoltDBにもデータを送る

	    
	    //String[] volt_data = s[1].split(",");

	    voltStore volst = new voltStore();
		
		
	    try{
		volst.runStore(table,column,values);
	    }catch(Exception e){
		System.out.println("voltStore call miss");
		e.printStackTrace();
	    }

		/*
		org.voltdb.client.Client myApp;

		myApp=ClientFactory.createClient();
		try{
		    myApp.createConnection("localhost");
		}catch(IOException e){

		}

		//voltdbに挿入する
		try{
		    myApp.callProcedure("Insert",Timestamp.valueOf(volt_data[0]),Double.parseDouble(volt_data[1]),Double.parseDouble(volt_data[2]),Double.parseDouble(volt_data[3]));
		    }catch(ProcCallException e){
		      System.out.println("私です");
		}catch(IOException e){
		}*/

	}
	
	/**
	 * テーブル作成要求の場合
	 * @param inst	受信した文字列の配列
	 * 			
	 * 	受信文字列の形式
	 * 		0,tableName (tm time,elem1 type1,elem2 type2)
	 */
	private void executeTableCreate(String inst)
	{
		//テーブルデータを取り出す
		String tb = inst.substring(2);
		Create.createTable(tb);
	}
	
	/**
	 * テーブル削除要求の場合
	 * @param inst
	 */
	private void executeTableDrop(String inst)
	{
		String tb = inst.substring(2);
		tb = "DROP TABLE " + tb;
		DbBase.getInstance().executeQuery(tb);
	}



}
