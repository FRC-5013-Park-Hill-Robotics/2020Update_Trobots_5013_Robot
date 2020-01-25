/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriverControllerConstants;

public abstract class AbstractDrivetrain extends SubsystemBase implements IDriveTrain {

  /**
   * Sets the max output of the drive.  Useful for scaling the drive to drive more slowly.
   *
   * @param maxOutput the maximum output to which the drive will be constrained
   */
  public void setMaxOutput(double maxOutput) {
    getDrive().setMaxOutput(maxOutput);
  }

  /**
   * Gets the average distance of the two encoders.
   *
   * @return the average of the two encoder readings
   */
  public double getAverageEncoderDistance() {
    return (Math.abs(getLeftEncoder().getDistance()) + Math.abs(getRightEncoder().getDistance())) / 2.0;
  }
  /**
   * Drives the robot using arcade controls.
   *
   * @param fwd the commanded forward movement
   * @param rot the commanded rotation
   */
  public void arcadeDrive(double fwd, double rot) {
    getDrive().arcadeDrive(applyDeadband(fwd),capControl(exponentControl(applyDeadband(rot))));
  }

  public double applyDeadband(double throttle){
    double result = 0;
    if (Math.abs(throttle) > DriverControllerConstants.DEADBAND_VALUE){
      result = throttle;
    }
    return result;
  }

  public double exponentControl(double throttle){
    int sign = 1;
    if (throttle < 0){
      sign = -1;
    }
    return sign * Math.pow(throttle, 2);
  }

  public double capControl(double throttle){
    double result = throttle;
    if (throttle > 0.9){
      result = 0.9;
    }
    return result;
  }
  /**
   * Resets the drive encoders to currently read a position of 0.
   */
  public void resetEncoders() {
    getLeftEncoder().reset();
    getRightEncoder().reset();
  }
}
