/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.net.CacheRequest;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANError;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.NOT_SHOOTER_INTAKE_CONSTANTS;


public class NotShooterIntake extends SubsystemBase {
  CANEncoder encoder;
  CANSparkMax motor;
  int direction = 1;
  /**
   * Creates a new NotShooterIntake.
   */
  public NotShooterIntake() {
    motor = new CANSparkMax(Constants.NOT_SHOOTER_INTAKE_CONSTANTS.MOTOR_CONTROLLER_ID, MotorType.kBrushless);

    motor.restoreFactoryDefaults();

    //Set Idle Mode
    motor.setIdleMode(IdleMode.kBrake);

    if (NOT_SHOOTER_INTAKE_CONSTANTS.IS_NEGATED) {
      direction = -1;
    }

    //Shooter Encoders
    encoder = motor.getEncoder();

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void on() {
    motor.set(direction * NOT_SHOOTER_INTAKE_CONSTANTS.SPEED);
  }
  public void off() {
    motor.set(0);
  }
  public void reverseOn() {
    motor.set(-1 * direction * NOT_SHOOTER_INTAKE_CONSTANTS.SPEED);
  }
}
