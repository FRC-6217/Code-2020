package frc.robot.libraries.swerve;

import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.trajectory.constraint.SwerveDriveKinematicsConstraint;

//class swerveDrive;
public class swerveDrive {

	//constants for the width and length;
	public double L = 21.5; //front to back in.
	public double W = 24.5; //Left to right in.

    //module location objects
    private Translation2d frontLeftLocation;
    private Translation2d frontRightLocation;
    private Translation2d backLeftLocation;
    private Translation2d backRightLocation;

    //swervekinematics object
    private SwerveDriveKinematics swerveKin;

    //chassis speed object
    private ChassisSpeeds speeds;

    //module objects array
    private SwerveModuleState[] moduleStates;

    //wheelDrive objects
	private wheelDrive backRight;
	private wheelDrive backLeft;
	private wheelDrive frontRight;
	private wheelDrive frontLeft;

    //constructor- takes in wheelDrive objects
	public swerveDrive(wheelDrive backRight, wheelDrive backLeft, wheelDrive frontRight, wheelDrive frontLeft) {
        //Convert length and width to meters
        L *= 0.0254;
        W *= 0.0254;

        //Defines locations for swerve modules relative to center
        //TODO-move constants 
        frontLeftLocation = new Translation2d(L/2, W/2);
        frontRightLocation = new Translation2d(L/2, -W/2);
        backLeftLocation = new Translation2d(-L/2, W/2);
        backRightLocation = new Translation2d(-L/2, -W/2);

        //pass module locations into kinematics object
        swerveKin = new SwerveDriveKinematics(frontLeftLocation, frontRightLocation, backLeftLocation, backRightLocation);

        this.backRight = backRight;
		this.backLeft = backLeft;
		this.frontRight = frontRight;
		this.frontLeft = frontLeft;
	}

	public void drive(double x, double y, double z) {
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
		frontLeft.drive(-frontLeftSpeed , frontLeftAngle);
	}
}
