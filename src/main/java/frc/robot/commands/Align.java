/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Constants.ALIGN_COMMAND_CONSTANTS;
import frc.robot.libraries.Angle;
import frc.robot.subsystems.DriveTrain;
import frc.robot.libraries.DistanceX;
import frc.robot.libraries.DistanceY;
import frc.robot.subsystems.LimeLight;

public class Align extends CommandBase {

  private final DriveTrain train;
  private Joystick joy;
  private LimeLight lime;
  private Angle angle;
  private DistanceY distanceY;
  private DistanceX distanceX;
  private double setZ;
  private double setY;
  private double setX;

  private boolean angleUse = true;
  private boolean distanceYUse = true;
  private boolean distanceXUse = true;

  private double x;
  private double y;
  private double z;



  private PIDController pidZ;
  private double errorZ;
  private double outputZ;
  private boolean outRangeZ;

  private double kPZ = ALIGN_COMMAND_CONSTANTS.kPZ;
  private double kIZ = ALIGN_COMMAND_CONSTANTS.kIZ;
  private double kDZ = ALIGN_COMMAND_CONSTANTS.kDZ;
  private double kMinZ = ALIGN_COMMAND_CONSTANTS.kMinZ;
  private double kMaxZ = ALIGN_COMMAND_CONSTANTS.kMaxZ;

  private PIDController pidY;
  private double errorY;
  private double outputY;
  private boolean outRangeY;

  private double kPY = ALIGN_COMMAND_CONSTANTS.kPY;
  private double kIY = ALIGN_COMMAND_CONSTANTS.kIY;
  private double kDY = ALIGN_COMMAND_CONSTANTS.kDY;
  private double kMinY = ALIGN_COMMAND_CONSTANTS.kMinY;
  private double kMaxY = ALIGN_COMMAND_CONSTANTS.kMaxY;

  private PIDController pidX;
  private double errorX;
  private double outputX;
  private boolean outRangeX;

  private double kPX = ALIGN_COMMAND_CONSTANTS.kPX;
  private double kIX = ALIGN_COMMAND_CONSTANTS.kIX;
  private double kDX = ALIGN_COMMAND_CONSTANTS.kDX;
  private double kMinX = ALIGN_COMMAND_CONSTANTS.kMinX;
  private double kMaxX = ALIGN_COMMAND_CONSTANTS.kMaxX;
  
