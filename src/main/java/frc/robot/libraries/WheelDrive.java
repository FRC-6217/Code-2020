package frc.robot.libraries;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANAnalog;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpiutil.math.MathUtil;

import frc.robot.Constants.WHEEL_DRIVE_CONSTANTS;

public class WheelDrive {
    //PID objects for angle and speed PID loops
    private PIDController speedPID;
    private PIDController anglePID;

    //Motors
    private CANSparkMax speedMotor;
    private VictorSPX angleMotor;
    
    //Encoder
    private CANEncoder speedEnc;
    private CANAnalog angleEnc;

    //TODO Current Channel

    //Calculation variables to enable wheel reversal if greater than 90deg turn neseccary
    private double angleFeedback;
    private double angleReq;
    private double rAngleReq;
    private double f1;
	private double f2;
	private double r1;
    private double r2;
    private double shortest;
    private boolean isF;
	
	public WheelDrive(int speedMotor, int angleMotor) {
        //Create PID loops
        //speedPID = new PIDController(1, 0.5, 0, 0.02);
        anglePID = new PIDController(WHEEL_DRIVE_CONSTANTS.kP, WHEEL_DRIVE_CONSTANTS.kI, WHEEL_DRIVE_CONSTANTS.kZ);

		//Allow Angle PID to wrap
        anglePID.enableContinuousInput(WHEEL_DRIVE_CONSTANTS.MIN_VOLTAGE, WHEEL_DRIVE_CONSTANTS.MAX_VOLTAGE);
        
        //Motors
        this.speedMotor = new CANSparkMax(speedMotor, MotorType.kBrushless);
        this.angleMotor = new VictorSPX(angleMotor);

        this.speedMotor.restoreFactoryDefaults();
        
        //Encoders
        this.speedEnc = this.speedMotor.getEncoder();
        this.speedEnc.setPositionConversionFactor(WHEEL_DRIVE_CONSTANTS.SCALE_FACTOR);
        this.angleEnc = this.speedMotor.getAnalog(CANAnalog.AnalogMode.kAbsolute);
    }
    
    public double getDrivePosition(){
        return speedEnc.getPosition();
    }

    public void resetDrivePosition(){
        speedEnc.setPosition(0);
    }

    public double getDriveVelocity(){
        return speedEnc.getVelocity();
    }

	public void drive(double speed, double angle) {
        //Put Enc values on Smart Dashboard
        SmartDashboard.putNumber("encAngle Position " + this.angleMotor.getDeviceID(), angleEnc.getVoltage());
        SmartDashboard.putNumber("encAngle Request " + this.angleMotor.getDeviceID(), angle);

        SmartDashboard.putNumber("encSpeed Position " + this.speedMotor.getDeviceId(), getDrivePosition());
        SmartDashboard.putNumber("encSpeed Velocity " + this.speedMotor.getDeviceId(), getDriveVelocity());
        SmartDashboard.putNumber("encSpeed Request " + this.speedMotor.getDeviceId(), speed);

        

        /*
        Enables wheels to reverse direction if magnitute of turn angle is greater than 90deg
        */        
        // //reverse requested angle across circle and Wrap back to 0-360 if exceded
        angleReq = angle;
        angleReq *= 180;
        angleReq += 180;
        
        rAngleReq = angleReq + 180;
		rAngleReq = (rAngleReq > 360 ) ? rAngleReq - 360 : rAngleReq;

        //Convert Current angle encoder from MIN_VOLTS - MAX_VOLTS to -1 - 1 so as to match requested value
        angleFeedback = angleEnc.getVoltage();
        angleFeedback *= WHEEL_DRIVE_CONSTANTS.SLOPE_CONVERSION;
        angleFeedback += WHEEL_DRIVE_CONSTANTS.Y_OFFSET_CONVERSION;

        //Find distance between current and requested angle
        //f1 = clock wise
        //f2 = ccw
        //Requires next step to provide actual distances
		f1 = angleFeedback - angleReq;
		f2 = angleReq - angleFeedback;
        
        //Wrap Negative values to postive
        //f1 and f2 will now represent distance between Requested and Current angle going either direction around the circle 
        f1 = (f1 < 0) ? f1 + 360 : f1;
        f2 = (f2 < 0) ? f2 + 360 : f2;

        //Find distance between current and requested angle
        //r1 = clock wise
        //r2 = ccw
        //Requires next step to provide actual distances
		r1 = angleFeedback - rAngleReq;
		r2 = rAngleReq - angleFeedback;
        
        //Wrap Negative values to postive
        //f1 and f2 will now represent distance between Requested and Current angle going either direction around the circle 
        r1 = (r1 < 0) ? r1 + 360 : r1;
        r2 = (r2 < 0) ? r2 + 360 : r2;        

        //Find shortest distance out of f1, f2, r1, r2
		shortest = f1;
		isF = true;

		if(shortest > f2){
			shortest = f2;
		}

		if(shortest > r1){
			shortest = r1;
			isF = false;
		}

		if (shortest > r2){
			shortest = r2;
			isF = false;
        }

        //If driving is reversed, set angle request to the reversed angle request
        if(!isF){
            angleReq = rAngleReq;
            speed *= -1;
        }

        //Convert angle request from -1 - 1 to MIN_VOLTS - MAX_VOLTS
        angleReq -= WHEEL_DRIVE_CONSTANTS.Y_OFFSET_CONVERSION;
        angleReq /= WHEEL_DRIVE_CONSTANTS.SLOPE_CONVERSION;
        

        //Calculate Error
        double angleOut = anglePID.calculate(angleEnc.getVoltage(), angleReq);
        MathUtil.clamp(angleOut, -1, 1);
        
        //Set motors
        speedMotor.set(speed);
        angleMotor.set(ControlMode.PercentOutput, angleOut);

	}
}