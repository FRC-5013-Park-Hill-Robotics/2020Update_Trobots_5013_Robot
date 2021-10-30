/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.Button;

/**
 * Add your docs here.
 */
public class TriggerButton extends Button {
    private GenericHID m_controller;
    private Trigger m_trigger;
    private double m_deadband;
    public static enum Trigger {
        LEFT(2), RIGHT(3);

        int axis;

        private Trigger(int axis) {
            this.axis = axis;
        }
    }
    public TriggerButton(GenericHID joystick, Trigger trigger, double deadband) {
        this.m_controller = joystick;
        this.m_trigger = trigger;
        this.m_deadband = deadband;
    }

    public boolean get() {
        double axis = m_controller.getRawAxis(m_trigger.axis);
        //SmartDashboard.putNumber("Trigger Value", axis);
        return axis > m_deadband;
    }
}
