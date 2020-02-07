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
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.JoyDrive;
import frc.robot.commands.RealShootCommand;
import frc.robot.commands.ShooterCommand;
import frc.robot.commands.ShooterCommand.control;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.IntakeSystem;
import frc.robot.subsystems.ballShooter;
import frc.robot.subsystems.driveTrain;
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
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final driveTrain m_driveTrain = new driveTrain();
  private final ballShooter m_BallShooter = new ballShooter();
  private final IntakeSystem m_IntakeSystem = new IntakeSystem();
  private final Joystick joy = new Joystick(0);

  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
  private final JoyDrive m_joyDrive = new JoyDrive(m_driveTrain, joy);
  private final AlignZ m_AlignZ = new AlignZ(m_driveTrain, joy);
  private final XboxController m_XboxController = new XboxController(1);
  private final ShooterCommand m_ShooterCommandTurnOff = new ShooterCommand(m_BallShooter, control.DISABLE);
  private final ShooterCommand m_ShooterCommandTurnOn = new ShooterCommand(m_BallShooter, control.ENABLE);
  private final RealShootCommand m_RealShooter = new RealShootCommand(m_IntakeSystem);


  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    CommandScheduler.getInstance().setDefaultCommand(m_driveTrain, m_joyDrive);
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // new JoystickButton(m_XboxController, Button.kY.value).whenPressed(m_ShooterCommandTurnOff);
    // new JoystickButton(m_XboxController, Button.kX.value).whenPressed(m_ShooterCommandTurnOn);
    // new JoystickButton(m_XboxController, Button.kA.value).whileHeld(m_RealShooter);
    new JoystickButton(joy, 1).whileHeld(m_AlignZ);
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
