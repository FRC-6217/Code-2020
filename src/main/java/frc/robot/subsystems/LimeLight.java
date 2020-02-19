/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.libraries.Angle;
import frc.robot.libraries.Distance;
import frc.robot.Constants.LIME_LIGHT_CONSTANTS;

public class LimeLight extends SubsystemBase {
  /**
   * Creates a new LimeLight.
   */
  private Angle angle;
  private Distance distance;

  private NetworkTable table;
	private NetworkTableEntry tx;
	private NetworkTableEntry ty;
  private NetworkTableEntry tv;

  public LimeLight(Angle angle, Distance distance) {
    this.angle = angle;
    this.distance = distance;

    table = NetworkTableInstance.getDefault().getTable("limelight");

  	tx = table.getEntry("tx");
	  ty = table.getEntry("ty");
	  tv = table.getEntry("tv");
  }

  @Override
  public void periodic() {
    if(tv.getDouble(0) == 1){
      SmartDashboard.putBoolean("Target Aquired", true);

      angle.setAngle(-1*ty.getDouble(Double.POSITIVE_INFINITY));

      distance.setDistance((LIME_LIGHT_CONSTANTS.GOAL_HEIGHT - LIME_LIGHT_CONSTANTS.LIME_HEIGHT)/(Math.tan(LIME_LIGHT_CONSTANTS.LIME_ANGLE + tx.getDouble(0))));
    }
    else{
      SmartDashboard.putBoolean("Target Aquired", false);

      angle.setAngle(Double.POSITIVE_INFINITY);

      distance.setDistance(Double.POSITIVE_INFINITY);
    }
    // This method will be called once per scheduler run
  }
}
