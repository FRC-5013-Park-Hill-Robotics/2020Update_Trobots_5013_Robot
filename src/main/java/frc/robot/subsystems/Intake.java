/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase {
  private TalonSRX intakeMotor = new TalonSRX(IntakeConstants.INTAKE_MOTOR);

  //Dropping the intake is set up as forward and raising it as reverse, may have to change based on mechanics and wiring.
  private DoubleSolenoid intakeSolenoid = new DoubleSolenoid(Constants.PCM_ID,IntakeConstants.DROP_INTAKE_SOLENOID_CHANNEL, IntakeConstants.LIFT_INTAKE_SOLENOID_CHANNEL);
  
  /**
   * Creates a new Intake.
   */
  public Intake() {
    intakeMotor.configFactoryDefault();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void dropIntake(){
    if (!intakeSolenoid.isFwdSolenoidBlackListed()){
      intakeSolenoid.set(DoubleSolenoid.Value.kForward);
      intakeMotor.set(ControlMode.PercentOutput,1.0);
      intakeSolenoid.set(DoubleSolenoid.Value.kOff);
    }

  }

  public void raiseIntake(){
    if (!intakeSolenoid.isRevSolenoidBlackListed()){
      intakeMotor.set(ControlMode.PercentOutput,0.0);
      intakeSolenoid.set(DoubleSolenoid.Value.kReverse);
      intakeSolenoid.set(DoubleSolenoid.Value.kOff);
    }
  }
}
