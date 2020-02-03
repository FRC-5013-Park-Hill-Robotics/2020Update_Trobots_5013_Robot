/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.CompetitionDriveConstants;
import frc.robot.subsystems.Drivetrain;

public class MotionPlanningTest extends CommandBase {
  /**
   * Creates a new MotionPlanningTest.
   */
  private Drivetrain driveTrain;

  public MotionPlanningTest(DriveTrain drive) {
    addRequirements(drive);
    this.driveTrain = drive;
  }

  public void generateTrajectory() {

    Pose2d start = this.driveTrain.getPose();
    Pose2d end = new Pose2d(.8,.8,
    new Rotation2d(8) );

    var interiorWaypoints = new ArrayList<Translation2d>();
    interiorWaypoints.add(new Translation2d(.6,.6));

    TrajectoryConfig config = new TrajectoryConfig(CompetitionDriveConstants.MAX_VELOCITY,
        CompetitionDriveConstants.MAX_ACCELERATION);

    var trajectory = TrajectoryGenerator.generateTrajectory(start, interiorWaypoints, end, config);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
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
