import java.sql.Timestamp;
import org.voltdb.*;


public class insertPrius extends VoltProcedure{

  public final SQLStmt sql = new SQLStmt(
     "INSERT INTO PRIUS_DATA_STORE(tm,devmode,drvcontmode,drvoverridemode,drvservo,drivepedal,targetpedalstr,inputpedalstr,targetveloc,speed,driveshift,targetshift,inputshift,strmode,strcontmode,stroverridemode,strservo,targettorque,torque,angle,targetangle,bbrakepress,brakepedal,brtargetpedalstr,brinputpedalstr,battery,voltage,anp,battmaxtemparature,battmintemparature,maxchgcurrent,maxdischgcurrent,sideacc,accellfromp,anglefromp,brakepedalfromp,speedfr,speedfl,speedrr,speedrl,velocfromp2,drvmode,devpedalstrfromp,rpm,velocflfromp,ev_mode,temp,shiftfrmprius,light,gaslevel,door,cluise) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);"
  );

    public VoltTable[] run(long tm,double devmode,double drvcontmode,double drvoverridemode,double drvservo,double drivepedal,double targetpedalstr,double inputpedalstr,double targetveloc,double speed,double driveshift,double targetshift,double inputshift,double strmode,double strcontmode,double stroverridemode,double strservo,double targettorque,double torque,double angle,double targetangle,double bbrakepress,double brakepedal,double brtargetpedalstr,double brinputpedalstr,double battery,double voltage,double anp,double battmaxtemparature,double battmintemparature,double maxchgcurrent,double maxdischgcurrent,double sideacc,double accellfromp,double anglefromp,double brakepedalfromp,double speedfr,double speedfl,double speedrr,double speedrl,double velocfromp2,double drvmode,double devpedalstrfromp,double rpm,double velocflfromp,double ev_mode,double temp,double shiftfrmprius,double light,int gaslevel,double door,double cluise)throws VoltAbortException{
	voltQueueSQL(sql, tm,devmode,drvcontmode,drvoverridemode,drvservo,drivepedal,targetpedalstr,inputpedalstr,targetveloc,speed,driveshift,targetshift,inputshift,strmode,strcontmode,stroverridemode,strservo,targettorque,torque,angle,targetangle,bbrakepress,brakepedal,brtargetpedalstr,brinputpedalstr,battery,voltage,anp,battmaxtemparature,battmintemparature,maxchgcurrent,maxdischgcurrent,sideacc,accellfromp,anglefromp,brakepedalfromp,speedfr,speedfl,speedrr,speedrl,velocfromp2,drvmode,devpedalstrfromp,rpm,velocflfromp,ev_mode,temp,shiftfrmprius,light,gaslevel,door,cluise);
	voltExecuteSQL();
	return null;
    }

}

/*column一覧
tm,devmode,drvcontmode,drvoverridemode,drvservo,drivepedal,targetpedalstr,inputpedalstr,targetveloc,speed,driveshift,targetshift,inputshift,strmode,strcontmode,stroverridemode,strservo,targettorque,torque,angle,targetangle,bbrakepress,brakepedal,brtargetpedalstr,brinputpedalstr,battery,voltage,anp,battmaxtemparature,battmintemparature,maxchgcurrent,maxdischgcurrentsideacc,accellfromp,anglefromp,brakepedalfromp,speedfr,speedfl,speedrr,speedrl,velocfromp2,drvmode,devpedalstrfromp,rpm,velocflfromp,ev_mode,temp,shiftfrmprius,light,gaslevel,door,cluise
*/

