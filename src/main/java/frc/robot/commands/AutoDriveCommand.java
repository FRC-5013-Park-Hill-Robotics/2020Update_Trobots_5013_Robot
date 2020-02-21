/**
 * Will drive straight for a given distance.
 */

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class AutoDriveCommand extends CommandBase {
    private double m_distance;
    private double m_speed;
    private Drivetrain m_drivetrain;
    private double m_start = 0;
  /**
   * Creates a new DriveBackwardsCommand.
   * @param speed is the percent output negative values being backwards
   * @param distance is the distance to travel in meters  //todo consider doing this in feet.
   * @param drivetrain is the drivetrain used to drive
   *
   */
  public AutoDriveCommand(double speed, double distance, Drivetrain drivetrain) {
    super();
    m_distance = distance;
    m_speed = speed;
    m_drivetrain = drivetrain;
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
     m_start = 0;
     m_drivetrain.resetEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putString("execute autodrive", "Speed" + m_speed);
    m_drivetrain.arcadeDrive(m_speed, 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    SmartDashboard.putNumber("Distance", m_drivetrain.getAverageEncoderDistance());
    return Math.abs(m_drivetrain.getAverageEncoderDistance()) >= m_distance;
  }
}
