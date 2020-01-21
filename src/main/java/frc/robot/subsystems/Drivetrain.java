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
import frc.robot.Constants.CompetitionDriveConstants;
import com.ctre.phoenix.motorcontrol.InvertType;

public class Drivetrain extends AbstractDrivetrain implements IDriveTrain {
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

    /* factory default values */
    rightMotor1.configFactoryDefault();
    rightMotor2.configFactoryDefault();
    leftMotor1.configFactoryDefault();
    leftMotor2.configFactoryDefault(); 

       /* set up followers */
    leftMotor2.set(ControlMode.Follower, CompetitionDriveConstants.LEFT_MOTOR_1_ID);
    rightMotor2.set(ControlMode.Follower,  CompetitionDriveConstants.RIGHT_MOTOR_1_ID);

    /* [3] flip values so robot moves forward when stick-forward/LEDs-green */
    rightMotor1.setInverted(CompetitionDriveConstants.RIGHT_REVERSED); // !< Update this
    leftMotor1.setInverted(CompetitionDriveConstants.LEFT_REVERSED); // !< Update this

    /*
      * set the invert of the followers to match their respective master controllers
      */
    rightMotor2.setInverted(InvertType.FollowMaster);
    leftMotor2.setInverted(InvertType.FollowMaster);

    /*
      * [4] adjust sensor phase so sensor moves positive when Talon LEDs are green
      */
    rightMotor1.setSensorPhase(true);
    leftMotor1.setSensorPhase(true);

    /*
      * WPI drivetrain classes defaultly assume left and right are opposite. call
      * this so we can apply + to both sides when moving forward. DO NOT CHANGE
      */
    m_drive.setRightSideInverted(false);
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
}
