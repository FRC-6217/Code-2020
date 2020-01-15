/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import frc.robot.Constants;
import frc.robot.Constants.NeoMotorControllerConstants;
public class NeoMotorController extends SubsystemBase {

  private CANSparkMax m_motor;
  /**
   * Creates a new NeoMotorController.
   */
  public NeoMotorController() {
    m_motor = new CANSparkMax(NeoMotorControllerConstants.CANid, NeoMotorControllerConstants.motorType);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
