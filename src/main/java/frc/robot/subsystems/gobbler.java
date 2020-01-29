/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class gobbler extends SubsystemBase {

  private VictorSPX gobbler;
  /**
   * Creates a new gobbler.
   */
  public gobbler() {
    gobbler = new VictorSPX(46);
  }

  public void gobble(double set){
    gobbler.set(ControlMode.PercentOutput, set);
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
