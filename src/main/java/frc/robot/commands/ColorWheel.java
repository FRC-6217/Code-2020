/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ColorSensor;

public class ColorWheel extends CommandBase {
  /**
   * Creates a new ColorWheel.
   */
  private Color colorMatch;
  private int proxmity;
  private ColorSensor m_colorSensor;

  public ColorWheel(ColorSensor m_colorSensor) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_colorSensor);
    this.m_colorSensor = m_colorSensor;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    colorMatch = m_colorSensor.getClosestColor();
    proxmity = m_colorSensor.getProximity();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    String colorString = "Unknown";

    if(colorMatch == m_colorSensor.getBlue()){
      colorString = "blue";
    }
    else if(colorMatch == m_colorSensor.getGreen()){
      colorString = "green";

    }
    else if(colorMatch == m_colorSensor.getRed()){
      colorString = "red";

    }
    else if(colorMatch == m_colorSensor.getYellow()){
      colorString = "yellow";

    }

    SmartDashboard.putString("Color", colorString);
    SmartDashboard.putNumber("Confidence", m_colorSensor.getPercent());
    SmartDashboard.putNumber("Proxmity", m_colorSensor.getProximity());
    SmartDashboard.putNumber("Red", m_colorSensor.getColor().red);
    SmartDashboard.putNumber("Green", m_colorSensor.getColor().green);
    SmartDashboard.putNumber("Blue", m_colorSensor.getColor().blue);
  
    colorMatch = m_colorSensor.getClosestColor();
    proxmity = m_colorSensor.getProximity();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
