@file:Suppress("unused", "RedundantVisibilityModifier")
package org.sertain.hardware

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.buttons.JoystickButton
import org.sertain.command.Command

/**
 * Executes the given command once when a joystick's [button] is pressed.
 *
 * @param button the number of the button to listen for
 * @param command the command to execute
 */
public fun Joystick.whenActive(button: Int, command: Command) {
    JoystickButton(this, button).whenActive(command.mirror)
}

/**
 * Constantly starts the given command while the joystick's [button] is held.
 *
 * @param button the number of the button to listen for
 * @param command the command to execute
 */
public fun Joystick.whileActive(button: Int, command: Command) {
    JoystickButton(this, button).whileActive(command.mirror)
}

/**
 * Executes the given lambda function once when a joystick's [button] is pressed.
 *
 * @param button the number of the button to listen for
 * @param block the lambda function to execute
 */
public inline fun Joystick.whenActive(
        button: Int,
        crossinline block: () -> Boolean
) = whenActive(button, object : Command() {
    override fun execute() = block()
})

/**
 * Constantly starts the given lambda function while the joystick's [button] is held.
 *
 * @param button the number of the button to listen for
 * @param block the lambda function to execute
 */
public inline fun Joystick.whileActive(
        button: Int,
        crossinline block: () -> Boolean
) = whileActive(button, object : Command() {
    override fun execute() = block()
})
