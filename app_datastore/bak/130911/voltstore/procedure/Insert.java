package procedure;

import java.sql.Timestamp;
import org.voltdb.*;


public class Insert extends VoltProcedure{

  public final SQLStmt sql = new SQLStmt(
     "INSERT INTO ANDROID_DATA_STORE(tm,xlocation,ylocation,velocity) VALUES(?,?,?,?);"
  );

    public VoltTable[] run(Timestamp tm, double xlocation,double ylocation,double velocity)throws VoltAbortException{
	voltQueueSQL(sql, tm, xlocation, ylocation, velocity);
	voltExecuteSQL();
	return null;
    }

}
