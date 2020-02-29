/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ARM_LIFT_CONSTANTS;
import frc.robot.Constants.STATE;
import frc.robot.Constants.SIDE;

public class ArmLift extends SubsystemBase {

  private CANSparkMax leftMotor;
  private CANSparkMax rightMotor;
  private DigitalInput leftLimitDown;
  private DigitalInput rightLimitDown;
  private int leftDirection = 1;
  private int rightDirection = 1;
  
  public ArmLift() {
    leftMotor = new CANSparkMax(ARM_LIFT_CONSTANTS.MOTOR_CONTROLLER_ID_LEFT, MotorType.kBrushed);
    rightMotor = new CANSparkMax(ARM_LIFT_CONSTANTS.MOTOR_CONTROLLER_ID_RIGHT, MotorType.kBrushed);

    leftMotor.restoreFactoryDefaults();
    rightMotor.restoreFactoryDefaults();
    // leftMotor.setIdleMode(IdleMode.kBrake);
    // rightMotor.setIdleMode(IdleMode.kBrake);
    
    // leftLimitDown = new DigitalInput(ARM_LIFT_CONSTANTS.LIMIT_SWITCH_ID_LEFT);
    // rightLimitDown = new DigitalInput(ARM_LIFT_CONSTANTS.LIMIT_SWITCH_ID_RIGHT);
    
    if (ARM_LIFT_CONSTANTS.IS_NEGATED_LEFT) {
      leftDirection = -1;
    }
    if (ARM_LIFT_CONSTANTS.IS_NEGATED_RIGHT) {
      rightDirection = -1;
    }
  }

  @Override
  public void periodic() {
  }

  public void up() {
    leftMotor.set(leftDirection*ARM_LIFT_CONSTANTS.SPEED);
    rightMotor.set(rightDirection*ARM_LIFT_CONSTANTS.SPEED);
  }
  public void off() {
    leftMotor.set(0);
    rightMotor.set(0);
  }
  public void down() {
      leftMotor.set(-leftDirection*ARM_LIFT_CONSTANTS.SPEED);
      rightMotor.set(-rightDirection*ARM_LIFT_CONSTANTS.SPEED);
  }

  // public void moveArm(SIDE side, STATE state) {

  //   if (SIDE.LEFT == side || SIDE.BOTH == side) {
  //     setArmState(leftMotor, state);
  //   }
  //   if (SIDE.RIGHT == side || SIDE.BOTH == side)
  //    setArmState(rightMotor, state);
  // }

  // private void setArmState(VictorSPX arm, STATE state) {
  //   switch (state) {
  //     case UP:
  //       arm.set(ControlMode.PercentOutput, leftDirection*ARM_LIFT_CONSTANTS.SPEED);
  //       break;
  //     case DOWN:
  //       arm.set(ControlMode.PercentOutput, -leftDirection*ARM_LIFT_CONSTANTS.SPEED);
  //       break;
  //     case OFF:
  //       arm.set(ControlMode.PercentOutput, 0);
  //   }
  // }


}
