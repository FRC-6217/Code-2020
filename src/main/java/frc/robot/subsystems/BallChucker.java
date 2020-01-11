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
import frc.robot.Constants;

public class BallChucker extends SubsystemBase {
  private VictorSPX topMotor;
  private VictorSPX botMotor;
  
  public BallChucker() {
    topMotor = new VictorSPX(Constants.BALL_CHUCKER_CONSTANTS.TOP_MOTOR);
    botMotor = new VictorSPX(Constants.BALL_CHUCKER_CONSTANTS.BOTTEM_MOTOR);
  }


  public void runFow(){
    topMotor.set(ControlMode.PercentOutput, Constants.BALL_CHUCKER_CONSTANTS.SPEED);
    botMotor.set(ControlMode.PercentOutput, -Constants.BALL_CHUCKER_CONSTANTS.SPEED);
  }

  public void runBac(){
    topMotor.set(ControlMode.PercentOutput, -Constants.BALL_CHUCKER_CONSTANTS.SPEED);
    botMotor.set(ControlMode.PercentOutput, Constants.BALL_CHUCKER_CONSTANTS.SPEED);
  }

  public void stop(){
    topMotor.set(ControlMode.PercentOutput, 0);
    botMotor.set(ControlMode.PercentOutput, 0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
