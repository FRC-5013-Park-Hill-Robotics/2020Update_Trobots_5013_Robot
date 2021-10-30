/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DriverControllerConstants;
import frc.robot.subsystems.Drivetrain;

public class DriveCommand extends CommandBase {
  private Drivetrain m_drivetrain;
  private GenericHID m_driveController;
  /**
   * Creates a new DriveCommand.
   */
  public DriveCommand(Drivetrain drivetrain, GenericHID driveController) {
    this.m_drivetrain = drivetrain;
    this.m_driveController = driveController;
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drivetrain.arcadeDrive(
      -m_driveController.getRawAxis(DriverControllerConstants.Y_LJOY_ID),
      m_driveController.getRawAxis(DriverControllerConstants.X_RJOY_ID)); 
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
