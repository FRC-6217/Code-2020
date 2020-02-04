/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.ballShooter;

public class ShooterCommand extends CommandBase {
  /**
   * Creates a new ShooterCommand.
   */
  public static enum control {
    ENABLE,
    DISABLE
  }
  private control ctrl;
  private ballShooter bs;
  public ShooterCommand(ballShooter bs, control ctrl) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.ctrl = ctrl;
    this.bs = bs;
    addRequirements(bs);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (ctrl == control.DISABLE){
      bs.turnOffShooter();
    
    } 
    else {
      bs.turnOnShooter();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (ctrl == control.DISABLE) {
      return true;
    }
    return false;
  }
}
