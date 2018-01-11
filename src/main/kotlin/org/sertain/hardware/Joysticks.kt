package org.sertain.hardware

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.buttons.JoystickButton
import edu.wpi.first.wpilibj.command.Command

fun Joystick.whenActive(buttonNumber: Int, command: Command) {
    JoystickButton(this, buttonNumber).whenActive(command)
}

fun Joystick.whileActive(buttonNumber: Int, command: Command) {
    JoystickButton(this, buttonNumber).whileActive(command)
}
