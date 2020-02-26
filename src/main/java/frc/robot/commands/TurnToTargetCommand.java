/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class TurnToTargetCommand extends CommandBase {
  private Drivetrain m_drivetrain;
  private Limelight m_limeLight;
  private Shooter m_shooter;
  /**
   * Creates a new TurnToTarget.
   */
  public TurnToTargetCommand(Limelight limeLight, Drivetrain drivetrain, Shooter shooter) {
    this.m_limeLight = limeLight;
    this.m_drivetrain = drivetrain;
    this.m_shooter = shooter;
    addRequirements(limeLight);
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_limeLight.turnToTargetVolts(m_drivetrain,m_shooter);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //make sure it stops the drive
    m_drivetrain.arcadeDrive(0,0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // don't return true ever, command will end after button is released
    return false;
  }
}
