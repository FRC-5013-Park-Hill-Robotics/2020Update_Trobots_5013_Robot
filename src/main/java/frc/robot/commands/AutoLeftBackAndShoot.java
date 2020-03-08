/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class AutoLeftBackAndShoot extends SequentialCommandGroup {
  /**
   * Creates a new AutoCenterBackAndShoot.
   */
  public AutoLeftBackAndShoot(Drivetrain drivetrain, Limelight limelight, Shooter shooter, Conveyor conveyor, Intake intake) {
    super(new ResetDrivetrainEncoders(drivetrain),
      new InstantCommand(() -> intake.dropIntake()),
      new InstantCommand(() -> shooter.spinUp()),
      new AutoDriveCommand(-0.5,6, drivetrain),
      new InstantCommand(() -> limelight.beforeTurnToTarget()),
      new FindTarget(limelight),
     // new TurnByAngle(limelight::getAngleOfError,drivetrain),
      new AutoTurnToTargetCommand(limelight, drivetrain, shooter),
      new FireAll(shooter, conveyor),
      new InstantCommand(() -> limelight.afterTurnToTarget()),
      new InstantCommand(() -> shooter.stopFiring())
      ) ;
  }
  
}
