/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants.CompetitionDriveConstants;
import frc.robot.Constants.DriverControllerConstants;
import frc.robot.subsystems.Drivetrain;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class TrenchRunDriveCommand extends PIDCommand {
  /**
   * Creates a new TrenchRunDriveCommand.
   */
  public TrenchRunDriveCommand(Drivetrain driveTrain, XboxController controller) {
    
    super(
        // The controller that the command will use
        new PIDController(CompetitionDriveConstants.kStabilizationGains.kP, 
                          CompetitionDriveConstants.kStabilizationGains.kI,
                          CompetitionDriveConstants.kStabilizationGains.kD),
        // Close the loop on the turn rate
        driveTrain::getWallError,
        // Setpoint is 0
        0,
        // Pipe the output to the turning controls
        output -> driveTrain.arcadeDrive(-controller.getRawAxis(DriverControllerConstants.Y_LJOY_ID), output));

    addRequirements(driveTrain);
    // Set the controller tolerance - the delta tolerance ensures the robot is stationary at the
    // setpoint before it is considered as having reached the reference
    getController().setTolerance(CompetitionDriveConstants.kTurnToleranceDeg , CompetitionDriveConstants.kTurnRateToleranceDegPerS);
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
