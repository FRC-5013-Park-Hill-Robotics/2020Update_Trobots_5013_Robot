/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase {
  private TalonSRX intakeMotor = new TalonSRX(IntakeConstants.INTAKE_MOTOR);
  
  //Dropping the intake is set up as forward and raising it as reverse, may have to change based on mechanics and wiring.
  private Solenoid dropIntateSolenoid = new Solenoid(Constants.PCM_ID,IntakeConstants.DROP_INTAKE_SOLENOID_CHANNEL);;
  /**
   * Creates a new Intake.
   */
  public Intake() {
    intakeMotor.configFactoryDefault();
    intakeMotor.setInverted(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void dropIntake(){
    intakeMotor.set(ControlMode.PercentOutput, .75 );
    dropIntateSolenoid.set(true); 

  }

  public void raiseIntake(){
    dropIntateSolenoid.set(false); 
    intakeMotor.set(ControlMode.PercentOutput, 0 );
  }

  public boolean isDown(){
    return dropIntateSolenoid.get();
  }
}
