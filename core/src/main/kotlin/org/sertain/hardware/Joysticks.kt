@file:Suppress("unused", "RedundantVisibilityModifier")
package org.sertain.hardware

import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.buttons.JoystickButton
import org.sertain.command.Command

public fun GenericHID.whenActive(buttonNumber: Int, command: Command) {
    JoystickButton(this, buttonNumber).whenActive(command.mirror)
}

public fun GenericHID.whileActive(buttonNumber: Int, command: Command) {
    JoystickButton(this, buttonNumber).whileActive(command.mirror)
}

public inline fun GenericHID.whenActive(
        buttonNumber: Int,
        crossinline block: () -> Boolean
) = whenActive(buttonNumber, object : Command() {
    override fun execute() = block()
})

public inline fun GenericHID.whileActive(
        buttonNumber: Int,
        crossinline block: () -> Boolean
) = whileActive(buttonNumber, object : Command() {
    override fun execute() = block()
})
