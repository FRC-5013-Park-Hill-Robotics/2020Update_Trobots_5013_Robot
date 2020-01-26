/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
  /**
   * Creates a new Climber.
   */
  public Climber() {

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
