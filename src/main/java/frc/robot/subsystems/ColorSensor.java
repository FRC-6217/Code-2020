/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ColorSensor extends SubsystemBase {
  private I2C.Port cPort;
  private ColorSensorV3 cs;

  private final ColorMatch match = new ColorMatch();
  
  private final Color blue = ColorMatch.makeColor(Constants.rgbFrcBlue[0],Constants.rgbFrcBlue[1],Constants.rgbFrcBlue[2]);
  private final Color red = ColorMatch.makeColor(Constants.rgbFrcRed[0],Constants.rgbFrcRed[1],Constants.rgbFrcRed[2]);
  private final Color yellow = ColorMatch.makeColor(Constants.rgbFrcYellow[0],Constants.rgbFrcYellow[1],Constants.rgbFrcYellow[2]);
  private final Color green = ColorMatch.makeColor(Constants.rgbFrcGreen[0],Constants.rgbFrcGreen[1],Constants.rgbFrcGreen[2]);

  public ColorSensor() {
    cPort = I2C.Port.kOnboard;
    cs = new ColorSensorV3(cPort);

    match.addColorMatch(blue);
    match.addColorMatch(red);
    match.addColorMatch(yellow);
    match.addColorMatch(green);
  }

  public Color getColor(){
    return cs.getColor();
  }

  public Color getClosestColor(){
    return match.matchClosestColor(getColor()).color;
  }

  public double getPercent(){
    return match.matchClosestColor(getColor()).confidence;
  }

  public int getProximity(){
    return cs.getProximity();
  }
  
  public Color getBlue(){
    return blue;
  }
  public Color getGreen(){
    return green;
  }
  public Color getYellow(){
    return yellow;
  }
  public Color getRed(){
    return red;
  }
}
