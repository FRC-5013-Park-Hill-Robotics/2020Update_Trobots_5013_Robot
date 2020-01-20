/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {
  private WPI_TalonFX leftMotor1;
  private WPI_TalonFX leftMotor2;
  private WPI_TalonFX rightMotor1;
  private WPI_TalonFX rightMotor2;

  private SpeedControllerGroup leftMotorGroup;
  private SpeedControllerGroup rightMotorGroup;

  private DifferentialDrive mainDrive;

  public Drivetrain(){
    this.leftMotor1 = new WPI_TalonFX(Constants.LEFT_MOTOR_1_ID);
    this.leftMotor2 = new WPI_TalonFX(Constants.LEFT_MOTOR_2_ID);
    this.rightMotor1 = new WPI_TalonFX(Constants.RIGHT_MOTOR_1_ID);
    this.rightMotor2 = new WPI_TalonFX(Constants.RIGHT_MOTOR_2_ID);

    this.leftMotorGroup = new SpeedControllerGroup(this.leftMotor1, this.leftMotor2);
    this.rightMotorGroup = new SpeedControllerGroup(this.rightMotor1, this.rightMotor2);

    this.mainDrive = new DifferentialDrive(this.leftMotorGroup, this.rightMotorGroup);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    
  }
  public DifferentialDrive getDrive(){
    return this.mainDrive;
  }
}
