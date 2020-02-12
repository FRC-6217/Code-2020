/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.libraries.swerve.Angle;
import frc.robot.subsystems.DriveTrain;

public class AlignZ extends CommandBase {
  /**
   * Creates a new AlignZ.
   */
  private final DriveTrain driveTrain;
  private Joystick joy;
  private double y;
  private double x;
  private Angle angle;

  private PIDController pidZ;
  private double errorZ;
  private double outputZ;

  public AlignZ(DriveTrain train, Joystick joy, Angle angle) {
    addRequirements(train);

    driveTrain = train;
    this.joy = joy;
    this.angle = angle;    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //Dead zone
    x = (Math.abs(joy.getRawAxis(0)) > .2) ? joy.getRawAxis(0) : 0.0;
    y = (Math.abs(joy.getRawAxis(1)) > .2) ? joy.getRawAxis(1) : 0.0;


    errorZ = pidZ.calculate(angle.getAngle(), 0);
		
		outputZ = MathUtil.clamp(errorZ, -1, 1);

		driveTrain.Drive(-y, x, outputZ, 0.5);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveTrain.Drive(0, 0, 0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
