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
import frc.robot.commands.BallAnglerController;
import frc.robot.commands.BallChuckerController;
import frc.robot.subsystems.BallAngler;
import frc.robot.subsystems.BallChucker;
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
  private static final Joystick m_xbox = new Joystick(Constants.XBOX_PORT);
  
  //Buttons (lots and lots of buttons)
  private static final JoystickButton m_BallChuckerForwButton = new JoystickButton(m_xbox, Constants.BALL_CHUCKER_BUTTON_FORWARD);
  private static final JoystickButton m_BallChuckerBackButton = new JoystickButton(m_xbox, Constants.BALL_CHUCKER_BUTTON_BACKWARD);
  private static final JoystickButton m_BallAnglerUpButton = new JoystickButton(m_xbox, Constants.BALL_ANGLER_BUTTON_UP);
  private static final JoystickButton m_BallAnglerDownButton = new JoystickButton(m_xbox, Constants.BALL_ANGLER_BUTTON_DOWN);
  
  //subsystems
  private static final BallChucker m_BallChucker = new BallChucker();
  private static final BallAngler m_BallAngler = new BallAngler();

  //commands
  private static final BallChuckerController m_BallChuckerController = new BallChuckerController(m_BallChucker, m_BallChuckerForwButton, m_BallChuckerBackButton);
  private static final BallAnglerController m_BallAnglerController = new BallAnglerController(m_BallAngler, m_BallAnglerUpButton, m_BallAnglerDownButton);



  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    CommandScheduler.getInstance().setDefaultCommand(m_BallChucker, m_BallChuckerController);
    CommandScheduler.getInstance().setDefaultCommand(m_BallAngler, m_BallAnglerController);
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
    return null;
  }
}
