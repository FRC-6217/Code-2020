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
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.GobblerJoy;
import frc.robot.commands.JoyDrive;
import frc.robot.commands.joyShooter;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.ballShooter;
import frc.robot.subsystems.driveTrain;
import frc.robot.subsystems.gobbler;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

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
  private final ballShooter m_ballShooter = new ballShooter();
  private final gobbler m_gobbler = new gobbler();
  private final Joystick joystick = new Joystick(0);
  private final Joystick xbox = new Joystick(1);

  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
  private final JoyDrive m_joyDrive = new JoyDrive(m_driveTrain, joystick);
  private final joyShooter m_joyShooter = new joyShooter(m_ballShooter, joystick, xbox);
  private final GobblerJoy m_gobblerJoy = new GobblerJoy(m_gobbler, xbox);



  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    CommandScheduler.getInstance().setDefaultCommand(m_driveTrain, m_joyDrive);
    CommandScheduler.getInstance().setDefaultCommand(m_ballShooter, m_joyShooter);
    CommandScheduler.getInstance().setDefaultCommand(m_gobbler, m_gobblerJoy);
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
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
