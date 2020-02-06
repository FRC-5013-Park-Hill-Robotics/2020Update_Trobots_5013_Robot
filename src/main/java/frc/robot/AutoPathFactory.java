/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Constants.CompetitionDriveConstants;
import frc.robot.Constants.PracticeDriveConstants;
import frc.robot.subsystems.Drivetrain;

/**
 * Add your docs here.
 */
public class AutoPathFactory {

    public static RamseteCommand generateTrajectory(Drivetrain driveTrain) {
        Pose2d start = driveTrain.getPose();
        Pose2d end = new Pose2d(.8,.8,
        new Rotation2d(8) );
    
        var interiorWaypoints = new ArrayList<Translation2d>();
        interiorWaypoints.add(new Translation2d(.6,.6));
    
        TrajectoryConfig config = new TrajectoryConfig(CompetitionDriveConstants.MAX_VELOCITY,
            CompetitionDriveConstants.MAX_ACCELERATION);
    
        var trajectory = TrajectoryGenerator.generateTrajectory(start, interiorWaypoints, end, config);

        return createRamseteCommand(trajectory, driveTrain);
      }

    public static RamseteCommand createRamseteCommand(Trajectory trajectory, Drivetrain driveTrain){
        RamseteCommand ramseteCommand = new RamseteCommand(
            trajectory,
            driveTrain::getPose,
            new RamseteController(PracticeDriveConstants.kRamseteB, PracticeDriveConstants.kRamseteZeta),
            new SimpleMotorFeedforward(PracticeDriveConstants.ksVolts,
                                    PracticeDriveConstants.kvVoltSecondsPerMeter,
                                    PracticeDriveConstants.kaVoltSecondsSquaredPerMeter),
            PracticeDriveConstants.kDriveKinematics,
            driveTrain::getWheelSpeeds,
            new edu.wpi.first.wpilibj.controller.PIDController(PracticeDriveConstants.kPDriveVel, 0, 0),
            new edu.wpi.first.wpilibj.controller.PIDController(PracticeDriveConstants.kPDriveVel, 0, 0),
            driveTrain::tankDriveVolts,
            driveTrain
            );
            return ramseteCommand;
    }  
}
