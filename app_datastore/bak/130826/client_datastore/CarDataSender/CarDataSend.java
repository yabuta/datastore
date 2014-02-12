/**

車のデータを整形して送信用のクラスに送る。
また、テーブル

 */
package CarDataSender;

import CarDataSender.SendDatabase.DbBase;

public class CarDataSend{

    private String DATABASE = "Car_Data";
    public static final String INSERT_SUCCESS = "insert_success";


    //すべてを送信する場合はこの変数を列とする
    private static final String ALL = "tm,CarSpeed,AccelQuantity,BrakeQuantity,BrakeSwitch,FrontWheelSpeed,BackWheelSpeed,ShiftPosition,Battery,DischargeAndChargeElectric,MaxDischargeElectric,ChargeLevel,BatteryElectric,EngineSpeed,EVmodeStatus,CoolantTemparature,LightTemprature,RemainingQuantityGasoline,DoorStatus,CruiseControlStatus,SteeringStatus";


    //portは固定
    //private static final int PORT = 5555;

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

    public boolean init(String url,String databaseName){

	//if((con = new ConnectServer(url,PORT)) != null) return false;
	if(!DbBase.getInstance().connect()) return false;
	DATABASE = databaseName;

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

    public String SendData(String sql,String dataType,String data){


	//クエリを作成して送信クラスを呼び出す
	String message = "";
	String DataType = "";

	switch (sql) {

	    case "INSERT":

		/*Insertクエリの作成INSERT INTO + (dataType) + (data);
		  dataTypeとdataはそれぞれ,で区切られている。

		 */

		if(dataType.equals("")||data.equals("")) return null;
		//Insertは最初に1をつける
		//String message = "";//"1,";

		message = message + "INSERT INTO ";
		
		if(dataType.equals("ALL")){

		    String[] dt = ALL.split(",");
		    String[] sd = data.split(",");
		    if(dt.length != sd.length){
			return null;
		    }

		    message = message + DATABASE + "(" + ALL + ")" + " VALUES(";

		}else{

		    //dataTypeの要素の数とdataの要素が合わなかったらfalse
		    String[] dt = dataType.split(",");
		    String[] sd = data.split(",");
		    if(dt.length != sd.length){
			return null;
		    }

		    message = message + DATABASE + "(" + dataType + ")" + " VALUES(";

		}

		message = message + data + ");";
		//System.out.println(message);

		DbBase.getInstance().InsertQuery(message);

		//con.send(message);

		return INSERT_SUCCESS;

	    case "SELECT":

		/*selectクエリを作成する
		  SELECT + (dataType) + FROM DATABASE;

		 */
		if(dataType.equals("")) return null;

		if(dataType.equals("ALL")){
		    message = message + "SELECT " + ALL + " FROM " + DATABASE + ";";
		}else{
		    message = message + "SELECT " + dataType + " FROM " + DATABASE + ";";
		}

		
		return DbBase.getInstance().SelectQuery(message,dataType);

	    default:
		return null;
		
	    }
	
	//return false;
	
    }

    public String SendQuery(String sql){


	String dataType = "";
	//InsertかSelectかによって場合分けをし、別のメソッドを呼び出す
	switch (sql.split(" ")[0]) {
	case "INSERT":
	    DbBase.getInstance().InsertQuery(sql);
	    return INSERT_SUCCESS;
	case "insert":
	    DbBase.getInstance().InsertQuery(sql);
	    return INSERT_SUCCESS;
	case "SELECT":
	    //Selectする列を抽出する
	    dataType = sql.split(" ")[1];
	    return DbBase.getInstance().SelectQuery(sql,dataType);
	case "select":
	    //上に同じ
	    dataType = sql.split(" ")[1];
	    return DbBase.getInstance().SelectQuery(sql,dataType);
	default:
	    return null;

	}


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



