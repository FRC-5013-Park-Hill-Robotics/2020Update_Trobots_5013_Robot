/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

public class ArcadeDrive extends CommandBase {
  
  private final Drivetrain m_driveTrain;
  private double throttle;
  private double rotation;

  
  /**
   * Creates a new ArcadeDrive.
   */
  public ArcadeDrive(Drivetrain driveTrain, double throttle, double rotation) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.m_driveTrain = driveTrain;
    this.throttle = throttle;
    this.rotation = rotation;

    this.addRequirements(driveTrain);
  }

// Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putString("ArcadeExecute", "ArcadeDrive.execute");
    SmartDashboard.putString("Throttle", "Throttle = " + this.throttle);
    this.throttle = applyDeadBand(this.throttle);
    this.rotation = applyDeadBand(this.rotation);
    m_driveTrain.getDrive().arcadeDrive(this.throttle, this.rotation);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_driveTrain.getDrive().arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
  public double applyDeadBand(double speed){
    double mod=0;
    if(speed > Constants.DEADBAND_VALUE){
      mod = speed;
    }

    return mod;
  }
}
