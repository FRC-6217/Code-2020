/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.driveTrain;
import frc.robot.subsystems.gobbler;

public class GobblerJoy extends CommandBase {

  private final gobbler m_gobbler;

  private Joystick joy;
  /**
   * Creates a new GobblerJoy.
   */
  public GobblerJoy(gobbler subsystem, Joystick xbox) {
    m_gobbler = subsystem;

    addRequirements(subsystem);

    this.joy = xbox;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(joy.getRawButton(1)){
      m_gobbler.gobble(-.75);
    }
    else{
      m_gobbler.gobble(0);
    }
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
