/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  /**
   * Creates a new Shooter.
   */
  public Shooter() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  
  public void turnToTarget(Drivetrain drivetrain, Limelight limelight){
    while(limelight.getTx().getDouble(0.0) != 0){
      // *NOTE 0.03 used in arcade drive should be altered to a constant suited for our bot
      if(limelight.hasTarget()){
        drivetrain.arcadeDrive(0, limelight.getTx().getDouble(0.0)*0.03);
      }
  }
    

  }

  /** Returns the height the shooter needs to elevate to for the shot based on distance to target
   * as determined by the limelight.   This is based on interpolating between known calebrated points
   */
  public int calculateHeight(Limelight limelight){
    int result = 0;
//TODO 
    return result;
  }

  public void fire(){
    //TODO 
  }

  /** Raises a number of encoder pulses upto the soft max */
  public void raise(int pulses){
    //TODO Raises a number of pulses according to the encoder.  We need to determine a max.
  }

  /**Retracts to full down position using the limit switch as the bottom
   * resets encoder to 0
   */
  public void retract(){
    //TODO retract to bottom using limit switch, reset encoder
  }

}
