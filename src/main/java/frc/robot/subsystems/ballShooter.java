/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.hal.PDPJNI;
import edu.wpi.first.hal.sim.mockdata.PDPDataJNI;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ballShooter extends SubsystemBase {
  /**
   * Creates a new ballShooter.
   */

  private CANPIDController pid1;
  private CANPIDController pid2;
  private CANEncoder encoder1;
  private CANEncoder encoder2;
  public double kP1, kI1, kD1, kIz1, kFF1, kMaxOutput1, kMinOutput1;
  public double kP2, kI2, kD2, kIz2, kFF2, kMaxOutput2, kMinOutput2;

  private CANSparkMax shooter1;
  private CANSparkMax shooter2;
  private VictorSPX feeder1;
  private VictorSPX feeder2;
  
  private PowerDistributionPanel pdp;

  public ballShooter() {
    shooter1 = new CANSparkMax(25, MotorType.kBrushless);
    shooter2 = new CANSparkMax(26, MotorType.kBrushless);
    resetFactory();
    feeder1 = new VictorSPX(53);
    feeder2 = new VictorSPX(44);

    pdp = new PowerDistributionPanel();

  }

  public void feed(double setpoint) {
    feeder1.set(ControlMode.PercentOutput, setpoint);
    feeder2.set(ControlMode.PercentOutput, setpoint);
  }

  public void resetFactory() {
    shooter1.restoreFactoryDefaults();
    shooter2.restoreFactoryDefaults();
  }

  public void setPid1() {
    pid1 = shooter1.getPIDController();

    encoder1 = shooter1.getEncoder();

    encoder1.setVelocityConversionFactor(1);

    kP1 = 0.0008;
    kI1 = 0;
    kD1 = 0; 
    kIz1 = 10; 
    kFF1 = 0.00018; 
    kMaxOutput1 = 1; 
    kMinOutput1 = -1;

    pid1.setP(kP1);
    pid1.setI(kI1);
    pid1.setD(kD1);
    pid1.setIZone(kIz1);
    pid1.setFF(kFF1);
    pid1.setOutputRange(kMinOutput1, kMaxOutput1);

    SmartDashboard.putNumber("P Gain 1", kP1);
    SmartDashboard.putNumber("I Gain 1", kI1);
    SmartDashboard.putNumber("D Gain 1", kD1);
    SmartDashboard.putNumber("I Zone 1", kIz1);
    SmartDashboard.putNumber("Feed Forward 1", kFF1);
    SmartDashboard.putNumber("Max Output 1", kMaxOutput1);
    SmartDashboard.putNumber("Min Output 1", kMinOutput1);
  }

  public void setPid2() {
    pid2 = shooter2.getPIDController();

    encoder2 = shooter2.getEncoder();

    kP2 = 0.1; 
    kI2 = 0.0001;
    kD2 = 0; 
    kIz2 = 0;
    kFF2 = 0.1; 
    kMaxOutput2 = 1; 
    kMinOutput2 = -1;

    pid2.setP(kP2);
    pid2.setI(kI2);
    pid2.setD(kD2);
    pid2.setIZone(kIz2);
    pid2.setFF(kFF2);
    pid2.setOutputRange(kMinOutput2, kMaxOutput2);

    SmartDashboard.putNumber("P Gain 2", kP2);
    SmartDashboard.putNumber("I Gain 2", kI2);
    SmartDashboard.putNumber("D Gain 2", kD2);
    SmartDashboard.putNumber("I Zone 2", kIz2);
    SmartDashboard.putNumber("Feed Forward 2", kFF2);
    SmartDashboard.putNumber("Max Output 2", kMaxOutput2);
    SmartDashboard.putNumber("Min Output 2", kMinOutput2);
  }

  public void shoot1(double velocity) {
    // read PID coefficients from SmartDashboard
    double p = SmartDashboard.getNumber("P Gain 1", 0);
    double i = SmartDashboard.getNumber("I Gain 1", 0);
    double d = SmartDashboard.getNumber("D Gain 1", 0);
    double iz = SmartDashboard.getNumber("I Zone 1", 0);
    double ff = SmartDashboard.getNumber("Feed Forward 1", 0);
    double max = SmartDashboard.getNumber("Max Output 1", 0);
    double min = SmartDashboard.getNumber("Min Output 1", 0);

    // if PID coefficients on SmartDashboard have changed, write new values to controller
    if((p != kP1)) { pid1.setP(p); kP1 = p; }
    if((i != kI1)) { pid1.setI(i); kI1 = i; }
    if((d != kD1)) { pid1.setD(d); kD1 = d; }
    if((iz != kIz1)) { pid1.setIZone(iz); kIz1 = iz; }
    if((ff != kFF1)) { pid1.setFF(ff); kFF1 = ff; }
    if((max != kMaxOutput1) || (min != kMinOutput1)) { 
      pid1.setOutputRange(min, max); 
      kMinOutput1 = min; kMaxOutput1 = max; 
    }
    // SmartDashboard.getNumber("P Gain 1", 0);
    shooter1.set(velocity);
    //pid1.setReference(velocity, ControlType.kVelocity);
    SmartDashboard.putNumber("Velocity 1", velocity);
    SmartDashboard.putNumber("ProcessVariable 1", encoder1.getVelocity());
    SmartDashboard.putNumber("Current 1", shooter1.getOutputCurrent());
    SmartDashboard.putNumber("Current 1 PDP", pdp.getTotalCurrent());
  }

  public void shoot2(double velocity) {
    // read PID coefficients from SmartDashboard
    double p = SmartDashboard.getNumber("P Gain 2", 0);
    double i = SmartDashboard.getNumber("I Gain 2", 0);
    double d = SmartDashboard.getNumber("D Gain 2", 0);
    double iz = SmartDashboard.getNumber("I Zone 2", 0);
    double ff = SmartDashboard.getNumber("Feed Forward 2", 0);
    double max = SmartDashboard.getNumber("Max Output 2", 0);
    double min = SmartDashboard.getNumber("Min Output 2", 0);

    // if PID coefficients on SmartDashboard have changed, write new values to controller
    if((p != kP2)) { pid2.setP(p); kP2 = p; }
    if((i != kI2)) { pid2.setI(i); kI2 = i; }
    if((d != kD2)) { pid2.setD(d); kD2 = d; }
    if((iz != kIz2)) { pid2.setIZone(iz); kIz2 = iz; }
    if((ff != kFF2)) { pid2.setFF(ff); kFF2 = ff; }
    if((max != kMaxOutput2) || (min != kMinOutput2)) { 
      pid2.setOutputRange(min, max); 
      kMinOutput2 = min; kMaxOutput2 = max; 
    }
    shooter2.set(velocity);
    //pid2.setReference(velocity, ControlType.kVelocity);
    SmartDashboard.putNumber("Velocity 2", velocity);
    SmartDashboard.putNumber("ProcessVariable 2", encoder2.getVelocity());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
