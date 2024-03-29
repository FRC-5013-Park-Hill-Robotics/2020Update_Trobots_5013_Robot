/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CompetitionDriveConstants;
import frc.robot.Constants.DriverControllerConstants;

public class Drivetrain extends SubsystemBase {
  private final WPI_TalonFX leftMotor1 = new WPI_TalonFX(CompetitionDriveConstants.LEFT_MOTOR_1_ID);
  private final WPI_TalonFX leftMotor2 = new WPI_TalonFX(CompetitionDriveConstants.LEFT_MOTOR_2_ID);
  private final WPI_TalonFX rightMotor1 = new WPI_TalonFX(CompetitionDriveConstants.RIGHT_MOTOR_1_ID);;
  private final WPI_TalonFX rightMotor2 = new WPI_TalonFX(CompetitionDriveConstants.RIGHT_MOTOR_2_ID);;
  private final TalonSRX pigeonTalon = new TalonSRX(CompetitionDriveConstants.PIGEON_ID);
  private final PigeonIMU pigeonIMU = new PigeonIMU(pigeonTalon);
  // Odometry class for tracking robot pose
  private final DifferentialDriveOdometry m_odometry;

  private interface DriveMode {
    public void drive(double throttle, double rotation);
  }

  public final DriveMode FINE_DRIVE = new DriveMode(){
    public void drive(double throttle, double rotation){
      getDrive().arcadeDrive(0.6 * applyDeadband(throttle), 0.6*applyDeadband(rotation)/2);
    }
  };

  public final DriveMode NORMAL_DRIVE = new DriveMode(){
    public void drive(double throttle, double rotation){
      getDrive().arcadeDrive(applyDeadband(throttle), applyDeadband(rotation));
    }
  };

  private DriveMode m_currentDriveMode = NORMAL_DRIVE;

  // The robot's drive
  private final DifferentialDrive m_drive = new DifferentialDrive(leftMotor1, rightMotor1);

