/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.BallShooterConstants;

public class ballShooter extends SubsystemBase {
  /**
   * Creates a new ballShooter.
   */
  private CANSparkMax topMotor;
  private CANSparkMax bottomMotor;
  private double bottomRPM = 0;
  private double topRPM = 0;

  private CANEncoder topEncoder;
  private CANEncoder bottomEncoder;
  private CANPIDController topPID;
  private CANPIDController bottomPID;

  public ballShooter() {
    topMotor = new CANSparkMax(BallShooterConstants.TOP_CAN_ID, MotorType.kBrushless);
    bottomMotor = new CANSparkMax(BallShooterConstants.BOTTOM_CAN_ID, MotorType.kBrushless);
    topMotor.restoreFactoryDefaults();
    bottomMotor.restoreFactoryDefaults();
    topEncoder = topMotor.getEncoder();
    bottomEncoder = bottomMotor.getEncoder();
    topPID = topMotor.getPIDController();
    bottomPID = bottomMotor.getPIDController();
    setPID();
  }
  public void turnOnShooter() {
    topPID.setReference(-topRPM, ControlType.kVelocity);
    bottomPID.setReference(bottomRPM, ControlType.kVelocity);
    //topMotor.set(-topRPM);
    //bottomMotor.set(bottomRPM);

  }
  public void turnOffShooter() {
    //topMotor.set(0);
   // bottomMotor.set(0);
    topPID.setReference(0, ControlType.kVelocity);
    bottomPID.setReference(0, ControlType.kVelocity);
  }
 
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    topRPM = SmartDashboard.getNumber("set top RPM", 0);
    bottomRPM = SmartDashboard.getNumber("set bottom RPM", 0);
    SmartDashboard.putNumber("actual top RPM", topEncoder.getVelocity());
    SmartDashboard.putNumber("actual bottom RPM", bottomEncoder.getVelocity());
  }
  private void setPID() {
    double kP = 0.1;
    double kI = 0;
    double kD = 0;
    double kIz = 0;
    double kFF = 0;
    double kMinOutput = -1;
    double kMaxOutput = 1;

    topPID.setP(kP);
    topPID.setI(kI);
    topPID.setD(kD);
    topPID.setIZone(kIz);
    topPID.setFF(kFF);
    topPID.setOutputRange(kMinOutput, kMaxOutput);
    
    bottomPID.setP(kP);
    bottomPID.setI(kI);
    bottomPID.setD(kD);
    bottomPID.setIZone(kIz);
    bottomPID.setFF(kFF);
    bottomPID.setOutputRange(kMinOutput, kMaxOutput);


  }
}
