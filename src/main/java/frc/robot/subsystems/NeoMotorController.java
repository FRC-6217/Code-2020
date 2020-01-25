/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import frc.robot.Constants.NeoMotorControllerConstants;

public class NeoMotorController extends SubsystemBase {

  private CANSparkMax m_motor;
  private CANEncoder m_encoder;
  private CANPIDController m_PIDContoller;
  private double PIDMinOutput = -1.0;
  private double PIDMaxOutput = 1.0;
  private int maxRPM = 5700;
  private PIDConstants PID;

  public NeoMotorController(PIDConstants PID) {
    m_motor = new CANSparkMax(NeoMotorControllerConstants.CANid, NeoMotorControllerConstants.motorType);
    m_motor.restoreFactoryDefaults();

    m_PIDContoller = m_motor.getPIDController();

    this.PID = PID;
    updatePID();

    m_encoder = m_motor.getEncoder();
  }
  //Percent
  public void setSpeed(double speed){
    m_motor.set(speed);
  }
  
  public void updatePID(){
    m_PIDContoller.setP(this.PID.kP);
    m_PIDContoller.setI(this.PID.kI);
    m_PIDContoller.setD(this.PID.kD);
    m_PIDContoller.setIZone(this.PID.kIz);
    m_PIDContoller.setFF(this.PID.kFF);
    m_PIDContoller.setOutputRange(PIDMinOutput, PIDMaxOutput);
  }

  public double getPIDMinOutput() {
    return this.PIDMinOutput;
  }

  public void setPIDMinOutput(double PIDMinOutput) {
    this.PIDMinOutput = PIDMinOutput;
  }

  public double getPIDMaxOutput() {
    return this.PIDMaxOutput;
  }

  public void setPIDMaxOutput(double PIDMaxOutput) {
    this.PIDMaxOutput = PIDMaxOutput;
  }

  public int getMaxRPM() {
    return this.maxRPM;
  }

  public void setMaxRPM(int maxRPM) {
    this.maxRPM = maxRPM;
  }

  public PIDConstants getPID() {
    return this.PID;
  }

  public void setPID(PIDConstants PID) {
    this.PID = PID;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public class PIDConstants{
    public double kP;
    public double kI;
    public double kD;
    public double kIz;
    public double kFF;
    
    public PIDConstants(double kP, double kI, double kD, double kIz, double kFF){
      this.kP = kP;
      this.kI = kI;
      this.kD = kD;
      this.kIz = kIz;
      this.kFF = kFF;
    }

    public PIDConstants(double kP, double kI, double kD){
      this(kP, kI, kD, 0, 0);
    }
  }
}