  public Drivetrain() {
    /* factory default values */
    rightMotor1.configFactoryDefault();
    rightMotor2.configFactoryDefault();
    leftMotor1.configFactoryDefault();
    leftMotor2.configFactoryDefault();
  
    rightMotor1.setNeutralMode(NeutralMode.Brake);
    rightMotor2.setNeutralMode(NeutralMode.Brake);
    leftMotor1.setNeutralMode(NeutralMode.Brake);
    leftMotor2.setNeutralMode(NeutralMode.Brake);

    rightMotor1.configOpenloopRamp(.5);
    rightMotor2.configOpenloopRamp(.5);
    leftMotor1.configOpenloopRamp(.5);
    leftMotor2.configOpenloopRamp(.5);

    /* set up followers */
    leftMotor2.set(ControlMode.Follower, CompetitionDriveConstants.LEFT_MOTOR_1_ID);
    rightMotor2.set(ControlMode.Follower, CompetitionDriveConstants.RIGHT_MOTOR_1_ID);

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

    // setPIDValues(rightMotor1);
    // setPIDValues(leftMotor1);
    resetEncoders();
    m_odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading()));

  }

  private void setPIDValues(final WPI_TalonFX motor) {

    motor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, CompetitionDriveConstants.kSlotIdx,
        CompetitionDriveConstants.kTimeoutMs);
    /* Set relevant frame periods to be at least as fast as periodic rate */
    motor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, CompetitionDriveConstants.kTimeoutMs);
    motor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, CompetitionDriveConstants.kTimeoutMs);

    /* Set the peak and nominal outputs */
    motor.configNominalOutputForward(0, CompetitionDriveConstants.kTimeoutMs);
    motor.configNominalOutputReverse(0, CompetitionDriveConstants.kTimeoutMs);
    motor.configPeakOutputForward(1, CompetitionDriveConstants.kTimeoutMs);
    motor.configPeakOutputReverse(-1, CompetitionDriveConstants.kTimeoutMs);

    /* Set Motion Magic gains in slot0 - see documentation */
    motor.selectProfileSlot(CompetitionDriveConstants.kSlotIdx, CompetitionDriveConstants.kPIDLoopIdx);
    motor.config_kF(CompetitionDriveConstants.kSlotIdx, CompetitionDriveConstants.kGains.kF,
        CompetitionDriveConstants.kTimeoutMs);
    motor.config_kP(CompetitionDriveConstants.kSlotIdx, CompetitionDriveConstants.kGains.kP,
        CompetitionDriveConstants.kTimeoutMs);
    motor.config_kI(CompetitionDriveConstants.kSlotIdx, CompetitionDriveConstants.kGains.kI,
        CompetitionDriveConstants.kTimeoutMs);
    motor.config_kD(CompetitionDriveConstants.kSlotIdx, CompetitionDriveConstants.kGains.kD,
        CompetitionDriveConstants.kTimeoutMs);

    /* Set acceleration and vcruise velocity - see documentation */
    motor.configMotionCruiseVelocity(15000, CompetitionDriveConstants.kTimeoutMs);
    motor.configMotionAcceleration(6000, CompetitionDriveConstants.kTimeoutMs);

    /* Zero the sensor once on robot boot up */
    motor.setSelectedSensorPosition(0, CompetitionDriveConstants.kPIDLoopIdx, CompetitionDriveConstants.kTimeoutMs);
  }

  public DifferentialDrive getDrive() {
    return m_drive;
  }


  public void arcadeDrive(final double fwd, final double rot) {
    m_currentDriveMode.drive(fwd, rot);
  }

  public void resetEncoders() {
    rightMotor1.setSelectedSensorPosition(0, CompetitionDriveConstants.kSlotIdx, CompetitionDriveConstants.kTimeoutMs);
    leftMotor1.setSelectedSensorPosition(0, CompetitionDriveConstants.kSlotIdx, CompetitionDriveConstants.kTimeoutMs);
  }

  /**
   * Sets the max output of the drive. Useful for scaling the drive to drive more
   * slowly.
   *
   * @param maxOutput the maximum output to which the drive will be constrained
   */
  public void setMaxOutput(final double maxOutput) {
    getDrive().setMaxOutput(maxOutput);
  }

  public double applyDeadband(final double throttle) {
    double result = 0;
    if (Math.abs(throttle) > DriverControllerConstants.DEADBAND_VALUE) {
      result = throttle;
    }
    return result;
  }

  public double exponentControl(final double throttle) {
    int sign = 1;
    if (throttle < 0) {
      sign = -1;
    }
    return sign * Math.pow(throttle, 2);
  }

  public double capControl(final double throttle) {
    double result = throttle;
    if (throttle > 0.9) {
      result = 0.9;
    }
    return result;
  }

  /**
   * Zeroes the heading of the robot.
   */
  public void zeroHeading() {
    pigeonIMU.setYaw(0,
    CompetitionDriveConstants.kTimeoutMs);
    pigeonIMU.setAccumZAngle(0,
    CompetitionDriveConstants.kTimeoutMs);
    System.out.println("[Pigeon] All sensors zeroed. /n");
  }

  /**
   * Returns the heading of the robot.
   *
   * @return the robot's heading in degrees, from -180 to 180
   */
  public double getHeading() {
    final double[] yprArray = new double[3];
    pigeonIMU.getYawPitchRoll(yprArray);
    return Math.IEEEremainder(yprArray[0], 360) * (CompetitionDriveConstants.GYRO_REVERSED ? -1.0 : 1.0);
  }

  /**
   * gets the distance of the right motor
   * @return the robot's right motor distance in meters
   */
  public double getRightDistanceMeters(){
    //SmartDashboard.putNumber("Right Encoder", rightMotor1.getSelectedSensorPosition());
    return CompetitionDriveConstants.DISTANCE_PER_PULSE_METERS*rightMotor1.getSelectedSensorPosition();
  }

  /**
   * gets the distance of the Left motor
   * @return the robot's Left motor distance in meters
   */
  public double getLeftDistanceMeters(){
    //SmartDashboard.putNumber("LEft Encoder", leftMotor1.getSelectedSensorPosition());
    return CompetitionDriveConstants.DISTANCE_PER_PULSE_METERS*leftMotor1.getSelectedSensorPosition();
  }

  /**
   * Returns the currently-estimated pose of the robot.
   *
   * @return The pose.
   */
  public Pose2d getPose() {
    return m_odometry.getPoseMeters();
  }

  /**
   * Returns the current wheel speeds of the robot. in pulses per 100ms
   *
   * @return The current wheel speeds.
   */
  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(
      getLeftDistanceMeters(),
    getRightDistanceMeters());
  }

  /**
   * Resets the odometry to the specified pose.
   *
   * @param pose The pose to which to set the odometry.
   */
  public void resetOdometry(final Pose2d pose) {
    resetEncoders();
    m_odometry.resetPosition(pose, Rotation2d.fromDegrees(getHeading()));
  }

  /**
   * Gets the average distance of the two encoders.
   *
   * @return the average of the two encoder readings in meters
   */
  public double getAverageEncoderDistance() {
    //String("Distances" , "Left" + getLeftDistanceMeters() + " right " + getRightDistanceMeters() );
    return (getLeftDistanceMeters() + 
    getRightDistanceMeters() )
    / 2.0;
  }
  @Override
  public void periodic() {
    m_odometry.update(
      Rotation2d.fromDegrees(getHeading()), 
      getLeftDistanceMeters(), 
      getRightDistanceMeters());
      //SmartDashboard.putString("Pose", m_odometry.getPoseMeters().toString());
     // SmartDashboard.putNumber("Left Distance", getLeftDistanceMeters());
      //SmartDashboard.putNumber("Right Distance", getRightDistanceMeters());
      SmartDashboard.putNumber("Heading", getHeading());
  }

  public void tankDriveVolts(final double leftVolts, final double rightVolts) {
    leftMotor1.setVoltage(leftVolts);
    rightMotor1.setVoltage(rightVolts);
    m_drive.feed();
  }
  
  public void setDriveModeNormal(){
    this.m_currentDriveMode = NORMAL_DRIVE;
  }

  public void setDriveModeFine(){
    this.m_currentDriveMode = FINE_DRIVE;
  }

  /*Return distance from wall of front time of flight sensor in inches */
  public double getFrontRightWallDistance(){
    return 0;
  }

  /*Return distance from wall of front time of flight sensor in inches */
  public double getRearRightWallDistance(){
    return 0;
  }

  /* Using time of flight sensors front and back measure distance from the wall
    If we are too far from the wall for determined distance return negative error value 
    to turn robot to the right, if both sensors are too close return a positive error value
    to turn left otherwise return a negative value if front sensor is farther 
    than back sensor to turn right if front sensor is closer return positive value to turn left 
    if out of trenchrun range return 0*/
  public double getWallError(){
    double frontRight = getFrontRightWallDistance();
    double rearRight = getRearRightWallDistance();
    double result = 0;
    
    if (frontRight < CompetitionDriveConstants.kTrenchRunWallDistance && rearRight < CompetitionDriveConstants.kTrenchRunWallDistance){
      //we are too close to the wall need to turn left
      result =Math.max(Math.min(frontRight, rearRight)/CompetitionDriveConstants.kTrenchRunWallDistance * CompetitionDriveConstants.kMaxTrenchTurn, getAngleToWall());
    } else if (frontRight > CompetitionDriveConstants.kTrenchRunWallDistance && rearRight > CompetitionDriveConstants.kTrenchRunWallDistance){
      result = Math.min((Math.min(frontRight, rearRight)-CompetitionDriveConstants.kTrenchRunWallDistance)/CompetitionDriveConstants.kTrenchRunWallDistance * -CompetitionDriveConstants.kMaxTrenchTurn,getAngleToWall());
    } else {
      result = getAngleToWall();
    }
    return result;

  }

  public double getAngleToWall(){
    double front = getFrontRightWallDistance();
    double rear = getRearRightWallDistance();
    double adjacent = CompetitionDriveConstants.kDistanceBetweenSensors;
    double opposite = rear-front; //may be negative

    return Math.toDegrees(Math.atan(opposite/adjacent));
  }
}
