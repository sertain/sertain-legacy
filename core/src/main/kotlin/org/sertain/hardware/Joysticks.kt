@file:Suppress("unused", "RedundantVisibilityModifier")
package org.sertain.hardware

import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.buttons.JoystickButton
import org.sertain.command.Command

/**
 * @param button the number of the button to listen for
 * @param command the command to execute
 * @see JoystickButton.whenActive
 */
public fun GenericHID.whenActive(button: Int, command: Command) {
    JoystickButton(this, button).whenActive(command.mirror)
}

/**
 * @param button the number of the button to listen for
 * @param command the command to execute
 * @see JoystickButton.whileActive
 */
public fun GenericHID.whileActive(button: Int, command: Command) {
    JoystickButton(this, button).whileActive(command.mirror)
}

/** @see [whenActive] */
public inline fun GenericHID.whenActive(
        button: Int,
        crossinline block: () -> Boolean
) = whenActive(button, object : Command() {
    override fun execute() = block()
})

/** @see [whileActive] */
public inline fun GenericHID.whileActive(
        button: Int,
        crossinline block: () -> Boolean
) = whileActive(button, object : Command() {
    override fun execute() = block()
})
