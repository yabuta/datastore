/**

車のデータを整形して送信用のクラスに送る。
また、テーブル

 */
package CarDataSender;

import SendPostgres.DbBase;

public class CarDataSend{
    private static final String DATABASE = "Car_Data";


    //すべてを送信する場合はこの変数を列とする
    private static final String ALL = "tm,CarSpeed,AccelQuantity,BrakeQuantity,BrakeSwitch,FrontWheelSpeed,BackWheelSpeed,ShiftPosition,Battery,DischargeAndChargeElectric,MaxDischargeElectric,ChargeLevel,BatteryElectric,EngineSpeed,EVmodeStatus,CoolantTemparature,LightTemprature,RemainingQuantityGasoline,DoorStatus,CruiseControlStatus,SteeringStatus";


    //portは固定
    private static final int PORT = 5555;
    private String DataType = "";
    private ConnectServer con;
    private DbBase db;

    //private Socket socket = null;

    public CarDataSend(){

    }

    public boolean init(String url){

	//if((con = new ConnectServer(url,PORT)) != null) return false;
	if(!DbBase.getInstance().connect()) return false;

	return true;

    }



    //受け取ったクエリを整形して送信クラスに渡す
    /*
      sql       :INSERT,SELECTのどちらか
      dataType  :(data1,data2,...)と挿入するデータの列
      data      :挿入するデータ

      データの送信はjdbcを使って直接データベースに送っている。
      データベースにあるサーバプログラムに指定した形式のデータを送信して
      データベースを使うこともできるが手間がかかるのでいったん凍結。使っていない

     */

    public boolean SendQuery(String sql,String dataType,String data){


	//クエリを作成して送信クラスを呼び出す
	String message = "";

	switch (sql) {

	    case "INSERT":



		//Insertは最初に1をつける
		//String message = "";//"1,";

		message = message + "INSERT INTO ";
		
		if(dataType.equals("ALL")){
		    message = message + DATABASE + "(" + ALL + ")" + " VALUES(";

		}else{
		    message = message + DATABASE + "(" + dataType + ")" + " VALUES(";
		
		    //dataTypeの要素の数とdataの要素が合わなかったらfalse
		    String[] dt = dataType.split(",");
		    String[] sd = data.split(",");
		    if(dt.length != sd.length){
			return false;
		    }

		    /*for(int i = 0;i<data.length;i++){
			if(i == (data.length-1)){
			    message = message + data[i];
			}else{
			    message = message + data[i] + ",";
			}
			}*/
		    message = message + data + ")";
		    message = message + ");";
		}

		//test用
		//System.out.println("message = " + message);
		//message = "1,android_data_store(tm,xlocation,ylocation,velocity) VALUES ('2013-8-1 12:31:50',35.15507021,136.9667089,0.0);";


		//System.out.println("message = " + message);
		DbBase.getInstance().InsertQuery(message);


		//con.send(message);
		

		return true;
	    case "SELECT":
		if(dataType.equals("ALL")){
		    message = message + "SELECT " + ALL + " FROM " + "DATABASE;";
		}else{
		    message = message + "SELECT " + dataType + " FROM " + "DATABASE";
		}
		DbBase.getInstance().SelectQuery(message,dataType);

		return true;
	    default:
		return false;
		
	    }
	
	//return false;
	
    }


    /*    
    public void setCarSpeed;
    public void setAccelQuantity;
    public void setBrakeQuantity;
    public void setBrakeSwitch;
    public void setFrontWheelSpeed;
    public void setBackWheelSpeed;
    public void setShiftPosition;
    public void setBattery;
    public void setDischargeAndChargeElectric;
    public void setMaxDischargeElectric;
    public void setChargeLevel;
    public void setBatteryElectric;
    public void setEngineSpeed;
    public void setEVmodeStatus;
    public void setCoolantTemparature;
    public void setLightTemprature;
    public void setRemainingQuantityGasoline;
    public void setDoorStatus;
    public void setCruiseControlStatus;
    public void setSteeringStatus;


    */


    public boolean finish(){

	
	DbBase.getInstance().disConnect();

	//con.send("0,test");
	//con.finish();

	return true;

    }




}



