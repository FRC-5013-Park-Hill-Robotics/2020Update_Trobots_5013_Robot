/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.Blinkin;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class BlinkinController extends SubsystemBase {
  
  private PWM pwm;
  /**
   * Creates a new BlinkinController.
   */
  public BlinkinController( PWM servo) {
    super();
    this.pwm = servo;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setColorPattern(int color){
    pwm.setRaw(color);
  }
}
