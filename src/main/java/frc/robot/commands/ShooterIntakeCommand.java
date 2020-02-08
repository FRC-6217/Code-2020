/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterIntake;

public class ShooterIntakeCommand extends CommandBase {
  ShooterIntake shooterIntake;
  boolean isOn;
  /**
   * Creates a new ShooterIntakeCommand.
   */
  public ShooterIntakeCommand(ShooterIntake intake, boolean onOff) {
    addRequirements(intake);
    shooterIntake = intake;
    this.isOn = onOff;

    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (isOn) {
      shooterIntake.on();

    }
    else {
      shooterIntake.off();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooterIntake.off();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (!isOn) {
      return true;
    }
    return false;
  }
}