/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.BallShooterConstants;



public class IntakeSystem extends SubsystemBase {
  /**
   * Creates a new IntakeSystem.
   */
  private VictorSPX intake;
  private double intakeSpd = 0;
  
  public IntakeSystem() {
    intake = new VictorSPX(BallShooterConstants.INTAKE_CAN_ID);
  }
  public boolean shoot(boolean override) {
    intake.set(ControlMode.PercentOutput, intakeSpd);


    return true;
  }
  public void turnOffIntake()  {
    intake.set(ControlMode.PercentOutput, 0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    intakeSpd = SmartDashboard.getNumber("intakeSpeed", 0);
  }
}
