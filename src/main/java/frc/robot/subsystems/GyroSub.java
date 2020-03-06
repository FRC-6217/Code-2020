/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.GYRO_CONSTANTS;
import frc.robot.libraries.Angle;

public class GyroSub extends SubsystemBase {

  private Gyro gyro;
  private Angle angle;
  
  public GyroSub(Angle angle) {
    this.angle = angle;
    gyro = new ADXRS450_Gyro();
    gyro.reset();
    if(GYRO_CONSTANTS.CALIBRATE_GYRO){
      calibrateGyro();
    }
  }

  public void resetGyro(){ //had to change from ResetGryo to ResetGyro so if that wasn´t something I was supposed to change feel free to change it back
		gyro.reset();
	}

	public void calibrateGyro(){
		gyro.calibrate();
	}

	private double get(){
		SmartDashboard.putNumber("Gyro", -gyro.getAngle());
		return -gyro.getAngle();
	}

  @Override
  public void periodic() {
    angle.setAngle(get());
  }
}
