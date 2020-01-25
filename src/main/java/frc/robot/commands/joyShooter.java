/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ballShooter;

public class joyShooter extends CommandBase {
  private final ballShooter m_ballShooter;
  private final Joystick joy;
  /**
   * Creates a new joyShooter.
   */
  public joyShooter(ballShooter subsystem, Joystick joy) {
    m_ballShooter = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);

    this.joy =joy;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
   // m_ballShooter.resetFactory();
    m_ballShooter.setPid();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(joy.getRawButton(1)) {
      m_ballShooter.feed(.5);
    }
    else{
      m_ballShooter.feed(0);
    }

    // m_ballShooter.setVelocity(100);
    m_ballShooter.shoot(1-joy.getRawAxis(3));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
