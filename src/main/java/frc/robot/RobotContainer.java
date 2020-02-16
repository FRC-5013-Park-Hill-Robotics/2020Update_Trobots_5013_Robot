/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.DriverControllerConstants;
import frc.robot.DirectionPadButton.Direction;
import frc.robot.commands.AutonomousCommand;
import frc.robot.commands.ConveyorCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.TurnToTargetCommand;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private XboxController driverController = new XboxController(DriverControllerConstants.XBOX_ID);
  // private XboxController operatorController;
  private final Drivetrain m_driveTrain = new Drivetrain();
  private final Conveyor conveyor = new Conveyor();
  private final Intake intake = new Intake();
  private final Shooter shooter = new Shooter(conveyor);
  private final Limelight m_Limelight = new Limelight();
  private final Climber climber = new Climber();

  private final AutonomousCommand m_autoCommand = new AutonomousCommand(m_driveTrain);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    // operatorController = new XboxController(OperatorControllerConstants.XBOX_ID);
    // Configure the button bindings
    configureButtonBindings();
    m_driveTrain.setDefaultCommand(new DriveCommand(m_driveTrain, driverController));
    intake.setDefaultCommand(new IntakeCommand(intake, conveyor, driverController));
    conveyor.setDefaultCommand(new ConveyorCommand(conveyor));
    /*
     * m_driveTrain.setDefaultCommand( // A split-stick arcade command, with
     * forward/backward controlled by the left // hand, and turning controlled by
     * the right. new RunCommand(() -> m_driveTrain.arcadeDrive(
     * -driverController.getRawAxis(DriverControllerConstants.Y_LJOY_ID),
     * driverController.getRawAxis(DriverControllerConstants.X_RJOY_ID)),
     * m_driveTrain));
     */

    // For shooter testing comment out when trying to drive
    /*
     * shooter.setDefaultCommand(new RunCommand(() -> shooter.test(
     * -driverController.getRawAxis(1.0),driverController.(getRawAxis(.05)),
     * shooter));
     */
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    // Turn to target
    new TriggerButton(driverController,TriggerButton.Trigger.LEFT, .05)
      .whenPressed(new InstantCommand(() -> m_Limelight.beforeTurnToTarget()))
      .whileHeld(new TurnToTargetCommand(m_Limelight, m_driveTrain, shooter))
      .whenReleased(new InstantCommand(() -> m_Limelight.afterTurnToTarget()));

    // Fire
    new TriggerButton(driverController, TriggerButton.Trigger.RIGHT,.05)
      .whenPressed(new InstantCommand(() -> intake.dropIntake(),intake))
      .whenReleased(new InstantCommand(() -> intake.raiseIntake(),intake));

    //Intake up down
    new JoystickButton(driverController, XboxController.Button.kBumperRight.value)
      .whenReleased(new InstantCommand(()-> shooter.changeSpeed(500), shooter));

    //Slow Turn
    new JoystickButton(driverController, XboxController.Button.kBumperLeft.value)
      .whileHeld(new InstantCommand(()->m_driveTrain.arcadeDrive(
         -driverController.getRawAxis(DriverControllerConstants.Y_LJOY_ID),
         driverController.getRawAxis(DriverControllerConstants.X_RJOY_ID)),
         m_driveTrain));
    
    // Extend Climber
    new DirectionPadButton(driverController, Direction.UP)
      .whileHeld(new InstantCommand(() -> climber.extend(1)))
      .whenReleased(new InstantCommand(() -> climber.hold()));
    // Retract Climber
    new DirectionPadButton(driverController, Direction.DOWN)
      .whileHeld(new InstantCommand(() -> climber.retract(.50)))
      .whenReleased(new InstantCommand(() -> climber.hold()));
    // Roll Climb left
    new DirectionPadButton(driverController, Direction.LEFT)
      .whileHeld(new InstantCommand(() -> climber.roll(.30)))
      .whenReleased(new InstantCommand(() -> climber.hold()));
    // Roll Climb Right
    new DirectionPadButton(driverController, Direction.RIGHT)
      .whileHeld(new InstantCommand(() -> climber.roll(-.30)))
      .whenReleased(new InstantCommand(() -> climber.hold()));

    // temporary
    new JoystickButton(driverController, XboxController.Button.kBumperLeft.value)
    .whenPressed(new InstantCommand(() -> conveyor.start()))
    .whenReleased(new InstantCommand(() -> conveyor.stop()));

  /*
   * new JoystickButton(driverController, XboxController.Button.kBumperLeft.value)
   * .whileHeld(new ConveyorCommand(conveyor));
   */
  /*
   * new JoystickButton(driverController, XboxController.Button.kY.value)
   * .whenReleased(new InstantCommand(()-> shooter.changeSpeed(500), shooter));
   * new JoystickButton(driverController, XboxController.Button.kX.value)
   * .whenReleased(new InstantCommand(() -> shooter.changeSpeed(500), shooter));
   */
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public CommandBase getAutonomousCommand() {
    RamseteCommand command = AutoPathFactory.generateTrajectory(this.m_driveTrain);
    return command.andThen(() -> m_driveTrain.tankDriveVolts(0, 0));
  }

}
