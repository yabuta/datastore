import java.sql.*;
import java.io.*;
import org.voltdb.*;
import org.voltdb.client.*;
import java.sql.Timestamp;
import procedure.*;


public class voltjdbc{


    public void voltjdbc(){

    }

    public static void jdbc(String sqldata) {
 
        String driver = "org.voltdb.jdbc.Driver";
        String url = "jdbc:voltdb://localhost:21212";
        String sql = "INSERT INTO " + sqldata;
        
        try {
	    // Load driver. Create connection.
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url);
            
	    // create a statement
            Statement query = conn.createStatement();
            query.executeUpdate(sql);
	    System.out.println("SUCCESS");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   



} 
