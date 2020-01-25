/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ballShooter extends SubsystemBase {
  /**
   * Creates a new ballShooter.
   */

  private CANPIDController pid1;
  private CANPIDController pid2;
  private CANEncoder encoder;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

  private CANSparkMax shooter1;
  private CANSparkMax shooter2;
  private VictorSPX feeder;

  double p;
  double i;
  double d;
  double iz;
  double ff;
  double max;
  double min;
  double velocity;

  public ballShooter() {
    shooter1 = new CANSparkMax(25, MotorType.kBrushless);
    shooter2 = new CANSparkMax(26, MotorType.kBrushless);
    resetFactory();
    feeder = new VictorSPX(53);

  }

  public void feed(double setpoint) {
    feeder.set(ControlMode.PercentOutput, setpoint);
  }

  public void resetFactory() {
    shooter1.restoreFactoryDefaults();
    shooter2.restoreFactoryDefaults();
  }

  public void setPid1() {
    pid1 = shooter1.getPIDController();

    encoder = shooter1.getEncoder();

    kP = 0.1; 
    kI = 0;
    kD = 0; 
    kIz = 0; 
    kFF = 0; 
    kMaxOutput = 2500; 
    kMinOutput = 0;

    pid2.setP(kP);
    pid2.setI(kI);
    pid2.setD(kD);
    pid2.setIZone(kIz);
    pid2.setFF(kFF);
    pid2.setOutputRange(kMinOutput, kMaxOutput);

    SmartDashboard.putNumber("P Gain", kP);
    SmartDashboard.putNumber("I Gain", kI);
    SmartDashboard.putNumber("D Gain", kD);
    SmartDashboard.putNumber("I Zone", kIz);
    SmartDashboard.putNumber("Feed Forward", kFF);
    SmartDashboard.putNumber("Max Output", kMaxOutput);
    SmartDashboard.putNumber("Min Output", kMinOutput);
    SmartDashboard.putNumber("Set Velocity", velocity);

  }

  public void setPid2() {
    pid2 = shooter2.getPIDController();

    encoder = shooter2.getEncoder();

    kP = 0.1; 
    kI = 0;
    kD = 0; 
    kIz = 0; 
    kFF = 0; 
    kMaxOutput = 2500; 
    kMinOutput = 0;

    pidController.setP(kP);
    pidController.setI(kI);
    pidController.setD(kD);
    pidController.setIZone(kIz);
    pidController.setFF(kFF);
    pidController.setOutputRange(kMinOutput, kMaxOutput);

    SmartDashboard.putNumber("P Gain", kP);
    SmartDashboard.putNumber("I Gain", kI);
    SmartDashboard.putNumber("D Gain", kD);
    SmartDashboard.putNumber("I Zone", kIz);
    SmartDashboard.putNumber("Feed Forward", kFF);
    SmartDashboard.putNumber("Max Output", kMaxOutput);
    SmartDashboard.putNumber("Min Output", kMinOutput);
    SmartDashboard.putNumber("Set Velocity", velocity);

  }

  public void shoot1(double velocity) {
    pidController.setReference(velocity, ControlType.kVelocity);
    shooter1.set(velocity);
    SmartDashboard.putNumber("Velocity", velocity);
    SmartDashboard.putNumber("ProcessVariable", encoder.getVelocity());
    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
