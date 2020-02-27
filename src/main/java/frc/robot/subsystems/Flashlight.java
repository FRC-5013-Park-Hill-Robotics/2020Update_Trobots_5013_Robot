/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Flashlight extends SubsystemBase {

  //The flashlight is wired into the compressor slot.
  private Compressor m_light;
  private boolean m_activated;
  /**
   * Creates a new Flashlight.
   */
  public Flashlight(int pcmId) {
    super();
    this.m_light = new Compressor(pcmId);

  }

  public boolean isActivated(){
    return m_activated;
  }

  private boolean isOn(){
    return m_light.getClosedLoopControl();
  }
  @Override
  public void periodic() {
    //we wanted activated light to blink
    if (System.currentTimeMillis() % 200  < 100){ //if the remainder of the time/200 (every 1/5 second) 
      if (!isOn()) {
        m_light.start();
      }
    } else {
      if (isOn()) {
        m_light.stop();
      }
    }
  }

  public void on(){
    m_activated = true;
  }  
  
  public void off(){
    m_activated = false;
  }

  public void toggle(){
    m_activated = !m_activated;
  }

}
