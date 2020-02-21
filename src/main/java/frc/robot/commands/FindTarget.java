 /** Limelight operates asynchronously so hasTarget will return false while finding the target.  
   During auto we need a command to make the sequence of commands wait until target is found before 
   proceeding to turn to target.  Thus the only thing this command does is implement isFinished.   If
   for some reason auto can't find target this command will hang the auto routine. */

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Limelight;

public class FindTarget extends CommandBase {
  private Limelight m_limelight;
  /**
   * Creates a new FindTarget.
   */
  public FindTarget(Limelight limelight) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_limelight = limelight;
  };

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }


  @Override
  public boolean isFinished() {
    SmartDashboard.putBoolean("Finder has target", m_limelight.hasTarget());
    return m_limelight.hasTarget();
  }
}
