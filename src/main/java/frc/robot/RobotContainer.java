/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.commands.AlignZ;
import frc.robot.commands.ArmLiftCommand;
import frc.robot.commands.BallShooterCommand;
import frc.robot.commands.JoyDriveCommand;
import frc.robot.commands.NotShooterIntakeCommand;
import frc.robot.commands.ShooterIntakeCommand;
import frc.robot.commands.NotShooterIntakeCommand.STATE;
import frc.robot.libraries.swerve.Angle;
import frc.robot.libraries.swerve.Distance;
import frc.robot.subsystems.ArmLift;
import frc.robot.subsystems.BallShooter;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.NotShooterIntake;
import frc.robot.subsystems.ShooterIntake;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  //Joysticks
  private final Joystick driveStick = new Joystick(Constants.DRIVESTICK_PORT);
  private final XboxController xbox = new XboxController(Constants.XBOX_PORT);

  //Helper classes
  private final Angle angle = new Angle();
  private final Distance distance = new Distance();

  // Subsystems
  private final DriveTrain driveTrain = new DriveTrain();
  // private final ArmLift armLift = new ArmLift();
  // private final ShooterIntake shooterIntake = new ShooterIntake();
  // private final NotShooterIntake notShooterIntake = new NotShooterIntake();
  // private final BallShooter ballShooter = new BallShooter();
  private final LimeLight limeLight = new LimeLight(angle, distance);


  public RobotContainer() {
    // Configure the button bindings
    CommandScheduler.getInstance().setDefaultCommand(driveTrain, new JoyDriveCommand(driveTrain, driveStick));
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(driveStick, 1).whileHeld(new AlignZ(driveTrain, driveStick, angle));
    // new JoystickButton(xbox, Button.kA.value).whileHeld(new ShooterIntakeCommand(shooterIntake, true));
    // new JoystickButton(xbox, Button.kX.value).whileHeld(new ArmLiftCommand(armLift, true));
    // new JoystickButton(xbox, Button.kY.value).whileHeld(new ArmLiftCommand(armLift, false));
    // new JoystickButton(xbox, Button.kBumperLeft.value).whenPressed(new BallShooterCommand(ballShooter, true));
    // new JoystickButton(xbox, Button.kBumperRight.value).whenPressed(new BallShooterCommand(ballShooter, false));
    // new JoystickButton(xbox, Button.kBack.value).whileHeld(new NotShooterIntakeCommand(notShooterIntake, STATE.REVERSE));
    // new JoystickButton(xbox, Button.kStart.value).whileHeld(new NotShooterIntakeCommand(notShooterIntake, STATE.FORWARDS));
}



  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}
