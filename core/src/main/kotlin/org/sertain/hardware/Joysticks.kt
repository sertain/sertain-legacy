@file:Suppress("unused", "RedundantVisibilityModifier")
package org.sertain.hardware

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.buttons.JoystickButton
import org.sertain.command.Command

public fun Joystick.whenActive(buttonNumber: Int, command: Command) {
    JoystickButton(this, buttonNumber).whenActive(command.mirror)
}

public fun Joystick.whileActive(buttonNumber: Int, command: Command) {
    JoystickButton(this, buttonNumber).whileActive(command.mirror)
}

public fun Joystick.whenActive(buttonNumber: Int, block: () -> Unit) {
    JoystickButton(this, buttonNumber).whenActive(
        object : edu.wpi.first.wpilibj.command.Command() {
            override fun execute() {
                block()
            }

            override fun isFinished(): Boolean {
                return true
            }
        }
    )
}

public fun Joystick.whileActive(buttonNumber: Int, block: () -> Unit) {
    JoystickButton(this, buttonNumber).whileActive(
        object : edu.wpi.first.wpilibj.command.Command() {
            override fun execute() {
                block()
            }

            override fun isFinished(): Boolean {
                return true
            }
        }
    )
}
