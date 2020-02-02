/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimberConstants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber extends SubsystemBase {
  private TalonSRX m_climberMotor;
  private Servo m_climbServo;
  private double m_targetServoPosition;
  private WPI_TalonSRX leftMotor1 = new WPI_TalonSRX(ClimberConstants.LEFT_MOTOR);
  private WPI_TalonSRX rightMotor1 = new WPI_TalonSRX(ClimberConstants.RIGHT_MOTOR);
  /**
   * Creates a new Climber.
   */
  public Climber() {
    m_climberMotor.setInverted(true);
    leftMotor1.configFactoryDefault();
    rightMotor1.configFactoryDefault();
    leftMotor1.setInverted(true);
    rightMotor1.setInverted(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  /**Extends Climber 45 inches*/
  public void extend(){
    // TODO WE will need to have the encoder and determine how many pulses 45 inches is using phoenix tuner.
  }

  /**EXtends Climber fully */
  public void fullyExtend(){
    leftMotor1.set(ControlMode.PercentOutput, 0.25);
    rightMotor1.set(ControlMode.PercentOutput, 0.25);
    //TODO will need to determine pulses to max height, we don't want to run slack into our rope
  }

  public void fullyRetract(){
    //TODO Must use limit switch to prevent damage to telescope rods
  }

 /**  negative values are down positive up, values 0...1 used as motor percent
  must obey limit switches*/
  public void manualExtenstion(double throttle){
    //TODO dont know if we need this or not, may want to limit throttle if we do though.
  }
}
//List of parts: Two Talon SRX's Mag encoder, Rahcet release, Parmeter, Velocity, wpilib import components. Liam