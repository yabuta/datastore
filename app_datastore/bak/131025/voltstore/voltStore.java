import org.voltdb.*;
import org.voltdb.client.*;
import java.sql.Timestamp;
import java.util.Date;
import procedure.*;


public class voltStore{

    private static int n=5;

    public voltStore(){

    }

    protected static void runStore(String table,String column,String values) throws Exception{

	/*
	 *Instantiate a client and connect to the database.
	 */

	org.voltdb.client.Client myApp = null;
	
	try{
	    myApp = ClientFactory.createClient();
	    myApp.createConnection("localhost");
	}catch(java.io.IOException e){
	    e.printStackTrace();
	    System.exit(-1);

	}
	
	/*
	 *Store the database.
	 */

	String[] val = values.split(",",-1);
	long tm = Timestamp.valueOf(val[0].replaceAll("'","")).getTime()*1000;
	double xloc = Double.valueOf(val[1]);
	double yloc = Double.valueOf(val[2]);
	double vel = Double.valueOf(val[3]);
	System.out.println(tm);

	//myApp.callProcedure("Insert", 100, 100, 100, 100);
	
	myApp.callProcedure("Insert", tm, xloc, yloc, vel);
	

	try {
	    myApp.drain();
	    myApp.close();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}

    }
}

