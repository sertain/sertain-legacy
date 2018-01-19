@file:Suppress("unused", "RedundantVisibilityModifier")
package org.sertain.hardware

import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj.Victor
import org.sertain.RobotLifecycle
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.TimeUnit
import kotlin.concurrent.schedule

public typealias Talon = WPI_TalonSRX

/**
 * Gets the encoder position of the currently selected sensor.
 */
public val Talon.encoderPosition: Int get() = getSelectedSensorPosition(0)

/**
 * Joins two [Talons][Talon] together by having the second follow the first.
 *
 * @param other the Talon which should follow this one
 * @return the original Talon
 */
public operator fun Talon.plus(other: Talon) = apply { other.follow(this) }

/**
 * Sets the Talon's current mode between either Brake or Coast.
 *
 * @param enable whether break mode should be enabled
 * @return the original Talon
 */
public fun Talon.setBreak(enable: Boolean = true) = apply {
    setNeutralMode(if (enable) NeutralMode.Brake else NeutralMode.Coast)
}

/**
 * Sets the Talon to use Auto Break mode. Auto Break mode will enable the Talon's break mode while
 * the robot is enabled in either the teleoperated or autonomous modes, but will disable break
 * mode after 5 seconds of being disabled. This mode is intended to be used on the drivetrain so
 * that the robot may be pushed around the field when it is disabled.
 *
 * @return the original Talon
 * @see BreakWhenStarted
 */
public fun Talon.autoBreak() = apply { BreakWhenStarted(this) }

/**
 * Resets a given sensor to 0.
 *
 * @param device the device to reset, default is FeedbackDevice.QuadEncoder
 * @return the original Talon
 */
public fun Talon.resetEncoder(device: FeedbackDevice = FeedbackDevice.QuadEncoder) = apply {
    configSelectedFeedbackSensor(device, 0, Int.MAX_VALUE)
    setSelectedSensorPosition(0, 0, Int.MAX_VALUE)
}

/**
 * Sets whether the Talon should be inverted.
 *
 * @param inverted whether the Talon should be inverted
 * @return the original Talon
 */
public fun Talon.inverted(inverted: Boolean = true) = apply { this.inverted = inverted }

/**
 * Stops the Talon until `set()` is called again.
 *
 * @return the original Talon
 */
public fun Talon.stop() = apply { stopMotor() }

/**
 * A lifecycle command which will put all specified talons in break mode when the robot is
 * enabled in either teleop or autonomous, and will disable break mode 5 seconds after the robot
 * is disabled in order to allow the robot to be pushed around on the field.
 *
 * @param talons the Talons to enable Auto Break on
 * @see autoBreak
 */
public class BreakWhenStarted(private vararg val talons: Talon) : RobotLifecycle {
    private var updateTask: TimerTask? = null

    init {
        RobotLifecycle.addListener(this)
    }

    override fun onStart() {
        updateTask?.cancel()
        set(true)
    }

    override fun onStop() {
        updateTask = Timer().schedule(TimeUnit.SECONDS.toMillis(5)) { set(false) }
    }

    private fun set(`break`: Boolean) {
        for (talon in talons) talon.setBreak(`break`)
    }
}
