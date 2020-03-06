/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class JoyDriveCommand extends CommandBase {
  private final DriveTrain driveTrain;

  private Joystick joy;
  private double x;
  private double y;
  private double z;
  private double governer;
  
  private boolean isReversed = false;
  private boolean bounce = false;
  private double x1;
  private double y1;
  private int direction = 1;
  private boolean isAuto;
  private double distanceInches;
  
  /**
   * Creates a new JoyDrive.
   */
  public JoyDriveCommand(DriveTrain train, Joystick joy) {
    addRequirements(train);

    driveTrain = train;
    this.joy = joy;
    isAuto = false;
  }

  public JoyDriveCommand(DriveTrain train, Joystick joy, double distanceInches) {
    addRequirements(train);

    driveTrain = train;
    this.joy = joy;
    isAuto = true;
    this.distanceInches = distanceInches;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    driveTrain.resetDrivePos();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    x = -joy.getRawAxis(0);
    y = joy.getRawAxis(1);
    z = -joy.getRawAxis(2);      
    governer = joy.getRawAxis(3);

    if(joy.getRawButton(3) && !bounce){
        isReversed ^= joy.getRawButton(3);
        System.out.println(isReversed);
        bounce = true;
    }
    else if(joy.getRawButton(3) && bounce){
      bounce = true;
    }
    else if(!(joy.getRawButton(3)) && bounce){
      bounce = false;
    }
    
    x = (Math.abs(x) > .2 ? x : 0.0);
    y = (Math.abs(y) > .2 ? y : 0.0);
    z = (Math.abs(z) > .2 ? z : 0.0);

    // x1 = driveTrain.TransformX(x, y, isReversed);
    // y1 = driveTrain.TransformY(x, y, isReversed);

    if(isReversed){
      driveTrain.drive(-x, -y, z, Math.abs(governer-1));
    }
    else{
      driveTrain.drive(x, y, z, Math.abs(governer-1));
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveTrain.drive(0, 0, 0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(isAuto){
      return (Math.abs(driveTrain.getAveragePos()) >= distanceInches);
    }
    else{
      return false;
    }
  }
  
  public String toString(){
    return "JoyDrive";
  }
}
