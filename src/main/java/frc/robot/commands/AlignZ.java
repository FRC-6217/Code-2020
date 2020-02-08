/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.driveTrain;

public class AlignZ extends CommandBase {
  /**
   * Creates a new AlignZ.
   */
  private final driveTrain m_driveTrain;
  private Joystick joy;
  private double y;

  public AlignZ(driveTrain subsystem, Joystick joy) {
    m_driveTrain = subsystem;

    addRequirements(subsystem);

    this.joy = joy;    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // m_driveTrain.Drive(0, 0, 0, 0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    y = (Math.abs(joy.getRawAxis(1)) > .2) ? joy.getRawAxis(1) : 0.0;
    m_driveTrain.XAlign(joy.getRawAxis(1));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // m_driveTrain.Drive(0, 0, 0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
