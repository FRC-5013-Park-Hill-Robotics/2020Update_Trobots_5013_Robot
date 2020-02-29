/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants.CompetitionDriveConstants;
import frc.robot.subsystems.Drivetrain;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
public class TurnToAngle extends PIDCommand {
  private final Drivetrain drive;

  private final PIDController controller;

  public TurnToAngle(double  targetAngleDegrees, Drivetrain drive) {
    super(new PIDController(CompetitionDriveConstants.kTurnP, CompetitionDriveConstants.kTurnI, CompetitionDriveConstants.kTurnD),
    // Close loop on heading
    () -> -drive.getHeading(),
    // Set reference to target
    targetAngleDegrees,
    // Pipe output to turn robot
    output -> {
      double turn =  output + Math.signum(output)*CompetitionDriveConstants.kTurnFriction;
      drive.tankDriveVolts(turn, -turn);
    },
    // Require the drive
    drive);
    SmartDashboard.putNumber("TurnToAngle", targetAngleDegrees);
     this.drive = drive;
    this.controller = getController();
    // Set the controller to be continuous (because it is an angle controller)
    controller.enableContinuousInput(-180, 180);
    // Set the controller tolerance - the delta tolerance ensures the robot is stationary at the
    // setpoint before it is considered as having reached the reference
    controller.setTolerance(CompetitionDriveConstants.kTurnToleranceDeg , CompetitionDriveConstants.kTurnRateToleranceDegPerS);
    
  }

  @Override
  public boolean isFinished() {
    // End when the controller is at the reference.
    SmartDashboard.putNumber("Setpoint",controller.getSetpoint());
    SmartDashboard.putNumber("pos error", controller.getPositionError());
    SmartDashboard.putBoolean("Turn By Angle finished", controller.atSetpoint());
    return controller.atSetpoint();
  }

  public double getPositionError() {
    return controller.getPositionError();
  }

  public double getVelocityError() {
    return controller.getVelocityError();
  }
}
