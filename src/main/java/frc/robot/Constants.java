/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import frc.robot.Gains;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */


public final class Constants {
    public static final int PCM_ID = 0;
    public static final class DriverControllerConstants{
        //=====================Controller Constants=====================
        public static final double DEADBAND_VALUE = .01;
        public static final int XBOX_ID = 0;
        public static final int OPERATOR_CONTROLLER = 3;
        public static final int Y_LJOY_ID = 1;
        public static final int TRIGGER_AXIS = 3; //negative values are right and positive values are left
        public static final int X_RJOY_ID = 4;
    }

    public static final class CompetitionDriveConstants {
        //=====================Motor IDS=====================
        public static final int LEFT_MOTOR_1_ID = 3;
        public static final int LEFT_MOTOR_2_ID = 4;
        public static final int RIGHT_MOTOR_1_ID = 1;
        public static final int RIGHT_MOTOR_2_ID = 2;

        public static final int PIGEON_ID = 5;

        public static final int[] LEFT_ENCODER_PORTS = new int[]{LEFT_MOTOR_1_ID,LEFT_MOTOR_2_ID};
        public static final int[] RIGHT_ENCODER_PORTS = new int[]{RIGHT_MOTOR_1_ID, RIGHT_MOTOR_2_ID};

        public static final boolean LEFT_REVERSED = false;
        public static final boolean RIGHT_REVERSED = true;

        public static final double DISTANCE_PER_PULSE = .0009029;
        public static final boolean GYRO_REVERSED = false;
        
        /**
         * Right now I don't actually know what we want the max velocity and acceleration to be, so I'm just setting them to random values 
         * Reinart
         */
        public static final double MAX_VELOCITY = 1;
        public static final double MAX_ACCELERATION = 1;

        //PID VALUES
        /**
         * Which PID slot to pull gains from. Starting 2018, you can choose from
         * 0,1,2 or 3. Only the first two (0,1) are visible in web-based
         * configuration.
         */
        public static final int kSlotIdx = 0;
        public static final int kPIDLoopIdx = 0;
        public static final int kTimeoutMs = 30; //et to zero to skip waiting for confirmation, set to nonzero to wait and report to DS if action fails.
        public static final Gains kGains = new Gains(0.2, 0.0, 0.0, 0.2, 0, 1.0); //Gains(kp, ki, kd, kf, izone, peak output);
    //drive kinemetics
        public static final double kTrackwidthMeters = 0.5842;
        public static final DifferentialDriveKinematics kDriveKinematics =
            new DifferentialDriveKinematics(kTrackwidthMeters);
        public static final double kMaxSpeedMetersPerSecond = 3;
        public static final double kMaxAccelerationMetersPerSecondSquared = 3;
        public static final double kRamseteB = 2;
        public static final double kRamseteZeta = 0.7;

        public static final double ksVolts =0.338;
        public static final double kvVoltSecondsPerMeter = 2.25;
        public static final double kaVoltSecondsSquaredPerMeter =0.121;
                
       public static final double kPDriveVel = 2.58;

    }

    public static final class ClimberConstants {
        public static final int LEFT_MOTOR = 6;
        public static final int RIGHT_MOTOR = 7;
        public static final int RATCHED_SOLENOID_CHANNEL = 2;
        public static final int EXTENSION_MOTOR = 5;
        public static final Gains kGains = new Gains(0.2, 0.0, 0.0, 0.0, 0, 1.0);
    }
    public static final class ShooterConstants {
        public static final int SHOOTER_TOP_MOTOR = 13;
        public static final int SHOOTER_BOTTOM_MOTOR = 14;
        public static final int ELEVATION_MOTOR = 0;
        public static final int ELEVATION_LOWER_LIMIT = 0;
        public static final int ELEVATION_ENCODER = 0;
        public static final int GATE_SOLENOID_CHANNEL = 0;
        public static final double MIN_TURN = 0.4;
        public static final double HIGH_VELOCITY = 9400.0;
        public static final double LOW_VELOCITY = 4500.0;
        public static final double TOP_PERCENT_OF_BOTTOM = 0.5; //50%;
        public static final double kP = .2;
        public static final double kF = 0;
    }
    public static final class IntakeConstants {
        public static final int INTAKE_MOTOR = 8;
        public static final int DROP_INTAKE_SOLENOID_CHANNEL = 0;
        public static final int RAISE_INTAKE_SOLENOID_CHANNEL = 1;
    }
    public static final class ConveyorConstants {
        public static final int LEFT_CONVEYOR_MOTOR = 9;
        public static final int RIGHT_CONVEYOR_MOTOR = 10;
        public static final int LOWER_EYE = 1;
        public static final int UPPER_EYE = 0;
    }
    public static final class LimelightConstants {
        public static final double CAMERA_ANGLE = 0;
        public static final double CAMERA_HEIGHT = 0;
        public static final double TARGET_HEIGHT = 0;
        public static final int TARGET_PIPELINE = 0;
        public static final int DEFAULT_PIPELINE = 1;
        public static final int DRIVE_PIPELINE = 2;
        public static final int LED_ON = 3;
        public static final int LED_OFF = 1;
        public static final double TURN_TO_TARGET_TOLERANCE = 2.0;


    }
}
