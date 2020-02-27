/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ConveyorConstants;


public class Conveyor extends SubsystemBase {
  private WPI_TalonSRX leftMotor1 = new WPI_TalonSRX(ConveyorConstants.LEFT_CONVEYOR_MOTOR);
  private WPI_TalonSRX rightMotor1 = new WPI_TalonSRX(ConveyorConstants.RIGHT_CONVEYOR_MOTOR);
  private DigitalInput lowerEye = new DigitalInput(ConveyorConstants.LOWER_EYE);
  private DigitalInput upperEye = new DigitalInput(ConveyorConstants.UPPER_EYE);
  private long startTime=0;
  private double percentOutput;
  public static final double kSpeed = 0.3;//percent output
  public static final double kSpeedForShooter = 0.6;//percent output
  

  /**
   * Creates a new Conveyor.
   */
  public Conveyor() {
    leftMotor1.configFactoryDefault();
    rightMotor1.configFactoryDefault();
    leftMotor1.setInverted(true);
    rightMotor1.setInverted(false);
    leftMotor1.setNeutralMode(NeutralMode.Brake);
    rightMotor1.setNeutralMode(NeutralMode.Brake);
  }

  @Override
  public void periodic() {
    //SmartDashboard.putString("Lower Eye", "" + lowerEye.get());
    //SmartDashboard.putString("Upper Eye", "" + upperEye.get());
    //SmartDashboard.putNumber("TimeInMilis", System.currentTimeMillis());
   // SmartDashboard.putBoolean("Conveyor Moving", isMoving());
    //SmartDashboard.putNumber("start time", startTime);
    if (System.currentTimeMillis() > startTime){ 
      //SmartDashboard.putNumber("Conveyor setting percent", percentOutput);
      rightMotor1.set(ControlMode.PercentOutput, percentOutput);
      leftMotor1.set(ControlMode.PercentOutput, percentOutput);
      startTime = 0;
    } 
  }

  private void setPercentOutput(double targetPercent, Long timeout){
    this.percentOutput = targetPercent;
    if (timeout == 0){
      this.startTime = 0;
    } else if (startTime == 0) { 
      //Don't set if you are delaying already.
      this.startTime = System.currentTimeMillis() + timeout;
    }
    //SmartDashboard.putNumber("Target timeout", startTime);
  }


  public void start(long timeout) {
    //SmartDashboard.putString("Last Conveyor Command", "start:"+timeout);
    if (!isMoving()){
      setPercentOutput(kSpeed, timeout);
    }
     //leftMotor1.set(ControlMode.PercentOutput, .6);
     //rightMotor1.set(ControlMode.PercentOutput, .6);
   }
  public void start() {
    //SmartDashboard.putString("Last Conveyor Command", "start");
   setPercentOutput(kSpeed, 0L);
    //leftMotor1.set(ControlMode.PercentOutput, .6);
    //rightMotor1.set(ControlMode.PercentOutput, .6);
  }
  public void startForShooter() {
    //SmartDashboard.putString("Last Conveyor Command", "start for shooter");
    setPercentOutput(kSpeedForShooter, 0L);
    //leftMotor1.set(ControlMode.PercentOutput, .6);
    //rightMotor1.set(ControlMode.PercentOutput, .6);
  }

  public void reverse() {
    setPercentOutput(-kSpeed, 0L);
    //leftMotor1.set(ControlMode.PercentOutput, -0.6);
    //rightMotor1.set(ControlMode.PercentOutput,- 0.6);
  }
  
  public void stop() {
    //SmartDashboard.putString("Last Conveyor Command", "stop");
    setPercentOutput(0, 0L);
    //leftMotor1.set(ControlMode.PercentOutput, 0.0);
    //rightMotor1.set(ControlMode.PercentOutput, 0.0);
  }

  public boolean isBallReadyForIntake(){
    return lowerEye.get();
  }

  public boolean isBallReadyToShoot(){
    return upperEye.get();
  }

  public boolean isMoving(){
    return Math.abs(percentOutput) > 0.0;
  }
  public void reversestart() {
    setPercentOutput(-kSpeed, 0L);
    //leftMotor1.set(ControlMode.PercentOutput, -0.6);
    //rightMotor1.set(ControlMode.PercentOutput, -0.6);
  }

}

