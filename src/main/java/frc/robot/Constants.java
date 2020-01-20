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
    //=====================Motor IDS=====================
    public static final int LEFT_MOTOR_1_ID = 1;
    public static final int LEFT_MOTOR_2_ID = 2;
    public static final int RIGHT_MOTOR_1_ID = 6;
    public static final int RIGHT_MOTOR_2_ID = 7;




    //=====================Controller Constants=====================
    public static final double DEADBAND_VALUE = .3;
    public static final int XBOX_ID = 0;
    public static final int Y_LJOY_ID = 1;
    public static final int X_RJOY_ID = 4;

    public static final int[] LEFT_ENCODER_PORTS = new int[]{LEFT_MOTOR_1_ID,LEFT_MOTOR_2_ID};
    public static final int[] RIGHT_ENCODER_PORTS = new int[]{RIGHT_MOTOR_1_ID, RIGHT_MOTOR_2_ID};

    public static final boolean LEFT_REVERSED = false;
    public static final boolean RIGHT_REVERSED = true;

    public static final int ENCODER_PULSES_PER_REVOLUTION = 2048;
    public static final double WHEEL_DIAMETER_INCHES = 8;
    public static final double DISTANCE_PER_PULSE =
        // Assumes the encoders are directly mounted on the wheel shafts
        (WHEEL_DIAMETER_INCHES * Math.PI) / (double) ENCODER_PULSES_PER_REVOLUTION;
    

}
