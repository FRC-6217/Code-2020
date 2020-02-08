/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import javax.security.auth.PrivateCredentialPermission;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.libraries.swerve.swerveDrive;
import frc.robot.libraries.swerve.wheelDrive;

public class driveTrain extends SubsystemBase {
  
	private final double hTower = 79.065;
	private final double hLime = 18.500;
  	private wheelDrive backRight;
	private wheelDrive backLeft;
	private wheelDrive frontRight;
	private wheelDrive frontLeft;
	private Gyro gyro;
	private double x1;
	private double y1;

	private swerveDrive swerveDrive;

	private NetworkTable table;
	private NetworkTableEntry tx;
	private NetworkTableEntry ty;
	private NetworkTableEntry tv;

	private PIDController pidZ;
	private double errorZ;
	private double outputZ;

	private PIDController pidX;
	private double errorX;
	private double outputX;

	private double measurementX;

	private double p10;
	private double i10;
	private double d10;

  /**
   * Creates a new driveTrain.
   */
  public driveTrain() {

    backRight = new wheelDrive(22, 41, 2);
	backLeft = new wheelDrive(23, 42, 3);
	frontRight = new wheelDrive(21, 40, 1);
	frontLeft = new wheelDrive(24, 43, 0);

		
    swerveDrive = new swerveDrive(backRight, backLeft, frontRight, frontLeft);
    
	gyro = new ADXRS450_Gyro();
	ResetGyro();

	
	table = NetworkTableInstance.getDefault().getTable("limelight");

	tx = table.getEntry("tx");
	ty = table.getEntry("ty");
	tv = table.getEntry("tv");

	pidZ = new PIDController(0.01, 0.005, 0);
	errorZ = 0.0;
	outputZ = 0.0;

	p10 = 0.01;
	i10 = 0;
	d10 = 0;

	SmartDashboard.putNumber("p10", p10);
	SmartDashboard.putNumber("i10", i10);
	SmartDashboard.putNumber("d10", d10);

	pidX = new PIDController(p10, i10, d10);
	errorX = 0.0;
	outputX = 0.0;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void ResetGyro(){ //had to change from ResetGryo to ResetGyro so if that wasn´t something I was supposed to change feel free to change it back
		gyro.reset();		
		// gyro.calibrate();
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

	public void ZAlign(double x, double y){

		SmartDashboard.putNumber("LimelightX", tx.getDouble(0.0));

		errorZ = pidZ.calculate(tx.getDouble(0.0), 0);
		
		outputZ = MathUtil.clamp(errorZ, -0.5, 0.5);

		swerveDrive.drive(-y, x, outputZ);
	}

	public void XAlign(double y){
		if(p10 != SmartDashboard.getNumber("p10", 0)){
			p10 = SmartDashboard.getNumber("p10", 0);

			pidX.setP(p10);
		}
		if(i10 != SmartDashboard.getNumber("i10", 0)){
			i10 = SmartDashboard.getNumber("i10", 0);

			pidX.setI(i10);
		}
		if(d10 != SmartDashboard.getNumber("d10", 0)){
			d10 = SmartDashboard.getNumber("d10", 0);

			pidX.setD(d10);
		}
		double xt = (Math.abs(tx.getDouble(0.0)) > 3) ? tx.getDouble(0.0) : 0.0;
		errorZ = pidZ.calculate(xt, 0);

		SmartDashboard.putNumber("ErrorZ", errorZ);
		double angleGyro = GetAngle();
		while (angleGyro > 360 || angleGyro < 0){
			if(angleGyro > 360){
				angleGyro -= 360;
			}
			else{
				angleGyro += 360;
			}
		}

		if(tv.getDouble(0.0) == 1.0){
			measurementX = (hTower - hLime) * Math.sin(Math.toRadians(angleGyro)) / Math.tan(Math.toRadians(ty.getDouble(0.0) + 10));
		}
		else{
			measurementX = 0;
		}
		measurementX = (Math.abs(measurementX) > 5) ? measurementX : 0.0;
		
		SmartDashboard.putNumber("MeasurementX", measurementX);
		SmartDashboard.putNumber("TV10", tv.getDouble(0.0));
		
		errorX = pidX.calculate(measurementX, 0);

		outputZ = MathUtil.clamp(errorZ, -0.5, 0.5);
		outputX = MathUtil.clamp(errorX, -0.5, 0.5);
		SmartDashboard.putNumber("OutX", outputX);

		swerveDrive.drive(-outputX, y, outputZ);
	}

}
