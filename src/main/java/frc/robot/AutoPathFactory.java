/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import frc.robot.Constants.CompetitionDriveConstants;
import frc.robot.subsystems.Drivetrain;

/**
 * Add your docs here.
 */
public class AutoPathFactory {

    public void generateTrajectory(Drivetrain driveTrain) {
        Pose2d start = driveTrain.getPose();
        Pose2d end = new Pose2d(.8,.8,
        new Rotation2d(8) );
    
        var interiorWaypoints = new ArrayList<Translation2d>();
        interiorWaypoints.add(new Translation2d(.6,.6));
    
        TrajectoryConfig config = new TrajectoryConfig(CompetitionDriveConstants.MAX_VELOCITY,
            CompetitionDriveConstants.MAX_ACCELERATION);
    
        var trajectory = TrajectoryGenerator.generateTrajectory(start, interiorWaypoints, end, config);

        this.createRamsetteCommand(trajectory, driveTrain);
      }

    public void createRamsetteCommand(Trajectory trajectory, Drivetrain driveTrain){

    }  
}
