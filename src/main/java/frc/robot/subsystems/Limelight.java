/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase {
  /**
   * Creates a new Limelight.
   */
  public Limelight() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  /**Returns distance to target in inches */
  public int distanceToTarget(){
    //TODO 
    return 0;
  }

  /** REturns if limelight can see defined retroreflective target */
  public boolean hasTarget(){
    //TODO 
    return false;
  }

  /**Returns the angle to targed in degrees negative values to the left and positive to the right
   * used for turn to target
   */
  public double angleToTarget(){
    //TODO
    return 0.0;
  }
}
