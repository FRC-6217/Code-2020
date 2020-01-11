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
    public static final int BA_LEFT_MOTOR = 51;
    public static final int BA_RIGHT_MOTOR = 53;
    public static final double BA_SPEED = .5;

    //Ball chucker constants
    public class BALL_CHUCKER_CONSTANTS {

        public static final int TOP_MOTOR = 50;
        public static final int BOTTEM_MOTOR = 52;
        public static final double SPEED = 0.8;

    }

    //Declaration of Button Mapping ports
    public static final int XBOX_PORT = 1;
    public static final int BALL_CHUCKER_BUTTON_FORWARD = 1; //(a) button for now
    public static final int BALL_CHUCKER_BUTTON_BACKWARD = 2; //(b) button for now
    public static final int BALL_ANGLER_BUTTON_UP = 3; //(x) button for now
    public static final int BALL_ANGLER_BUTTON_DOWN = 4; //(y) button for now
}


/*
PDP (Device ID 30)	Running Application.	PDP	30	1.30	July 14, 2015	3.1	1.1
Victor SPX (Device ID 40)	Running Application.	Victor SPX	40	4.22	Nov 19, 2017	0.2	1.0
Victor SPX (Device ID 41)	Running Application.	Victor SPX	41	3.1	Nov 19, 2017	0.2	1.0
Victor SPX (Device ID 42)	Running Application.	Victor SPX	42	4.22	Nov 19, 2017	0.2	1.0
Victor SPX (Device ID 43)	Running Application.	Victor SPX	43	4.22	Nov 19, 2017	0.2	1.0
Victor SPX (Device ID 44)	Running Application.	Victor SPX	44	4.22	Nov 19, 2017	0.2	1.0
Victor SPX (Device ID 45)	Running Application.	Victor SPX	45	4.22	Nov 19, 2017	0.2	1.0
Victor SPX (Device ID 46)	Running Application.	Victor SPX	46	4.22	Nov 19, 2017	0.2	1.0
Victor SPX (Device ID 47)	Running Application.	Victor SPX	47	4.22	Nov 19, 2017	0.2	1.0
Victor SPX.51.arm1	Running Application.	Victor SPX	51	2.121	Nov 19, 2017	0.2	1.0
Victor SPX.52.shooter2	Running Application.	Victor SPX	52	2.121	Nov 19, 2017	0.2	1.0
Victor SPX.53.arm2	Running Application.	Victor SPX	53	2.121	Nov 19, 2017	0.2	1.0
VictorSPX.50.Shooter1	Running Application.	Victor SPX	50	2.121	Nov 19, 2017	0.2	1.0
*/