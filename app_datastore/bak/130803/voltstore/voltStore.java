import org.voltdb.*;
import org.voltdb.client.*;
import java.sql.Timestamp;
import procedure.*;


public class voltStore{

    private static int n=5;

    public voltStore(){
    }

    protected static void runStore(Timestamp tm,double xloc,double yloc,double vel) throws Exception{

	/*
	 *Instantiate a client and connect to the database.
	 */

	System.out.println("before init");
	org.voltdb.client.Client myApp = null;
	//ClientConfig config = null;
	System.out.println("before init2");
	
	try{
	    //config = new ClientConfig("localhost");
	    myApp = ClientFactory.createClient();
	    System.out.println("before init3");
	    myApp.createConnection("localhost");
	}catch(java.io.IOException e){
	    e.printStackTrace();
	    System.exit(-1);

	}
	
	/*
	 *Store the database.
	 */

	System.out.println("before Insert");
	
	myApp.callProcedure("Insert", tm, xloc, yloc, vel);
	    
	System.out.println("after Insert");
	

	try {
	    myApp.drain();
	    myApp.close();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}

    }
}

