@file:Suppress("unused", "RedundantVisibilityModifier")
package org.sertain.hardware

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.buttons.JoystickButton
import edu.wpi.first.wpilibj.command.Command

/**
 * Executes the given command once when a joystick's [button] is pressed.
 *
 * @param button the number of the button to listen for
 * @param command the command to execute
 */
public fun Joystick.whenActive(button: Int, command: Command) {
    JoystickButton(this, button).whenActive(command)
}

/**
 * Constantly starts the given command while the joystick's [button] is held.
 *
 * @param button the number of the button to listen for
 * @param command the command to execute
 */
public fun Joystick.whileActive(button: Int, command: Command) {
    JoystickButton(this, button).whileActive(command)
}
