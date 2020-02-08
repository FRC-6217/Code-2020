/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.SHOOTER_CONSTANTS;

public class Shooter extends SubsystemBase {
  CANSparkMax topMotor, bottomMotor;
  int topDirection = 1;
  int bottomDirection = 1;
  double topSpeed, bottomSpeed;

  /**
   * Creates a new Shooter.
   */
  public Shooter() {
    topMotor = new CANSparkMax(SHOOTER_CONSTANTS.MOTOR_CONTROLLER_ID_TOP, MotorType.kBrushless);
    bottomMotor = new CANSparkMax(SHOOTER_CONSTANTS.MOTOR_CONTROLLER_ID_BOTTOM, MotorType.kBrushless);
    if (SHOOTER_CONSTANTS.IS_NEGATED_TOP) {
      topDirection = -1;
    }
    if (SHOOTER_CONSTANTS.IS_NEGATED_BOTTOM) {
      bottomDirection = -1;
    }
    SmartDashboard.putNumber("topMotorSpeed", 0.1);
    SmartDashboard.putNumber("bottomMotorSpeed", 0.1);
    topMotor.restoreFactoryDefaults();
    bottomMotor.restoreFactoryDefaults();
    topMotor.setIdleMode(IdleMode.kBrake);
    bottomMotor.setIdleMode(IdleMode.kBrake);
  }

  @Override
  public void periodic() {
    topSpeed = SmartDashboard.getNumber("topMotorSpeed", 0);
    bottomSpeed = SmartDashboard.getNumber("bottomMotorSpeed", 0);
    SmartDashboard.putNumber("topRPM", topMotor.getEncoder().getVelocity());
    SmartDashboard.putNumber("bottomRPM", bottomMotor.getEncoder().getVelocity());
    // This method will be called once per scheduler run
    
  }
  public void on() {
    topMotor.set(topDirection*topSpeed);
    bottomMotor.set(bottomDirection*bottomSpeed);
  }
  public void off() {
    topMotor.set(0);
    bottomMotor.set(0); 
  }
}
