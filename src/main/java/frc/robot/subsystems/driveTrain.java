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
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.libraries.swerve.WheelDrive;
import frc.robot.Constants.DRIVE_TRAIN_CONSTANTS;

public class DriveTrain extends SubsystemBase {
  	
	//constants for the width and length;
	private final double L = DRIVE_TRAIN_CONSTANTS.LENGTH; //front to back in.
	private final double W = DRIVE_TRAIN_CONSTANTS.WIDTH; //Left to right in.

    //module location objects
    private Translation2d frontLeftLocation;
    private Translation2d frontRightLocation;
    private Translation2d backLeftLocation;
    private Translation2d backRightLocation;

	//Wheel Drive Objetcs
	private WheelDrive backRight;
	private WheelDrive backLeft;
	private WheelDrive frontRight;
	private WheelDrive frontLeft;

    //swervekinematics object
    private SwerveDriveKinematics swerveKin;

    //chassis speed object
    private ChassisSpeeds speeds;

    //module array object
    private SwerveModuleState[] moduleStates;

	//Gyro object
	private Gyro gyro;

	//Transform variables
	private double x1;
	private double y1;

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
  public DriveTrain() {
	//Defines locations for swerve modules relative to center 
	frontLeftLocation = new Translation2d(L/2, W/2);
	frontRightLocation = new Translation2d(L/2, -W/2);
	backLeftLocation = new Translation2d(-L/2, W/2);
	backRightLocation = new Translation2d(-L/2, -W/2);

	//pass module locations into kinematics object
	swerveKin = new SwerveDriveKinematics(frontLeftLocation, frontRightLocation, backLeftLocation, backRightLocation);
	
	//Wheel Drive Modules
    backRight = new WheelDrive(DRIVE_TRAIN_CONSTANTS.BR_SPEED_MOTOR, DRIVE_TRAIN_CONSTANTS.BR_ANGLE_MOTOR);
	backLeft = new WheelDrive(DRIVE_TRAIN_CONSTANTS.BL_SPEED_MOTOR, DRIVE_TRAIN_CONSTANTS.BL_ANGLE_MOTOR);
	frontRight = new WheelDrive(DRIVE_TRAIN_CONSTANTS.FR_SPEED_MOTOR, DRIVE_TRAIN_CONSTANTS.FR_ANGLE_MOTOR);
	frontLeft = new WheelDrive(DRIVE_TRAIN_CONSTANTS.FL_SPEED_MOTOR, DRIVE_TRAIN_CONSTANTS.FL_ANGLE_MOTOR);
    
	gyro = new ADXRS450_Gyro();
	ResetGyro();
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
		x *= governer;
		y *= governer;
		z *= governer;
		SmartDashboard.putNumber("xSpeed", x);
		SmartDashboard.putNumber("ySpeed", y);
		SmartDashboard.putNumber("zSpeed", z);

		 // //pass joystick requests into chassisSpeed object.
        // speeds = new ChassisSpeeds(x, y, z);
        // // speeds = new ChassisSpeeds(0, 0, 1);

        // // Convert to module states
        // moduleStates = swerveKin.toSwerveModuleStates(speeds);
        // //Pass module states into wheelDrive objects
        // frontLeft.drive(moduleStates[0].speedMetersPerSecond, moduleStates[0].angle.getDegrees());
        // frontRight.drive(moduleStates[1].speedMetersPerSecond, moduleStates[1].angle.getDegrees());
        // backLeft.drive(moduleStates[2].speedMetersPerSecond, moduleStates[2].angle.getDegrees());
        // backRight.drive(moduleStates[3].speedMetersPerSecond, moduleStates[3].angle.getDegrees());

        /*
		 * First we need to find the radius of the circle the robot is going to spin and
		 * for that we use the Pythagorean theorem and y *= -1 means y is assigned the
		 * value of y * -1
		 */
		double r = Math.sqrt((L * L) + (W * W));
		y *= -1;
		// The next thing we do is assign the value a to the equation. a makes the robot
		// go backwards
		// Note: The L/r part makes the code in radians not degrees

		// a makes it go backwards;
		double a = x - z * (L / r);
		// b makes it go forwards.
		double b = x + z * (L / r);
		// c makes it go left.
		double c = y - z * (W / r);
		// d makes it go right.
		double d = y + z * (W / r);

		/*
		 * Now we do the Pythagorean theorem again because the robot will only go in a
		 * straight line so we find the hypotenuse using our above variables for all
		 * direction combos.
		 */
		double backRightSpeed = Math.sqrt((a * a) + (d * d));
		double backLeftSpeed = Math.sqrt((a * a) + (c * c));
		double frontRightSpeed = Math.sqrt((b * b) + (d * d));
		double frontLeftSpeed = Math.sqrt((b * b) + (c * c));

		/*
		 * In order to find the angles we need to turn based on the inputs of the
		 * controller the code turns the tangent of the coordinates (x,y) into
		 * (radius,angle) to find the angle that applies to our robot. Then it's divided
		 * by pi to turn from radians into degrees.
		 */
		double backLeftAngle = Math.atan2(a, c) / Math.PI;
		double backRightAngle = Math.atan2(a, d) / Math.PI;
		double frontRightAngle = Math.atan2(b, d) / Math.PI;
		double frontLeftAngle = Math.atan2(b, c) / Math.PI;

		/*
		 * Lastly the results of the above code are all plugged back in to be used
		 * later.
		 */
		backRight.drive(backRightSpeed , backRightAngle);
		backLeft.drive(backLeftSpeed , backLeftAngle);
		frontRight.drive(frontRightSpeed , frontRightAngle);
		frontLeft.drive(frontLeftSpeed , frontLeftAngle);
	}
}
