/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    //Ball Angler constants
    public static final int BA_LEFT_MOTOR = 42;
    public static final int BA_RIGHT_MOTOR = 45;
    public static final double BA_SPEED = .5;

    //Ball chucker constants
    public static final int BC_TOP_MOTOR = 25;
    public static final int BC_BOTTEM_MOTOR = 26;
    public static final int BC_INTAKE_TO_SHOOTER = 52;
    public static final double BC_SPEED = 0.8;
    public static final double BC_INTAKE_SPEED = .5;


    //Declaration of Button Mapping ports
    public static final int XBOX_PORT = 1;
    public static final int BALL_CHUCKER_BUTTON_FORWARD = 1; //(a) button for now
    public static final int BALL_CHUCKER_BUTTON_BACKWARD = 2; //(b) button for now
    public static final int BALL_CHUCKER_BUTTON_INTAKE = 3; //(x) button for now
    public static final int BALL_ANGLER_BUTTON_UP = 5; //(LB) button for now
    public static final int BALL_ANGLER_BUTTON_DOWN = 6; //(RB) button for now
}
