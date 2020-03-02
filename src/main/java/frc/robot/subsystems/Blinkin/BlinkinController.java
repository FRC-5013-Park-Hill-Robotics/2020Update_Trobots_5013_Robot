/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.Blinkin;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Limelight;

public class BlinkinController extends SubsystemBase {
  private Limelight m_limelight;
  private PWM pwm;
  /**
   * Creates a new BlinkinController.
   */
  public BlinkinController( PWM servo, Limelight limelight) {
    super();
    this.pwm = servo;
    this.m_limelight = limelight;
  }

  @Override
  public void periodic() {
    if (m_limelight.isTargeting()){
      if (m_limelight.hasTarget() || m_limelight.isPrimeRange()){
        if (m_limelight.isPrimeRange()){
          setColorPattern(IBlinkinColors.GREEN);
        } else {
          setColorPattern(IBlinkinColors.YELLOW);
        }
      } else {
        setColorPattern(IBlinkinColors.RED_STROBE);
      }
    } else {
     setColorPattern(IBlinkinColors.WHITE);
    }
  }

  public void setColorPattern(int color){
    pwm.setRaw(color);
  }
}
