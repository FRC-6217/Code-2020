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
	public double L = 23; //front to back in.
	public double W = 23; //Left to right in.

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
        L *= 0.0245;
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
        //pass joystick requests into chassisSpeed object.
        speeds = new ChassisSpeeds(x, y, z);
        // speeds = new ChassisSpeeds(0, 0, 1);

        // Convert to module states
        moduleStates = swerveKin.toSwerveModuleStates(speeds);
        //Pass module states into wheelDrive objects
        frontLeft.drive(moduleStates[0].speedMetersPerSecond, moduleStates[0].angle.getDegrees());
        frontRight.drive(moduleStates[1].speedMetersPerSecond, moduleStates[1].angle.getDegrees());
        backLeft.drive(moduleStates[2].speedMetersPerSecond, moduleStates[2].angle.getDegrees());
        backRight.drive(moduleStates[3].speedMetersPerSecond, moduleStates[3].angle.getDegrees());
	}
}
