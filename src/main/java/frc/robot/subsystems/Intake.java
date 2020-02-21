/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StickyFaults;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase {
  private TalonSRX intakeMotor = new TalonSRX(IntakeConstants.INTAKE_MOTOR);
  
  //Dropping the intake is set up as forward and raising it as reverse, may have to change based on mechanics and wiring.
  private Solenoid dropIntakeSolenoid = new Solenoid(Constants.PCM_ID,IntakeConstants.DROP_INTAKE_SOLENOID_CHANNEL);
  private Solenoid raiseIntakeSolenoid = new Solenoid(Constants.PCM_ID,IntakeConstants.RAISE_INTAKE_SOLENOID_CHANNEL);
  private Conveyor m_conveyor;
  /**
   * Creates a new Intake.
   */
  public Intake(Conveyor conveyor) {
    super();
    intakeMotor.configFactoryDefault();
    intakeMotor.setInverted(true);
    this.m_conveyor = conveyor;
    intakeMotor.setNeutralMode(NeutralMode.Brake);
  }

  @Override
  public void periodic() {
    Faults faults = new Faults();
    StickyFaults stickyFaults = new StickyFaults();
    intakeMotor.getFaults(faults);
    intakeMotor.getStickyFaults(stickyFaults);
    if (faults.hasAnyFault()){
      SmartDashboard.putString("Intake Faults",faults.toString());
    } else {
      SmartDashboard.putString("Intake Faults", "none");
    }
    if (stickyFaults.hasAnyFault()){
      SmartDashboard.putString("Intake Sticky Faults",stickyFaults.toString());
    } else {
      SmartDashboard.putString("IntakeSticky Faults", "none");
    }
  }

  public void dropIntake(){
    //Using command scheduler because shooter may be controlling conveyor and it gets presidence
    //CommandScheduler.getInstance().schedule(new InstantCommand(() -> m_conveyor.start(), m_conveyor));
    dropIntakeSolenoid.set(true); 
    raiseIntakeSolenoid.set(false); 
    intakeMotor.set(ControlMode.PercentOutput, .9 );
  }

  public void raiseIntake(){
    //Using command scheduler because shooter may be controlling conveyor and it gets presidence
   // CommandScheduler.getInstance().schedule(new InstantCommand(() -> m_conveyor.stop(), m_conveyor));
    intakeMotor.set(ControlMode.PercentOutput, 0 );
    dropIntakeSolenoid.set(false); 
    raiseIntakeSolenoid.set(true); 
    
  }

  public boolean isDown(){
    return dropIntakeSolenoid.get();
  }
  public void reverseIntake(){
    intakeMotor.set(ControlMode.PercentOutput, -.75 );
  }
  
}
