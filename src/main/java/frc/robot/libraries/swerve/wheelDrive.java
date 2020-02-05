package frc.robot.libraries.swerve;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANAnalog;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogInput;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpiutil.WPIUtilJNI;
import edu.wpi.first.wpiutil.math.MathUtil;

public class wheelDrive {
    //Encoder, motor, and current channel port for wheel module
    private AnalogInput speedEnc;
    private CANAnalog angleEnc;

    //PID objects for angle and speed PID loops
    private PIDController speedPID;
    private PIDController anglePID;

    //Motors
    private CANSparkMax speedMotor;
    private VictorSPX angleMotor;
    //Reversible Math object
    private reversibleMath revMath;

    //Max voltage of angle encoder
    private final double MIN_VOLTS = 0.015625;
	private final double MAX_VOLTS = 3.2500;
	
	public wheelDrive(int speedMotor, int angleMotor, int angleEncoder) {
        //Pass in Encoder ports to objects
        //this.speedEnc = new AnalogInput(speedEncoder);

        //Reversible Math Object\
        revMath = new reversibleMath();

        //Create PID loops
        //speedPID = new PIDController(1, 0.5, 0, 0.02);
        anglePID = new PIDController(1, 0.5, 0, 0.02);

		//Allow Angle PID to wrap
        anglePID.enableContinuousInput(MIN_VOLTS, MAX_VOLTS);
        
        //Motors
        this.speedMotor = new CANSparkMax(speedMotor, MotorType.kBrushless);
        this.angleMotor = new VictorSPX(angleMotor);
        this.angleEnc = this.speedMotor.getAnalog(CANAnalog.AnalogMode.kAbsolute);
	}

	public void drive(double speed, double angle) {
        SmartDashboard.putNumber("enc " + this.angleMotor.getDeviceID(), angleEnc.getVoltage());
        //Use reverible math class to find shortest rotational distance
        revMath.calculate(angle, angleEnc.getVoltage());
        
        //If the speed direction needs to be reversed because of the reversible math class calculation, reverse the speed direction
        if(!revMath.getDirection()){
            speed *= -1;
        }

        //Set angle to value calculated in the reversible math class
        angle = revMath.getAngle();

        //Convert angle from -1 - 1 to MINVOLTS to MAXVOLTS
        double angleSetpoint = angle;  // Optimization offset can be calculated	here.
        angleSetpoint *= 0.008984375;
        angleSetpoint += 0.015625;
        
        // if (angleSetpoint < MIN_VOLTS) {
		// 	angleSetpoint += MAX_VOLTS;
		// }
		// if (angleSetpoint > MAX_VOLTS) {
		// 	angleSetpoint -= MAX_VOLTS;
        // }
        
        //Calculate Error
        double angleOut = anglePID.calculate(angleEnc.getVoltage(), angleSetpoint);
        MathUtil.clamp(angleOut, -1, 1);
        
        //Set motors
        speedMotor.set(speed);
        angleMotor.set(ControlMode.PercentOutput, angleOut);

	}
}