CREATE TABLE ANDROID_DATA_STORE(
       tm timestamp,
       xlocation real,
       ylocation real,
       velocity real

);

CREATE TABLE PRIUS_DATA_STORE( 

 tm timestamp,
 devmode float,
 drvcontmode float,
 drvoverridemode float,
 drvservo float,
 drivepedal float,
 targetpedalstr float,
 inputpedalstr float,
 targetveloc float,
 speed float,
 driveshift float,
 targetshift float,
 inputshift float,
 strmode float,
 strcontmode float,
 stroverridemode float,
 strservo float,
 targettorque float,
 torque float,
 angle float,
 targetangle float,
 bbrakepress float,
 brakepedal float,
 brtargetpedalstr float,
 brinputpedalstr float,
 battery float,
 voltage float,
 anp float,
 battmaxtemparature float,
 battmintemparature float,
 maxchgcurrent float,
 maxdischgcurrent float,
 sideacc float,
 accellfromp float,
 anglefromp float,
 brakepedalfromp float,
 speedfr float,
 speedfl float,
 speedrr float,
 speedrl float,
 velocfromp2 float,
 drvmode float,
 devpedalstrfromp float,
 rpm float,
 velocflfromp float,
 ev_mode float,
 temp float,
 shiftfrmprius float,
 light float,
 gaslevel integer not null,
 door float,
 cluise float

);

CREATE INDEX priusIdx ON PRIUS_DATA_STORE (tm);
CREATE INDEX androidIdx ON ANDROID_DATA_STORE (tm);

PARTITION TABLE PRIUS_DATA_STORE ON COLUMN gaslevel;

CREATE PROCEDURE FROM CLASS Insert;
CREATE PROCEDURE FROM CLASS insertPrius;
