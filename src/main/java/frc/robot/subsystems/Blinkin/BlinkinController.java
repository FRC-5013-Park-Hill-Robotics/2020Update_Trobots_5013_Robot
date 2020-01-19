/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.Blinkin;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class BlinkinController extends SubsystemBase {
  
  private Servo servo;
  /**
   * Creates a new BlinkinController.
   */
  public BlinkinController(  Servo servo) {
    super();
    this.servo = servo;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setColorPattern(int color){
    servo.setPosition(color);
  }
}
