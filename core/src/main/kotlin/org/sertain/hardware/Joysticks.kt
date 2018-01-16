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
public fun Joystick.whenActive(buttonNumber: Int, command: Command) {
    JoystickButton(this, buttonNumber).whenActive(command.mirror)
}

/**
 * Constantly starts the given command while the joystick's [button] is held.
 *
 * @param button the number of the button to listen for
 * @param command the command to execute
 */
public fun Joystick.whileActive(buttonNumber: Int, command: Command) {
    JoystickButton(this, buttonNumber).whileActive(command.mirror)
}
