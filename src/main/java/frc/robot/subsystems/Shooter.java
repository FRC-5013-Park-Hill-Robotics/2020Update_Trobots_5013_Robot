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
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
  private WPI_TalonFX topMotor = new WPI_TalonFX(ShooterConstants.SHOOTER_TOP_MOTOR);
  private WPI_TalonFX bottomMotor = new WPI_TalonFX(ShooterConstants.SHOOTER_BOTTOM_MOTOR);
  private boolean firing = false;
  private double m_targetVelocity = 0;
  private Conveyor m_conveyor;

  /**
   * Creates a new Shooter.
   */
  public Shooter(Conveyor conveyor) {
    topMotor.setInverted(true);
    bottomMotor.setInverted(!topMotor.getInverted());
    m_conveyor = conveyor;
  }

  //method for testing.
  public void changeSpeed(double velocity){
    setTargetVelocity(getTargetVelocity() + velocity);
    adjustMotorsToTarget();
    firing = true;
    SmartDashboard.putString("topShooterVelocity",""+ topMotor.getSelectedSensorVelocity());
    SmartDashboard.putString("bottomShooterVelocity", ""+bottomMotor.getSelectedSensorVelocity());
  }
  
  public void fire(){
    setTargetVelocity(ShooterConstants.HIGH_VELOCITY);
    firing = true;
  }

  public void fireLow(){
    firing = true;
    setTargetVelocity(ShooterConstants.LOW_VELOCITY);
  }

  public void stopFiring(){
    firing = false;
    setTargetVelocity(0.0);
  }

  @Override
  public void periodic() {
    //if we want to shoot and we are not at speed we need to stop the conveyor
   /* if (firing && !atSpeed()){
      m_conveyor.stop();
    }*/
    adjustMotorsToTarget();
    //if we are at speed which we should be, fire away
   /* if (firing && atSpeed()){
      m_conveyor.start();
    }*/
  }

  public void setTargetVelocity(double bottomMotorTarget){
    m_targetVelocity = bottomMotorTarget;
  }

  public double getTopTargetVelocity(){
    return m_targetVelocity * ShooterConstants.TOP_PERCENT_OF_BOTTOM;
  }

  public double getTargetVelocity(){
    return m_targetVelocity;
  }

  private void adjustMotorsToTarget(){
  //  while (!atSpeed()){
    
       adjustMotorToTarget(bottomMotor, getTargetVelocity());
       adjustMotorToTarget(topMotor, getTopTargetVelocity());
    
   // }
    bottomMotor.set(ControlMode.Velocity, getTargetVelocity());
    topMotor.set(ControlMode.Velocity, getTopTargetVelocity());
    SmartDashboard.putString("topShooterVelocity",""+ topMotor.getSelectedSensorVelocity());
    SmartDashboard.putString("bottomShooterVelocity", ""+bottomMotor.getSelectedSensorVelocity());
    SmartDashboard.putString("topShooterVelocity rpm",""+ topMotor.getSelectedSensorVelocity() * 600 / 2048);
    SmartDashboard.putString("bottomShooterVelocity rpm", ""+bottomMotor.getSelectedSensorVelocity() * 600 / 2048);
  }


  private void adjustMotorToTarget(WPI_TalonFX motor, double target){
    if ((motor.getSelectedSensorVelocity() < target) && (target != 0)){
      //100% output to tet up to speed;
      motor.set(ControlMode.PercentOutput, 1.0);
    } else {
      //maintain velocity     
      motor.set(ControlMode.Velocity, target);
    }
  }

  private boolean atSpeed(){
    return bottomMotor.getSelectedSensorVelocity() >= getTargetVelocity() &&
      topMotor.getSelectedSensorVelocity() >= getTopTargetVelocity();
  }
}
