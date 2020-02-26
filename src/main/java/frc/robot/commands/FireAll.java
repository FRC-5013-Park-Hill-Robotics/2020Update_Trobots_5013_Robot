/**
 * Command Used for Auto routine, it will continue to fire until there isn't a ball in the 
 * conveyor.  
 */

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Shooter;

public class FireAll extends CommandBase {
  private Shooter m_shooter;
  private Conveyor m_conveyor;
  private long lastBallTime;
  /**
   * Creates a new FireAll.
   */
  public FireAll(Shooter shooter, Conveyor conveyor) {
    m_shooter = shooter;
    m_conveyor = conveyor;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
    addRequirements(conveyor);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putBoolean("Execute firing",true);
    m_shooter.fire();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (m_conveyor.isBallReadyToShoot()){
      lastBallTime = System.currentTimeMillis();
    }
    SmartDashboard.putBoolean("Fire All Finished", System.currentTimeMillis() > lastBallTime);
    //Stop shooting when it has been a second since the last ball.
    return System.currentTimeMillis() > lastBallTime + 1000;
    
  }
}
