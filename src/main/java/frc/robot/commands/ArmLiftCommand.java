/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.STATE;
import frc.robot.subsystems.ArmLift;

public class ArmLiftCommand extends CommandBase {
  private ArmLift arm;
  private STATE state;
  private Timer timer; 
  private double timeOut;
  
  public ArmLiftCommand(ArmLift arm, STATE state) {
    addRequirements(arm);
    this.arm = arm;
    this.state = state;
  }

  public ArmLiftCommand(ArmLift arm, STATE state, double timeOut) {
    addRequirements(arm);
    this.arm = arm;
    this.state = state;
    timer = new Timer();
    this.timeOut = timeOut;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if(timer != null){
      timer.start();
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    switch(state) {
      case UP:
        arm.up();
        break;
      case DOWN:
        arm.downLimit();
        break;
      case OFF:
        arm.off();
        break;
      case FORWARDS:
        arm.off();
        break;
      case REVERSE:
        arm.off();
        break;
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    arm.off();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // if(state == STATE.DOWN && arm.getLowerLimits()){
    //   return true;
    // }
    // else if(state == STATE.UP){
    //   return false;
    // }
    // else if(state == STATE.OFF || state == STATE.FORWARDS || state == STATE.REVERSE){
    //   return true;
    // }
    // else{
    //   return false;
    // }

    if(timer != null){
      return (timer.get() >= timeOut);
    }
    else{
      return false;
    }

    // return false;
  }
}
