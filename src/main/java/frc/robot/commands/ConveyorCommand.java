/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Conveyor;

public class ConveyorCommand extends CommandBase {
  private Conveyor m_conveyor;
  /**
   * Creates a new ConveyorCommand.
   */
  public ConveyorCommand(Conveyor conveyor) {
    this.m_conveyor = conveyor;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(conveyor);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putString("ConveyorCommand lower eye", ""+m_conveyor.isBallReadyForIntake());
    SmartDashboard.putString("ConveyorCommand upper eye",""+ m_conveyor.isBallReadyToShoot());
    if (m_conveyor.isBallReadyForIntake() && !m_conveyor.isBallReadyToShoot()){
      m_conveyor.start();
    } else {
      m_conveyor.stop();
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
