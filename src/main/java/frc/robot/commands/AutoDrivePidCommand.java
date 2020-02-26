/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants.CompetitionDriveConstants;
import frc.robot.subsystems.Drivetrain;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class AutoDrivePidCommand extends PIDCommand {
  private final Drivetrain drivetrain;
  private final PIDController controller;
  /**
   * Creates a new AutoDrivePidCommand.
   */
  public AutoDrivePidCommand(double distance, Drivetrain drivetrain) {
    super(new PIDController(CompetitionDriveConstants.kGains.kP, CompetitionDriveConstants.kGains.kI, CompetitionDriveConstants.kGains.kD),
    // Close loop on heading
    () -> drivetrain.getAverageEncoderDistance(),
    // Set reference to target
    Units.feetToMeters(distance) + drivetrain.getAverageEncoderDistance(),
    // Pipe output to turn robot
    output -> {
      drivetrain.arcadeDrive(output, 0);
    },
    // Require the drive
    drivetrain);
        this.drivetrain = drivetrain;
        this.controller = getController();
        // Set the controller to be continuous (because it is an angle controller)
        controller.enableContinuousInput(-1, 1);
        // Set the controller tolerance - the delta tolerance ensures the robot is stationary at the
        // setpoint before it is considered as having reached the reference
        controller.setTolerance(.1 , .5);}

  public boolean isFinished() {
    // End when the controller is at the reference.
    return controller.atSetpoint();
  }
}
