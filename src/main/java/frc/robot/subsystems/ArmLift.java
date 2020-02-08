/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ARM_LIFT_CONSTANTS;

public class ArmLift extends SubsystemBase {
  VictorSPX leftMotor;
  VictorSPX rightMotor;
  int leftDirection = 1;
  int rightDirection = 1;
  /**
   * Creates a new ArmLift.
   */
  public ArmLift() {
    leftMotor = new VictorSPX(ARM_LIFT_CONSTANTS.MOTOR_CONTROLLER_ID_LEFT);
    rightMotor = new VictorSPX(ARM_LIFT_CONSTANTS.MOTOR_CONTROLLER_ID_RIGHT);
    if (ARM_LIFT_CONSTANTS.IS_NEGATED_LEFT) {
      leftDirection = -1;
    }
    if (ARM_LIFT_CONSTANTS.IS_NEGATED_RIGHT) {
      rightDirection = -1;
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void up() {
    leftMotor.set(ControlMode.PercentOutput, leftDirection*ARM_LIFT_CONSTANTS.SPEED);
    rightMotor.set(ControlMode.PercentOutput, rightDirection*ARM_LIFT_CONSTANTS.SPEED);
  }
  public void off() {
    leftMotor.set(ControlMode.PercentOutput, 0);
    rightMotor.set(ControlMode.PercentOutput, 0);
  }
  public void down() {
    leftMotor.set(ControlMode.PercentOutput, -1*leftDirection*ARM_LIFT_CONSTANTS.SPEED);
    rightMotor.set(ControlMode.PercentOutput, -1*rightDirection*ARM_LIFT_CONSTANTS.SPEED);
  }
}
