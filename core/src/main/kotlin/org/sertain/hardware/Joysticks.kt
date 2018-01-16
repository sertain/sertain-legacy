@file:Suppress("unused", "RedundantVisibilityModifier")
package org.sertain.hardware

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.buttons.JoystickButton
import edu.wpi.first.wpilibj.command.Command

public fun Joystick.whenActive(buttonNumber: Int, command: Command) {
    JoystickButton(this, buttonNumber).whenActive(command)
}

public fun Joystick.whileActive(buttonNumber: Int, command: Command) {
    JoystickButton(this, buttonNumber).whileActive(command)
}
