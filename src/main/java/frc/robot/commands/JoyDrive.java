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

public class JoyDrive extends CommandBase {
  private final driveTrain m_driveTrain;

  private Joystick joy;
  private double x;
  private double y;
  private double z;
  private boolean gyroButtonForward;
  private boolean gyroButtonBackward;
  private double governer;
  
  private boolean isReversed;
  private double x1;
  private double y1;
  
  /**
   * Creates a new JoyDrive.
   */
  public JoyDrive(driveTrain subsystem, Joystick joy) {
    m_driveTrain = subsystem;

    addRequirements(subsystem);
    // Use addRequirements() here to declare subsystem dependencies.

    this.joy = joy;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_driveTrain.ResetGyro();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    y = joy.getRawAxis(0);
    x = joy.getRawAxis(1);
    z = joy.getRawAxis(2);
    gyroButtonForward = joy.getRawButton(5);
    gyroButtonBackward = joy.getRawButton(6);        
    governer = joy.getRawAxis(3);

    if(gyroButtonForward){
        m_driveTrain.ResetGyro();
        isReversed = false;
    }
    else if(gyroButtonBackward){
        m_driveTrain.ResetGyro();
        isReversed = true;
    }
    
    x = (Math.abs(x) > .2 ? x : 0.0);
    y = (Math.abs(y) > .2 ? y : 0.0);
    z = (Math.abs(z) > .2 ? z : 0.0);

    x1 = m_driveTrain.TransformX(x, y, isReversed);
    y1 = m_driveTrain.TransformY(x, y, isReversed);

    m_driveTrain.Drive(x1, -y1, z, Math.abs(governer-1));
    gyroButtonForward = false;
    gyroButtonBackward = false;
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
