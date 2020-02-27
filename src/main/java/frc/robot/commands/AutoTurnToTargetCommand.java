/**
 * Turns to target until target found.  To work properly this command requires
 * the target be in vistion and the beforeTurnToTarget on the limelight have 
 * been called.  
 */

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class AutoTurnToTargetCommand extends CommandBase {
  private Drivetrain m_drivetrain;
  private Limelight m_limeLight;
  private Shooter m_shooter;
  private boolean m_finished;

  /**
   * Creates a new AutoTurnToTargetCommand.
   */
  public AutoTurnToTargetCommand(Limelight limeLight, Drivetrain drivetrain, Shooter shooter) {
    super();
    this.m_limeLight = limeLight;
    this.m_drivetrain = drivetrain;
    this.m_shooter = shooter;
    addRequirements(drivetrain);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_finished = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_finished = m_limeLight.turnToTargetVolts(m_drivetrain, m_shooter);
    //SmartDashboard.putString("Execute auto turn to target", "Finishged =" + m_finished);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // make sure it stops the drive
    m_drivetrain.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_finished;
  }
}
