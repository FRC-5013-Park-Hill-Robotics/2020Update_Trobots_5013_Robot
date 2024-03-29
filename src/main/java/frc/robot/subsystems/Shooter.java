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
  private double heightVelocity = ShooterConstants.HIGH_VELOCITY;
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
    //String("topShooterVelocity",""+ topMotor.getSelectedSensorVelocity());
    //SmartDashboard.putString("bottomShooterVelocity", ""+bottomMotor.getSelectedSensorVelocity());
  }
  

  public void setPID(WPI_TalonFX motor,double kP, double kI, double kD, double kF) {
    motor.config_kP(0, kP, 30);
    motor.config_kI(0, kI, 30);
		motor.config_kD(0, kD, 30);
		motor.config_kF(0, kF, 30);
  }

  public void spinUp(){
    setTargetVelocity(heightVelocity);
  }
  public void fire(){
    setTargetVelocity(heightVelocity);
    firing = true;
    //String("Shooter is Firing: ", ""+firing);
  }

  public void fireLow(){
    firing = true;
    setTargetVelocity(ShooterConstants.LOW_VELOCITY);
  }

  public void stopFiring(){
    firing = false;
    setTargetVelocity(ShooterConstants.LOW_VELOCITY);
    m_conveyor.stop();
  }

  @Override
  public void periodic() {
    //SmartDashboard.putString("topShooterTargetVelocity",""+ getTopTargetVelocity());
    //SmartDashboard.putString("bottomShooterTargetVelocity", ""+getTargetVelocity());
    //SmartDashboard.putString("topShooterVelocity",""+ topMotor.getSelectedSensorVelocity());
    SmartDashboard.putString("bottomShooterVelocity", ""+bottomMotor.getSelectedSensorVelocity());
    if (firing){
      if (atSpeed()){
        //SmartDashboard.putString("at speed", ""+true);
        m_conveyor.startForShooter();
      } else {
        //SmartDashboard.putString("at speed", ""+false);
        m_conveyor.stop();
        if (m_conveyor.isBallReadyToShoot()){
          m_conveyor.reverse();
        }
      } 
      bottomMotor.set(ControlMode.Velocity,getTargetVelocity());
      topMotor.set(ControlMode.Velocity,getTopTargetVelocity());
    } else {
      bottomMotor.set(ControlMode.Velocity,ShooterConstants.LOW_VELOCITY);
      topMotor.set(ControlMode.Velocity,ShooterConstants.LOW_VELOCITY);
    }

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
  public boolean atSpeed(){
    //Boolean("Is BottomMotor at speed?: ", bottomMotor.getSelectedSensorVelocity() >= getTargetVelocity() );

    return bottomMotor.getSelectedSensorVelocity() >= getTargetVelocity() *.95 &&
      topMotor.getSelectedSensorVelocity() >= getTopTargetVelocity() *.95;
  }

  public void changeHighVelocity(double amount){
    heightVelocity += amount;
  }
  public void resetHighVelocity(){
    heightVelocity = ShooterConstants.HIGH_VELOCITY;
  }
}
