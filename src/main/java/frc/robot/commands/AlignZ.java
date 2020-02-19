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

  private PIDController pidZ;
  private double errorZ;
  private double outputZ;

  private double kP;
  private double kI;
  private double kD;
  private double kIz;
  private double kFF;

  public AlignZ(DriveTrain train, Joystick joy, Angle angle) {
    addRequirements(train);

    driveTrain = train;
    this.joy = joy;
    this.angle = angle;

    updatePID();
    pidZ = new PIDController(kP, kI, kD);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //Dead zone
    updatePID();
    x = (Math.abs(joy.getRawAxis(0)) > .2) ? joy.getRawAxis(0) : 0.0;
    y = (Math.abs(joy.getRawAxis(1)) > .2) ? joy.getRawAxis(1) : 0.0;
    z = (Math.abs(joy.getRawAxis(2)) > .2) ? joy.getRawAxis(2) : 0.0;
      
    double localAngle = angle.getAngle();
    if((localAngle != (Double.POSITIVE_INFINITY))){
      if (Math.abs(localAngle)<2.5) {
        localAngle = 0;
      }
      

      errorZ = pidZ.calculate(localAngle, 0);	
      outputZ = MathUtil.clamp(errorZ, -1, 1);

      System.out.println(localAngle + "," + errorZ + "," + outputZ);

      driveTrain.Drive(-x, y, outputZ, 0.5);
      //TODO set max speed constant
    }
    else{
      driveTrain.Drive(-y, x, 0, 0.5);
    }
  }
    public void updatePID(){
      boolean isUpdated = false;
  
      if(SmartDashboard.getNumber("KpAlignZ", 0) != kP){
        kP = SmartDashboard.getNumber("KpAlignZ", 0);
        isUpdated = true;  
      }
      if(SmartDashboard.getNumber("KiAlignZ", 0) != kI){
        kI = SmartDashboard.getNumber("KiAlignZ", 0);
        isUpdated = true;  
      }
      if(SmartDashboard.getNumber("KdAlignZ", 0) != kD){
        kD = SmartDashboard.getNumber("KdAlignZ", 0);
        isUpdated = true;  
      }
      if(SmartDashboard.getNumber("IzAlignZ", 0) != kIz){
        kIz = SmartDashboard.getNumber("IzAlignZ", 0);
        isUpdated = true;  
      }
      if(SmartDashboard.getNumber("FfAlignZ", 0) != kFF){
        kFF = SmartDashboard.getNumber("FfAlignZ", 0);
        isUpdated = true;  
      }
      
      if(isUpdated){
        setPID(pidZ);
      }
  }

  private void setPID(PIDController pid) {
    pid.setP(kP);
    pid.setI(kI);
    pid.setD(kD);

  }

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
