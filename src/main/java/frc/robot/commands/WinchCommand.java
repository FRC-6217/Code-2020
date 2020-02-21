/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.STATE;
import frc.robot.subsystems.Winch;

public class WinchCommand extends CommandBase {
  STATE state;
  Winch winch;
  /**
   * Creates a new WinchCommand.
   */
  public WinchCommand(Winch winch, STATE state) {
    addRequirements(winch);
    this.winch = winch;
    this.state = state;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    switch (state) {
      case UP:
        winch.on();
        break;
      case DOWN:
        winch.reverse();
        break;
      case OFF:
        winch.off();
        break;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    winch.off();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return state == STATE.OFF;
  }
}