  public Align(DriveTrain train, Joystick joy, LimeLight lime, Angle angle, double setZ, DistanceY distanceY, double setY, DistanceX distanceX, double setX) {
    addRequirements(train);
    addRequirements(lime);

    this.train = train;
    this.joy = joy;
    this.lime = lime;
    this.angle = angle;
    this.distanceY = distanceY;
    this.distanceX = distanceX;
    this.setZ = setZ;
    this.setY = setY;
    this.setX = setX;

    // lime.visionOn();

    if(angle == null){
      angleUse = false;
    }
    if(distanceY == null){
      distanceYUse = false;
    }
    if(distanceX == null){
      distanceXUse = false;
    }

    pidZ = new PIDController(kPZ, kIZ, kDZ);
    pidY = new PIDController(kPY, kIY, kDY);
    pidY = new PIDController(kPY, kIY, kDY);
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    SmartDashboard.putNumber("KpAlignZ", kPZ);
    SmartDashboard.putNumber("KiAlignZ", kIZ);
    SmartDashboard.putNumber("KdAlignZ", kDZ);
    SmartDashboard.putNumber("KminAlignZ", kMinZ);
    SmartDashboard.putNumber("KmaxAlignZ", kMaxZ);
  
    SmartDashboard.putNumber("KpAlignY", kPY);
    SmartDashboard.putNumber("KiAlignY", kIY);
    SmartDashboard.putNumber("KdAlignY", kDY);
    SmartDashboard.putNumber("KminAlignY", kMinY);
    SmartDashboard.putNumber("KmaxAlignY", kMaxY);
    
    SmartDashboard.putNumber("KpAlignX", kPX);
    SmartDashboard.putNumber("KiAlignX", kIX);
    SmartDashboard.putNumber("KdAlignX", kDX);
    SmartDashboard.putNumber("KMinAlignX", kMinX);
    SmartDashboard.putNumber("KMaxAlignX", kMaxX);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //Dead zone
    x = (Math.abs(joy.getRawAxis(0)) > .2) ? joy.getRawAxis(0) : 0.0;
    y = -((Math.abs(joy.getRawAxis(1)) > .2) ? joy.getRawAxis(1) : 0.0);
    z = -((Math.abs(joy.getRawAxis(2)) > .2) ? joy.getRawAxis(2) : 0.0);

    if(angleUse){
      updatePIDZ();

      double localAngle = angle.getAngle();
      if((localAngle != (Double.POSITIVE_INFINITY))){
        outRangeZ = false;
        errorZ = pidZ.calculate(localAngle, setZ);
        SmartDashboard.putNumber("ErrorZ", errorZ);
        outputZ = MathUtil.clamp(errorZ, -1, 1);
      }
      else{
        outRangeZ = true;
        outputZ = 0;
      }
    }
    else{
      outputZ = 0;
    }

    if(distanceYUse){
      updatePIDY();

      double localDistanceY = distanceY.getDistance();
      if((localDistanceY != (Double.POSITIVE_INFINITY))){
        outRangeY = false;
        errorY = pidY.calculate(localDistanceY, setY);
        SmartDashboard.putNumber("ErrorY", errorY);
        outputY = MathUtil.clamp(errorY, -1, 1);
      }
      else{
        outRangeY = true;
        outputY = 0;
      }
    }
    else{
      outputY = 0;
    }

    if(distanceXUse){
      updatePIDX();

      double localDistanceX = distanceX.getDistance();
      if((localDistanceX != (Double.POSITIVE_INFINITY))){
        outRangeX = false;
        errorX = pidX.calculate(localDistanceX, setX);
        SmartDashboard.putNumber("ErrorX", errorX);
        outputX = MathUtil.clamp(errorX, -1, 1);
      }
      else{
        outRangeX = true;
        outputX = 0;
      }
    }
    else{
      outputX = 0;
    }

    System.out.println("outZ: " + outputZ);

      double zSend = (outputZ * (1 - ALIGN_COMMAND_CONSTANTS.Z_HUMAN_IMPORTANCE)) + (z * ALIGN_COMMAND_CONSTANTS.Z_HUMAN_IMPORTANCE); 
      // zSend = MathUtil.clamp(zSend, -kMaxZ, kMaxZ);
      System.out.println("zSend: " + zSend);

      double ySend = (outputY * (1 - ALIGN_COMMAND_CONSTANTS.Y_HUMAN_IMPORTANCE)) + (y * ALIGN_COMMAND_CONSTANTS.Y_HUMAN_IMPORTANCE); 
      // ySend = MathUtil.clamp(ySend, -kMaxY, kMaxY);

      double xSend = (outputX * (1 - ALIGN_COMMAND_CONSTANTS.X_HUMAN_IMPORTANCE)) + (x * ALIGN_COMMAND_CONSTANTS.X_HUMAN_IMPORTANCE); 
      // xSend = MathUtil.clamp(xSend, -kMaxX, kMaxX);

      train.drive(xSend, ySend, zSend, 1); 
    }

