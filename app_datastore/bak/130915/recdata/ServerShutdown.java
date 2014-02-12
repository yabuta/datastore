
public class ServerShutdown extends Thread{

    public ServerShutdown(){
	

    }

    public run() throws java.io.IOException {

	System.out.println("For finish , input \"quit\"");
	
	while(1){
	    BufferReader r = new BufferReader(new InputStreamReader(System.in), 1);
	    System.out.print(">");
	    System.out.flush();
	    String quit = r.readLine();
	    
	    if(quit.equals("quit")){

		//Handler処理を書く。



	    }
	    

	}


    }
    
    private syncronized void setShutdown(){

	while(available == true){
	    try{
		wait();
	    }
	    catch(InterruptedException e){
		
	    }
	}

	ServerMain.SHUTDOWN = true;

	

    }


}
