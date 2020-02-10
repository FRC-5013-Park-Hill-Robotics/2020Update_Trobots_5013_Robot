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
  private WPI_TalonFX topMotor = new WPI_TalonFX(ShooterConstants.SHOOTER_TOP_MOTOR);
  private WPI_TalonFX bottomMotor = new WPI_TalonFX(ShooterConstants.SHOOTER_BOTTOM_MOTOR);
  private double speed = 0;
  private double m_targetVelocity = 0;
  /**
   * Creates a new Shooter.
   */
  public Shooter() {
    topMotor.setInverted(true);
    bottomMotor.setInverted(!topMotor.getInverted());
  }

//9600 pulses per 100ms good shooter speed lower wheel
  public void test(double bottom, double top){
    topMotor.set(ControlMode.PercentOutput, MathUtil.clamp(top, -1.0, 1.0) );
    bottomMotor.set(ControlMode.PercentOutput,MathUtil.clamp(bottom, -1.0, 1.0) );
    SmartDashboard.putString("topShooterVelocity",""+ topMotor.getSelectedSensorVelocity());
    SmartDashboard.putString("bottomShooterVelocity", ""+bottomMotor.getSelectedSensorVelocity());
  }

  public void changeSpeed(double velocity){
    setTargetVelocity(getTargetVelocity() + velocity);
    adjustMotorsToTarget();
    SmartDashboard.putString("ShooterPErcent", ""+speed);
    SmartDashboard.putString("topShooterVelocity",""+ topMotor.getSelectedSensorVelocity());
    SmartDashboard.putString("bottomShooterVelocity", ""+bottomMotor.getSelectedSensorVelocity());
  }
  
  public void fire(){
    //TODO 
  }

  public void fireLow(){

  }

  @Override
  public void periodic() {
    adjustMotorsToTarget();
    SmartDashboard.putString("topShooterVelocity",""+ topMotor.getSelectedSensorVelocity());
    SmartDashboard.putString("bottomShooterVelocity", ""+bottomMotor.getSelectedSensorVelocity());
    SmartDashboard.putString("topShooterVelocity rpm",""+ topMotor.getSelectedSensorVelocity() * 600 / 2048);
    SmartDashboard.putString("bottomShooterVelocity rpm", ""+bottomMotor.getSelectedSensorVelocity() * 600 / 2048);
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
    while (!atSpeed()){
       adjustMotorToTarget(bottomMotor, getTargetVelocity());
       adjustMotorToTarget(topMotor, getTopTargetVelocity());
    }
    bottomMotor.set(ControlMode.Velocity, getTargetVelocity());
    topMotor.set(ControlMode.Velocity, getTopTargetVelocity());
  }


  private void adjustMotorToTarget(WPI_TalonFX motor, double target){
    if (motor.getSelectedSensorVelocity() < target ){
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
