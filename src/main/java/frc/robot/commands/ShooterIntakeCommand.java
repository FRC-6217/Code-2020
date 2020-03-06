/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.STATE;
import frc.robot.subsystems.ShooterIntake;

public class ShooterIntakeCommand extends CommandBase {
  ShooterIntake intake;
  STATE state;
  Timer timer; 
  double timeOut;
  
  public ShooterIntakeCommand(ShooterIntake intake, STATE state) {
    addRequirements(intake);
    this.intake = intake;
    this.state = state;
  }

  public ShooterIntakeCommand(ShooterIntake intake, STATE state, double timeOut) {
    addRequirements(intake);
    this.intake = intake;
    this.state = state;

    this.timer = new Timer();
    this.timeOut = timeOut;
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if(timer != null){
      timer.start();
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    switch (state) {
      case FORWARDS:
        intake.forward();
        break;
      case REVERSE:
        intake.reverse();
        break;
      case OFF:
        intake.off();
        break;
      case UP:
        intake.off();
        break;
      case DOWN:
        intake.off();
        break;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.off();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(timer != null){
      return (timer.get() >= timeOut);
    }
    else{
      return false;
    }
  }
}
