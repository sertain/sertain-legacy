@file:Suppress("unused", "RedundantVisibilityModifier")
@file:JvmName("Joysticks")
package org.sertain.hardware

import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.buttons.JoystickButton
import org.sertain.command.Command
import org.sertain.command.CommandBase

/** @return [Joystick.getThrottle], scaled to positive values (0..1) */
// Minus because `throttle` is 1 when at the bottom...sigh WPI
public val Joystick.scaledThrottle get() = (throttle - 1) / 2

/** @return currently pressed POV button or null if unknown */
public val GenericHID.povButton get() = PovButton.values().find { isPovButtonPressed(it) }

/**
 * @param button the POV button
 * @return true if the POV button is pressed, false otherwise
 */
public fun GenericHID.isPovButtonPressed(button: PovButton) = pov == button.angle

/**
 * @param button the number of the button to listen for
 * @param command the command to execute
 * @see JoystickButton.whenActive
 */
public fun GenericHID.whenActive(button: Int, command: CommandBase) {
    JoystickButton(this, button).whenActive(command.mirror)
}

/**
 * @param button the number of the button to listen for
 * @param command the command to execute
 * @see JoystickButton.whileActive
 */
public fun GenericHID.whileActive(button: Int, command: CommandBase) {
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

/** Represents the most common POV buttons. */
public enum class PovButton(val angle: Int) {
    CENTER(-1),
    TOP(0),
    TOP_RIGHT(45),
    RIGHT(90),
    BOTTOM_RIGHT(135),
    BOTTOM(180),
    BOTTOM_LEFT(225),
    LEFT(270),
    TOP_LEFT(315)
}
