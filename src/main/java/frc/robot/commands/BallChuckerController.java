/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.BallChucker;

public class BallChuckerController extends CommandBase {
  /**
   * Creates a new BallChuckerController.
   */
  private final BallChucker m_bc;
  private final JoystickButton forwards;
  private final JoystickButton backwards;

  private boolean forward;
  private boolean backward;

  public BallChuckerController(BallChucker bc, JoystickButton forwards, JoystickButton backwards, JoystickButton intake) {
    this.forwards = forwards;
    this.backwards = backwards;
    this.m_bc = bc;

    addRequirements(bc);
  }

// Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    forward = forwards.get();
    backward = backwards.get();
    if(forward && !backward){
      m_bc.runFow();
    }

    else if(!forward && backward){
      m_bc.runBac();
    }

    else{
      m_bc.stop();
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
