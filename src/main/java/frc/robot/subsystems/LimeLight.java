/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.libraries.Angle;
import frc.robot.libraries.DistanceY;
import frc.robot.libraries.DistanceX;
import frc.robot.Constants.LIME_LIGHT_CONSTANTS;

public class LimeLight extends SubsystemBase {

  private Angle angleZ;
  private Angle angleY;
  private DistanceY distanceY;
  private DistanceX distanceX;
  private Angle gyro;

  private NetworkTable table;
	private NetworkTableEntry tx;
	private NetworkTableEntry ty;
  private NetworkTableEntry tv;

  private int numClients = 0;

  public LimeLight(Angle angleZ, Angle angleY, DistanceY distanceY, DistanceX distanceX, Angle gyro) {
    this.angleZ = angleZ;
    this.angleY = angleY;
    this.distanceY = distanceY;
    this.distanceX = distanceX;
    this.gyro = gyro;

    table = NetworkTableInstance.getDefault().getTable("limelight");
    // table.getEntry("pipeline").setNumber(1);
  	tx = table.getEntry("tx");
	  ty = table.getEntry("ty");
    tv = table.getEntry("tv");
  }

  // public void toggleLight(){
  //   if(table.getEntry("pipeline").getDouble(0) == 0){
  //     table.getEntry("pipeline").setDouble(3);
  //   }
  //   else{
  //     table.getEntry("pipeline").setDouble(0);
  //   }
  // }

  // public void visionOn() {
  //   if(!(table.getEntry("pipeline").getDouble(0) == 0)){
  //     table.getEntry("pipeline").setDouble(0);
  //   }
    
    // numClients++;
    // table.getEntry("pipeline").setNumber(1);
    /*0 	use the LED Mode set in the current pipeline
    1 	force off*/
  // }

  // public void visionOff() {
  //   if(!(table.getEntry("pipeline").getDouble(0) == 1)){
  //     table.getEntry("pipeline").setDouble(1);
  //   }
    
    // numClients--;
    // if (numClients == 0) {
    //   table.getEntry("pipeline").setNumber(0);
    // }
    /* 0 	Vision processor
    1 	Driver Camera (Increases exposure, disables vision processing) */
  // }

  private boolean targetAquired(){
    return tv.getDouble(0) == 1;
  }

  private double getZ(){
    return -ty.getDouble(Double.POSITIVE_INFINITY);
  }

  private double getY(){
    return tx.getDouble(Double.POSITIVE_INFINITY);
  }

  private double getDistanceY(){
    return (LIME_LIGHT_CONSTANTS.GOAL_HEIGHT - LIME_LIGHT_CONSTANTS.LIME_HEIGHT)/(Math.tan(LIME_LIGHT_CONSTANTS.LIME_ANGLE + getY()));
  }

  private double getDistanceX(){
    return getY() * Math.sin(gyro.getAngle());
  }

  @Override
  public void periodic() {
    // SmartDashboard.putNumber("pipeline", table.getEntry("pipeline").getDouble(1000000));
    SmartDashboard.putBoolean("Target Aquired", targetAquired());
    SmartDashboard.putNumber("LimeLight Z", getZ());
    SmartDashboard.putNumber("LimeLight Y", getY());
    SmartDashboard.putNumber("LimeLight DistacneY", getDistanceY());
    SmartDashboard.putNumber("LimeLight DistanceX", getDistanceX());

    if(targetAquired()){
      angleZ.setAngle(getZ());
      angleY.setAngle(getY());
      distanceY.setDistance(getDistanceY());
      distanceX.setDistance(getDistanceX());
    }
    else{
      angleZ.setAngle(Double.POSITIVE_INFINITY);
      angleY.setAngle(Double.POSITIVE_INFINITY);
      distanceY.setDistance(Double.POSITIVE_INFINITY);
      distanceX.setDistance(Double.POSITIVE_INFINITY);
    }
  }
}
