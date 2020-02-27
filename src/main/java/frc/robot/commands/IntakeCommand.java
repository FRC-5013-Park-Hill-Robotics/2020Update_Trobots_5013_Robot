/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants.DriverControllerConstants;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Intake;

public class IntakeCommand extends CommandBase {
  private Intake m_intake;
  private Conveyor m_conveyor;
  private XboxController m_controller;
  /**
   * Creates a new IntakeCommand.
   */
  public IntakeCommand(Intake intake, Conveyor conveyor, XboxController controller) {
    m_intake = intake;
    m_controller = controller;
    m_conveyor = conveyor;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double triggerValue = m_controller.getRawAxis(DriverControllerConstants.TRIGGER_AXIS);
    //.putString("Trigger Value",""+triggerValue); 
    //SmartDashboard.putString("Intake down", ""+ m_intake.isDown());
    if (m_intake.isDown() && triggerValue < .5){
     // SmartDashboard.putString("Intake Aaction ","raise");
       //Using command scheduler because shooter may be controlling conveyor and it gets presidence
       CommandScheduler.getInstance().schedule(new InstantCommand(() -> m_conveyor.stop(), m_conveyor));
      m_intake.raiseIntake();
    } else if (!m_intake.isDown() && triggerValue > .5){
      //Using command scheduler because shooter may be controlling conveyor and it gets presidence
      //SmartDashboard.putString("Intake Aaction ","drop");
      CommandScheduler.getInstance().schedule(new InstantCommand(() -> m_conveyor.start(), m_conveyor));
      m_intake.dropIntake();
    } else {
      //SmartDashboard.putString("Intake Aaction ","none");
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
