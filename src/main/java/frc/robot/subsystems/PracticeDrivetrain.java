/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PWMSparkMax;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants.PracticeDriveConstants;

public class PracticeDrivetrain extends AbstractDrivetrain implements IDriveTrain {
  private PWMSparkMax leftMotor1 = new PWMSparkMax(PracticeDriveConstants.LEFT_MOTOR_1_ID);
  private PWMSparkMax leftMotor2 = new PWMSparkMax(PracticeDriveConstants.LEFT_MOTOR_2_ID);
  private PWMSparkMax rightMotor1 = new PWMSparkMax(PracticeDriveConstants.RIGHT_MOTOR_1_ID);
  private PWMSparkMax rightMotor2 = new PWMSparkMax(PracticeDriveConstants.RIGHT_MOTOR_2_ID);

  SpeedControllerGroup leftMotorGroup = new SpeedControllerGroup(leftMotor1, leftMotor2);
  SpeedControllerGroup rightMotorGroup = new SpeedControllerGroup(rightMotor1, rightMotor2);

  // The robot's drive
  private final DifferentialDrive m_drive = new DifferentialDrive(leftMotorGroup, rightMotorGroup);

  // The left-side drive encoder
  private final Encoder leftEncoder = new Encoder(PracticeDriveConstants.LEFT_ENCODER_PORTS[0], PracticeDriveConstants.LEFT_ENCODER_PORTS[1],
      PracticeDriveConstants.LEFT_REVERSED);

  // The right-side drive encoder
  private final Encoder rightEncoder = new Encoder(PracticeDriveConstants.RIGHT_ENCODER_PORTS[0], PracticeDriveConstants.RIGHT_ENCODER_PORTS[1],
      PracticeDriveConstants.RIGHT_REVERSED);

  public PracticeDrivetrain() {
    leftEncoder.setDistancePerPulse(PracticeDriveConstants.DISTANCE_PER_PULSE);
    rightEncoder.setDistancePerPulse(PracticeDriveConstants.DISTANCE_PER_PULSE);
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
  public DifferentialDrive getDrive(){
    return m_drive;
  }

  @Override
  public void moveTo(double Inches) {
    // NO-OP not implementing for practice drivtrain

  }
}
