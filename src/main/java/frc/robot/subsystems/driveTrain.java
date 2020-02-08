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
import frc.robot.libraries.swerve.swerveDrive;
import frc.robot.libraries.swerve.wheelDrive;

public class DriveTrain extends SubsystemBase {
  
  private wheelDrive backRight;
	private wheelDrive backLeft;
	private wheelDrive frontRight;
	private wheelDrive frontLeft;
	private Gyro gyro;
	private double x1;
	private double y1;

	private swerveDrive swerveDrive;

  /**
   * Creates a new driveTrain.
   */
  public DriveTrain() {

    backRight = new wheelDrive(22, 41, 2);
	backLeft = new wheelDrive(23, 42, 3);
	frontRight = new wheelDrive(21, 40, 1);
	frontLeft = new wheelDrive(24, 43, 0);

		
    swerveDrive = new swerveDrive(backRight, backLeft, frontRight, frontLeft);
    
    gyro = new ADXRS450_Gyro();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void ResetGyro(){ //had to change from ResetGryo to ResetGyro so if that wasn´t something I was supposed to change feel free to change it back
		gyro.reset();
	}

	public double TransformX(double x, double y, boolean isReversed){
		if(isReversed){
			x1 = (x * Math.cos((GetAngle() + 180) * (Math.PI / 180))) - (y * Math.sin((GetAngle() + 180) * (Math.PI / 180)));	
		}
		else{
			x1 = (x * Math.cos(GetAngle() * (Math.PI / 180))) - (y * Math.sin(GetAngle() * (Math.PI / 180)));
		}
		return x1;
	}

	public double TransformY(double x, double y, boolean isReversed){
		if(isReversed){
			y1 = (x * Math.sin((GetAngle() + 180) * (Math.PI / 180))) + (y * Math.cos((GetAngle() + 180) * (Math.PI / 180)));
		}
		else{
			y1 = (x * Math.sin(GetAngle() * (Math.PI / 180))) + (y * Math.cos(GetAngle() * (Math.PI / 180)));
		}
		return y1;
	}

	public double GetAngle(){
		SmartDashboard.putNumber("Gyro", -gyro.getAngle());
		return -gyro.getAngle();
	}

	public void Drive(double x, double y, double z, double governer) {
		swerveDrive.drive(x*governer, y*governer, z*governer);
	}
}