  public void updatePIDZ(){
    boolean isUpdated = false;
  
    if(SmartDashboard.getNumber("KpAlignZ", 0) != kPZ){
      kPZ = SmartDashboard.getNumber("KpAlignZ", 0);
      isUpdated = true;  
    }
    if(SmartDashboard.getNumber("KiAlignZ", 0) != kIZ){
      kIZ = SmartDashboard.getNumber("KiAlignZ", 0);
      isUpdated = true;  
    }
    if(SmartDashboard.getNumber("KdAlignZ", 0) != kDZ){
      kDZ = SmartDashboard.getNumber("KdAlignZ", 0);
      isUpdated = true;  
    }
    if(SmartDashboard.getNumber("KMinAlignZ", 0) != kMinZ){
      kMinZ = SmartDashboard.getNumber("KMinAlignZ", 0);
      isUpdated = true;  
    }
    if(SmartDashboard.getNumber("KMaxAlignZ", 0) != kMaxZ){
      kMaxZ = SmartDashboard.getNumber("KMaxAlignZ", 0);
      isUpdated = true;  
    }

    if(isUpdated){
      System.out.println("Updating");
      pidZ.setP(kPZ);
      pidZ.setI(kIZ);
      pidZ.setD(kDZ);
    }
  }

  public void updatePIDY(){
    boolean isUpdated = false;
  
    if(SmartDashboard.getNumber("KpAlignY", 0) != kPY){
      kPY = SmartDashboard.getNumber("KpAlignY", 0);
      isUpdated = true;  
    }
    if(SmartDashboard.getNumber("KiAlignY", 0) != kIY){
      kIY = SmartDashboard.getNumber("KiAlignY", 0);
      isUpdated = true;  
    }
    if(SmartDashboard.getNumber("KdAlignY", 0) != kDY){
      kDY = SmartDashboard.getNumber("KdAlignY", 0);
      isUpdated = true;  
    }
    if(SmartDashboard.getNumber("KMinAlignY", 0) != kMinY){
      kMinY = SmartDashboard.getNumber("KMinAlignY", 0);
      isUpdated = true;  
    }
    if(SmartDashboard.getNumber("KMaxAlignY", 0) != kMaxY){
      kMaxY = SmartDashboard.getNumber("KMaxAlignY", 0);
      isUpdated = true;  
    }

    if(isUpdated){
      pidY.setP(kPY);
      pidY.setI(kIY);
      pidY.setD(kDY);
    }
  }

  public void updatePIDX(){
    boolean isUpdated = false;
  
    if(SmartDashboard.getNumber("KpAlignX", 0) != kPX){
      kPX = SmartDashboard.getNumber("KpAlignX", 0);
      isUpdated = true;  
    }
    if(SmartDashboard.getNumber("KiAlignX", 0) != kIX){
      kIX = SmartDashboard.getNumber("KiAlignX", 0);
      isUpdated = true;  
    }
    if(SmartDashboard.getNumber("KdAlignX", 0) != kDX){
      kDX = SmartDashboard.getNumber("KdAlignX", 0);
      isUpdated = true;  
    }
    if(SmartDashboard.getNumber("KMinAlignX", 0) != kMinX){
      kMinX = SmartDashboard.getNumber("KMinAlignX", 0);
      isUpdated = true;  
    }
    if(SmartDashboard.getNumber("KMaxAlignX", 0) != kMaxX){
      kMaxX = SmartDashboard.getNumber("KMaxAlignX", 0);
      isUpdated = true;  
    }

    if(isUpdated){
      pidX.setP(kPX);
      pidX.setI(kIX);
      pidX.setD(kDX);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    train.drive(0, 0, 0, 0);
    // lime.visionOff();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    boolean zDone = true;
    boolean yDone = true;
    boolean xDone = true;

    if(angleUse){
      if((train.getGreatestVel() != 0) && (outputZ != 0) && (!outRangeZ)){
        zDone = false;
        System.out.println("Done" + zDone);
      }
    }
    if(distanceYUse){
      if((train.getGreatestVel() != 0) && (outputY != 0) && (!outRangeY)){
        yDone = false;
      }
    }
    if(distanceXUse){
      if(!(train.getGreatestVel() == 0) && (outputY != 0) && (!outRangeX)){
        zDone = false;
      }
    }

    return(zDone && yDone && xDone);
  }

  public String toString(){
    return "AlignZ";
  }
}
