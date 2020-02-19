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
    setPID(bottomMotor,ShooterConstants.kP, 0, 0, ShooterConstants.kF);
    setPID(topMotor,ShooterConstants.kP, 0, 0, ShooterConstants.kF);
  }

  //method for testing.
  public void changeSpeed(double velocity){
    setTargetVelocity(getTargetVelocity() + velocity);
    //adjustMotorsToTarget();
    firing = true;
    SmartDashboard.putString("topShooterVelocity",""+ topMotor.getSelectedSensorVelocity());
    SmartDashboard.putString("bottomShooterVelocity", ""+bottomMotor.getSelectedSensorVelocity());
  }
  

  public void setPID(WPI_TalonFX motor,double kP, double kI, double kD, double kF) {
    motor.config_kP(0, kP, 30);
    motor.config_kI(0, kI, 30);
		motor.config_kD(0, kD, 30);
		motor.config_kF(0, kF, 30);
  }

  public void fire(){
    setTargetVelocity(ShooterConstants.HIGH_VELOCITY);
    firing = true;
    SmartDashboard.putString("Shooter is Firing: ", ""+firing);
  }

  public void fireLow(){
    firing = true;
    setTargetVelocity(ShooterConstants.LOW_VELOCITY);
  }

  public void stopFiring(){
    firing = false;
    setTargetVelocity(0.0);
    m_conveyor.stop();
  }

  @Override
  public void periodic() {
    SmartDashboard.putString("topShooterTargetVelocity",""+ getTopTargetVelocity());
    SmartDashboard.putString("bottomShooterTargetVelocity", ""+getTargetVelocity());
    SmartDashboard.putString("topShooterVelocity",""+ topMotor.getSelectedSensorVelocity());
    SmartDashboard.putString("bottomShooterVelocity", ""+bottomMotor.getSelectedSensorVelocity());
    if (firing){
      if (atSpeed()){
        SmartDashboard.putString("at speed", ""+true);
        m_conveyor.start();
      } else {
        SmartDashboard.putString("at speed", ""+false);
        m_conveyor.stop();
        if (m_conveyor.isBallReadyToShoot()){
   //       m_conveyor.reverse();
        }
      } 
      bottomMotor.set(ControlMode.Velocity,getTargetVelocity());
      topMotor.set(ControlMode.Velocity,getTopTargetVelocity());
    } else {
      bottomMotor.set(ControlMode.PercentOutput,0);
      topMotor.set(ControlMode.PercentOutput,0);
    }
    //if we want to shoot and we are not at speed we need to stop the conveyor
   /* if (firing && !atSpeed()){
      m_conveyor.stop();
    }

    adjustMotorsToTarget();
    //if we are at speed which we should be, fire away
    /*f (firing && atSpeed()){
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
/*
  private void adjustMotorsToTarget(){
  //  while (!atSpeed()){
    
       adjustMotorToTarget(bottomMotor, getTargetVelocity());
       adjustMotorToTarget(topMotor, getTopTargetVelocity());
    
   // }
    SmartDashboard.putString("topShooterVelocity",""+ topMotor.getSelectedSensorVelocity());
    SmartDashboard.putString("bottomShooterVelocity", ""+bottomMotor.getSelectedSensorVelocity());
    SmartDashboard.putString("topShooterVelocity rpm",""+ topMotor.getSelectedSensorVelocity() * 600 / 2048);
    SmartDashboard.putString("bottomShooterVelocity rpm", ""+bottomMotor.getSelectedSensorVelocity() * 600 / 2048);
  }


  private void adjustMotorToTarget(WPI_TalonFX motor, double target){
    SmartDashboard.putNumber("Target Speed: ", target);
    if ((motor.getSelectedSensorVelocity() < target) && (target != 0)){
      SmartDashboard.putNumber("Velocity is less than Target: ", motor.getSelectedSensorVelocity());
      //100% output to get up to speed;
      motor.set(ControlMode.PercentOutput, 1.0);
    } else {
      SmartDashboard.putNumber("Velocity is greater than Target: ", motor.getSelectedSensorVelocity());
      //maintain velocity     
      motor.set(ControlMode.Velocity, target);
    }
  }
*/
  private boolean atSpeed(){
    SmartDashboard.putBoolean("Is BottomMotor at speed?: ", bottomMotor.getSelectedSensorVelocity() >= getTargetVelocity() );

    return bottomMotor.getSelectedSensorVelocity() >= getTargetVelocity() *.95 &&
      topMotor.getSelectedSensorVelocity() >= getTopTargetVelocity() *.95;
  }
}
