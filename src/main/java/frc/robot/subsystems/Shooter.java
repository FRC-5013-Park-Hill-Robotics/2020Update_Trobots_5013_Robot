/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
  WPI_TalonFX topMotor = new WPI_TalonFX(ShooterConstants.SHOOTER_TOP_MOTOR);
  WPI_TalonFX bottomMotor = new WPI_TalonFX(ShooterConstants.SHOOTER_BOTTOM_MOTOR);
  /**
   * Creates a new Shooter.
   */
  public Shooter() {
    topMotor.setInverted(false);
    bottomMotor.setInverted(!topMotor.getInverted());
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

  public void test(double bottom, double top){
    topMotor.set(ControlMode.PercentOutput, MathUtil.clamp(top, -1.0, 1.0) );
    bottomMotor.set(ControlMode.PercentOutput,MathUtil.clamp(bottom, -1.0, 1.0) );
    SmartDashboard.putString("topShooterVelocity",""+ topMotor.getSelectedSensorVelocity());
    SmartDashboard.putString("bottomShooterVelocity", ""+bottomMotor.getSelectedSensorVelocity());
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
