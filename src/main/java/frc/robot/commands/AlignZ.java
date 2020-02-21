/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.revrobotics.CANPIDController;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.libraries.Angle;
import frc.robot.libraries.Distance;
import frc.robot.subsystems.DriveTrain;

public class AlignZ extends CommandBase {
  /**
   * Creates a new AlignZ.
   */
  private final DriveTrain driveTrain;
  private Joystick joy;
  private double y;
  private double x;
  private double z;
  private Angle angle;
  private Distance distance;

  private boolean angleUse;
  private boolean distanceUse;

  private PIDController pidZ;
  private double errorZ;
  private double outputZ;

  private double kPZ;
  private double kIZ;
  private double kDZ;

  private PIDController pidY;
  private double errorY;
  private double outputY;

  private double kPY;
  private double kIY;
  private double kDY;
  

  public AlignZ(DriveTrain train, Joystick joy, Angle angle) {
    addRequirements(train);

    driveTrain = train;
    this.joy = joy;
    this.angle = angle;

    angleUse = true;
    distanceUse = false;

    kPZ = 0;
    kIZ = 0; 
    kDZ = 0;

    updatePIDZ();
    pidZ = new PIDController(kPZ, kIZ, kDZ);
  }
  public AlignZ(DriveTrain train, Joystick joy, Distance distance) {
    addRequirements(train);

    driveTrain = train;
    this.joy = joy;
    this.distance = distance;

    angleUse = false;
    distanceUse = true;

    kPY = 0;
    kIY = 0; 
    kDY = 0;

    updatePIDY();
    pidY = new PIDController(kPY, kIY, kDY);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //Dead zone
    x = (Math.abs(joy.getRawAxis(0)) > .2) ? joy.getRawAxis(0) : 0.0;
    y = (Math.abs(joy.getRawAxis(1)) > .2) ? joy.getRawAxis(1) : 0.0;
    z = (Math.abs(joy.getRawAxis(2)) > .2) ? joy.getRawAxis(2) : 0.0;


    if(angleUse && !distanceUse){
      updatePIDZ();
  
      double localAngle = angle.getAngle();
      if((localAngle != (Double.POSITIVE_INFINITY))){
        if (Math.abs(localAngle) < .1 ) {
          localAngle = 0;
        }      

        errorZ = pidZ.calculate(localAngle, 0);
        SmartDashboard.putNumber("ErrorZ", errorZ);
        outputZ = MathUtil.clamp(errorZ, -1, 1);

        driveTrain.Drive(-x, y, outputZ, 0.5);
        //TODO set max speed constant
      }
      else{
        driveTrain.Drive(-y, x, z, 0.5);
      }
    }
  
    if(!angleUse && distanceUse){
      updatePIDY();

      double localDistance = distance.getDistance();
      if((localDistance != (Double.POSITIVE_INFINITY))){
        if (Math.abs(localDistance) < .1 ) {
          localDistance = 0;
        }      

        errorY = pidY.calculate(localDistance, 0);
        SmartDashboard.putNumber("ErrorY", errorY);
        outputY = MathUtil.clamp(errorY, -1, 1);

        driveTrain.Drive(x, -outputY, z, 0.5);
        //TODO set max speed constant
      }
      else{
        driveTrain.Drive(x, -y, z, 0.5);
      }
    }

  }

  public void updatePIDZ(){
    boolean isUpdated = false;
  
    if(SmartDashboard.getNumber("KpAlignZ", 0) != kPZ){
      kPZ = SmartDashboard.getNumber("KpAlignZ", 0);
      isUpdated = true;  
    }
    if(SmartDashboard.getNumber("KiAlignZ", 0) != kIZ){
      kIZ = SmartDashboard.getNumber("KiAlignZ", 0);
      isUpdated = true;  
    }
    if(SmartDashboard.getNumber("KdAlignZ", 0) != kDZ){
      kDZ = SmartDashboard.getNumber("KdAlignZ", 0);
      isUpdated = true;  
    }

    if(isUpdated){
      pidZ.setP(kPZ);
      pidZ.setI(kIZ);
      pidZ.setD(kDZ);
    }
  }

  public void updatePIDY(){
    boolean isUpdated = false;
    
    if(SmartDashboard.getNumber("KpAlignY", 0) != kPY){
      kPY = SmartDashboard.getNumber("KpAlignY", 0);
      isUpdated = true;  
    }
    if(SmartDashboard.getNumber("KiAlignY", 0) != kIY){
      kIY = SmartDashboard.getNumber("KiAlignY", 0);
      isUpdated = true;  
    }
    if(SmartDashboard.getNumber("KdAlignY", 0) != kDY){
      kDY = SmartDashboard.getNumber("KdAlignY", 0);
      isUpdated = true;  
    }

    if(isUpdated){
      pidY.setP(kPY);
      pidY.setI(kIY);
      pidY.setD(kDY);
    }
  }
  

  // private void setPID(PIDController pid) {
  //   pid.setP(kPZ);
  //   pid.setI(kIZ);
  //   pid.setD(kDZ);
  // }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveTrain.Drive(0, 0, 0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  public String toString(){
    return "AlignZ";
  }
}
