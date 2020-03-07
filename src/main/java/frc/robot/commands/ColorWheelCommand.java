/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.COLOR_STATE;
import frc.robot.subsystems.ColorWheel;
import edu.wpi.first.wpilibj.util.Color;

public class ColorWheelCommand extends CommandBase {
  private ColorWheel colorWheel;
  private COLOR_STATE state;
  private Color lastColor;
  private Color currentColor;
  private int spinCounter = 1;
  /**
   * Creates a new ColorWheelCommand.
   */
  public ColorWheelCommand(ColorWheel colorWheel, COLOR_STATE state) {
    addRequirements(colorWheel);
    this.colorWheel = colorWheel;
    this.state = state;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    colorWheel.forwards();
    lastColor = colorWheel.getClosestColor();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute(){
    if(state == COLOR_STATE.SPIN){
      currentColor = colorWheel.getClosestColor();
      if(currentColor != lastColor) {
        spinCounter++;
        lastColor = currentColor;
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    colorWheel.off();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    switch (state) {
      case SPIN:
      return spinCounter >= 28;

    }
  }
}
