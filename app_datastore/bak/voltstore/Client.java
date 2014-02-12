import org.voltdb.*;
import org.voltdb.client.*;


public class Client{

private static int n=5;

public static void main(String[] args) throws Exception{

  /*
 *Instantiate a client and connect to the database.
 */

  org.voltdb.client.Client myApp;
  myApp=ClientFactory.createClient();
  myApp.createConnection("localhost");
  
  /*
 *Load the database.
 */



  for(int i = 0;i < n; i++) {

      myApp.callProcedure("Insert","English" + i,"Hello" + i,"World");

  }


  for(int i = 0;i < n-1; i++){
      myApp.callProcedure("Insert2","English" + i,"Hello" + ( i + n ) ,"World", "Tom");
  }
  
  }
}

