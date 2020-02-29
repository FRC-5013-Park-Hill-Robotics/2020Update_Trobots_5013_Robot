/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class HandoffRoller extends SubsystemBase {
  private Conveyor m_conveyor;
  private Intake m_intake;
  private Servo m_servo = new Servo(IntakeConstants.ROLLER_SERVO);
  /**
   * Creates a new HandoffRoller.
   */
  public HandoffRoller(Conveyor conveyor, Intake intake) {
    this.m_conveyor = conveyor;
    this.m_intake = intake;
  }

  @Override
  public void periodic() {
    if (m_conveyor.isMoving() || m_intake.isDown()){
      m_servo.set(0);
    } else {
      m_servo.stopMotor();
    }
  }
}
