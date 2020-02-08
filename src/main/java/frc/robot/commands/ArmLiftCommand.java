/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmLift;

public class ArmLiftCommand extends CommandBase {
  ArmLift arm;
  boolean isDown;
  /**
   * Creates a new ArmLiftCommand.
   */
  public ArmLiftCommand(ArmLift arm, boolean isDown) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(arm);
    this.arm = arm;
    this.isDown = isDown;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (isDown) {
      arm.down();
    }
    else {
      arm.up();
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    arm.off();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
