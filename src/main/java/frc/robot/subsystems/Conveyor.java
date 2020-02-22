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
    SmartDashboard.putString("Lower Eye", "" + lowerEye.get());
    SmartDashboard.putString("Upper Eye", "" + upperEye.get());
  }
  public void start() {
    leftMotor1.set(ControlMode.PercentOutput, .6);
    rightMotor1.set(ControlMode.PercentOutput, .6);
  }

  public void reverse() {
    leftMotor1.set(ControlMode.PercentOutput, -0.6);
    rightMotor1.set(ControlMode.PercentOutput,- 0.6);
  }
  
  public void stop() {
    leftMotor1.set(ControlMode.PercentOutput, 0.0);
    rightMotor1.set(ControlMode.PercentOutput, 0.0);
  }

  public boolean isBallReadyForIntake(){
    return lowerEye.get();
  }

  public boolean isBallReadyToShoot(){
    return upperEye.get();
  }
  public void reversestart() {
    leftMotor1.set(ControlMode.PercentOutput, -0.6);
    rightMotor1.set(ControlMode.PercentOutput, -0.6);
  }

}

