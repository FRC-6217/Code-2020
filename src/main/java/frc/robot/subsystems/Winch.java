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
import frc.robot.Constants.WINCH_CONSTANTS;

public class Winch extends SubsystemBase {
  /**
   * Creates a new Winch.
   */
  VictorSPX winch;
  int direction = 1;

  public Winch() {
    winch = new VictorSPX(WINCH_CONSTANTS.MOTOR_CONTROLLER_ID);
    if (WINCH_CONSTANTS.IS_NEGATED) {
      direction = -1;
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void on() {
    winch.set(ControlMode.PercentOutput, direction*WINCH_CONSTANTS.SPEED);
  }

  public void reverse() {
    winch.set(ControlMode.PercentOutput, -direction*WINCH_CONSTANTS.SPEED);
  }

  public void off() {
    winch.set(ControlMode.PercentOutput, 0);
  }
}
