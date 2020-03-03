/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.Blinkin;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
      SmartDashboard.putString("Blinkin State", "Targeting");
      if (m_limelight.hasTarget() && !m_limelight.isOutOfRange()){
        if (m_limelight.isPrimeRange()){
          SmartDashboard.putString("Blinkin State", "PrimeRange");
          setColorPattern(IBlinkinColors.GREEN);
        } else {
          SmartDashboard.putString("Blinkin State", "MediumRange");
          setColorPattern(IBlinkinColors.YELLOW);
        }
      } else {
        SmartDashboard.putString("Blinkin State", "Out Of Range");
        setColorPattern(IBlinkinColors.RED_STROBE);
      }
    } else {
      SmartDashboard.putString("Blinkin State", "Not Targeting");
     setColorPattern(IBlinkinColors.WHITE);
    }
  }

  public void setColorPattern(double color){
    pwm.setSpeed(color);
  }
}
