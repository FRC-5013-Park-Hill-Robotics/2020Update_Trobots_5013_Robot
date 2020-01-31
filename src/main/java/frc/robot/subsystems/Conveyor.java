/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ConveyorConstants;

public class Conveyor extends SubsystemBase {
  private WPI_TalonSRX leftMotor1 = new WPI_TalonSRX(ConveyorConstants.LEFT_CONVEYOR_MOTOR);
  private WPI_TalonSRX rightMotor1 = new WPI_TalonSRX(ConveyorConstants.RIGHT_CONVEYOR_MOTOR);

  /**
   * Creates a new Conveyor.
   */
  public Conveyor() {
    leftMotor1.configFactoryDefault();
    rightMotor1.configFactoryDefault();
    leftMotor1.setInverted(true);
    rightMotor1.setInverted(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void start() {
    leftMotor1.set(ControlMode.PercentOutput, 0.25);
    rightMotor1.set(ControlMode.PercentOutput, 0.25);
  }
  public void stop() {
    leftMotor1.set(ControlMode.PercentOutput, 0.0);
    rightMotor1.set(ControlMode.PercentOutput, 0.0);
  }
}
//This is Liam's Subsystem.
