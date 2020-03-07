/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.autoCommand;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.STATE;
import frc.robot.commands.ArmLiftCommand;
import frc.robot.commands.BallShooterCommand;
import frc.robot.commands.JoyDriveCommand;
import frc.robot.commands.ShooterIntakeCommand;
import frc.robot.commands.Wait;
import frc.robot.libraries.FakeJoystick;
import frc.robot.subsystems.ArmLift;
import frc.robot.subsystems.BallShooter;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.ShooterIntake;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class LeftStartShoot extends SequentialCommandGroup {
  /**
   * Creates a new LeftStartShoot.
   */
  public LeftStartShoot(BallShooter bs, ArmLift al, ShooterIntake si, DriveTrain dt, FakeJoystick joy) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(new BallShooterCommand(bs), new ArmLiftCommand(al, STATE.DOWN, 1.5), new Wait(0.5), new ShooterIntakeCommand(si, STATE.FORWARDS, 0.25), new ShooterIntakeCommand(si, STATE.OFF, 1), new ShooterIntakeCommand(si, STATE.FORWARDS, 0.5), new ShooterIntakeCommand(si, STATE.OFF, 1), new ShooterIntakeCommand(si, STATE.FORWARDS, 1), new ShooterIntakeCommand(si, STATE.OFF, 0), new BallShooterCommand(bs), new JoyDriveCommand(dt, joy, 50));
  }
}
