/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CompetitionDriveConstants;

public class Drivetrain extends SubsystemBase implements IDriveTrain {
  private WPI_TalonFX leftMotor1 = new WPI_TalonFX(CompetitionDriveConstants.LEFT_MOTOR_1_ID);
  private WPI_TalonFX leftMotor2 = new WPI_TalonFX(CompetitionDriveConstants.LEFT_MOTOR_2_ID);
  private WPI_TalonFX rightMotor1 = new WPI_TalonFX(CompetitionDriveConstants.RIGHT_MOTOR_1_ID);;
  private WPI_TalonFX rightMotor2 = new WPI_TalonFX(CompetitionDriveConstants.RIGHT_MOTOR_2_ID);;

  // The robot's drive
  private final DifferentialDrive m_drive = new DifferentialDrive(leftMotor1, rightMotor1);

  // The left-side drive encoder
  private final Encoder leftEncoder = new Encoder(CompetitionDriveConstants.LEFT_ENCODER_PORTS[0], CompetitionDriveConstants.LEFT_ENCODER_PORTS[1],
      CompetitionDriveConstants.LEFT_REVERSED);

  // The right-side drive encoder
  private final Encoder rightEncoder = new Encoder(CompetitionDriveConstants.RIGHT_ENCODER_PORTS[0], CompetitionDriveConstants.RIGHT_ENCODER_PORTS[1],
      CompetitionDriveConstants.RIGHT_REVERSED);

  public Drivetrain() {
    leftEncoder.setDistancePerPulse(CompetitionDriveConstants.DISTANCE_PER_PULSE);
    rightEncoder.setDistancePerPulse(CompetitionDriveConstants.DISTANCE_PER_PULSE);
    leftMotor2.set(ControlMode.Follower, CompetitionDriveConstants.LEFT_MOTOR_1_ID);
    rightMotor2.set(ControlMode.Follower,  CompetitionDriveConstants.RIGHT_MOTOR_1_ID);
  }

 
  /**
   * Drives the robot using arcade controls.
   *
   * @param fwd the commanded forward movement
   * @param rot the commanded rotation
   */
  public void arcadeDrive(double fwd, double rot) {
    m_drive.arcadeDrive(fwd, rot);
  }

  /**
   * Resets the drive encoders to currently read a position of 0.
   */
  public void resetEncoders() {
    leftEncoder.reset();
    rightEncoder.reset();
  }

  /**
   * Gets the average distance of the two encoders.
   *
   * @return the average of the two encoder readings
   */
  public double getAverageEncoderDistance() {
    return (Math.abs(leftEncoder.getDistance()) + Math.abs(rightEncoder.getDistance())) / 2.0;
  }

  /**
   * Gets the left drive encoder.
   *
   * @return the left drive encoder
   */
  public Encoder getLeftEncoder() {
    return leftEncoder;
  }

  /**
   * Gets the right drive encoder.
   *
   * @return the right drive encoder
   */
  public Encoder getRightEncoder() {
    return rightEncoder;
  }

  /**
   * Sets the max output of the drive.  Useful for scaling the drive to drive more slowly.
   *
   * @param maxOutput the maximum output to which the drive will be constrained
   */
  public void setMaxOutput(double maxOutput) {
    m_drive.setMaxOutput(maxOutput);
  }
}
