@file:Suppress("unused", "RedundantVisibilityModifier")
@file:JvmName("Motors")
package org.sertain.hardware

import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import org.sertain.RobotLifecycle
import org.sertain.hardware.BreakWhenStarted.minusAssign
import org.sertain.hardware.BreakWhenStarted.plusAssign
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.TimeUnit
import kotlin.concurrent.schedule

public typealias Talon = WPI_TalonSRX

/** Sets the currently selected sensor. */
public var Talon.selectedSensor: FeedbackDevice
    get() = FeedbackDevice.QuadEncoder
    set(value) {
        configSelectedFeedbackSensor(value, 0, 0)
    }

/** Gets the encoder position of the currently selected sensor. */
public val Talon.encoderPosition: Int get() = getSelectedSensorPosition(0)

/**
 * Gets the position of the specified [sensor].
 *
 * @param sensor the [FeedbackDevice] to get the position of
 * @return the position of the [sensor]
 */
public fun Talon.getEncoderPosition(sensor: FeedbackDevice): Int {
    configSelectedFeedbackSensor(sensor, 0, 0)
    return getSelectedSensorPosition(0)
}

/**
 * Gets the velocity of the specified [sensor].
 *
 * @param sensor the [FeedbackDevice] to get the velocity of
 * @return the velocity of the [sensor]
 */
public fun Talon.getEncoderVelocity(sensor: FeedbackDevice): Int {
    configSelectedFeedbackSensor(sensor, 0, 0)
    return getSelectedSensorVelocity(0)
}

/**
 * Joins two [Talons][Talon] together by having the second follow the first.
 *
 * @param other the Talon which should follow this one
 * @return the original Talon
 */
public operator fun Talon.plus(other: Talon) = apply { other.follow(this) }

/**
 * Sets the [Talon]'s current mode between either Brake or Coast.
 *
 * @param enable whether break mode should be enabled
 * @return the original Talon
 */
@JvmOverloads
public fun Talon.setBreak(enable: Boolean = true) = apply {
    setNeutralMode(if (enable) NeutralMode.Brake else NeutralMode.Coast)
}

/**
 * Sets the Talon to use Auto Break mode.
 *
 * @return the original Talon
 * @see BreakWhenStarted
 */
public fun Talon.autoBreak() = apply { BreakWhenStarted += this }

/**
 * Sets the Talon to break only when you explicitly use [setBreak].
 *
 * @return the original Talon
 * @see BreakWhenStarted
 */
public fun Talon.manualBreak() = apply { BreakWhenStarted -= this }

/**
 * Resets a given sensor to 0.
 *
 * @param device the device to reset, default is FeedbackDevice.QuadEncoder
 * @return the original Talon
 */
@JvmOverloads
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
@JvmOverloads
public fun Talon.invert(inverted: Boolean = true) = apply { this.inverted = inverted }

/**
 * Stops the Talon until [WPI_TalonSRX.set] is called again.
 *
 * @return the original Talon
 */
public fun Talon.stop() = apply { stopMotor() }

/**
 * Puts all specified talons in break mode when the robot is enabled in either teleop or autonomous
 * mode, and will disable break mode 5 seconds after the robot is disabled in order to allow the
 * robot to be pushed around on the field.
 *
 * @see autoBreak
 */
private object BreakWhenStarted : RobotLifecycle {
    private val talons = mutableSetOf<Talon>()
    private var updateTask: TimerTask? = null

    init {
        RobotLifecycle.addListener(this)
    }

    operator fun BreakWhenStarted.plusAssign(talon: Talon) {
        synchronized(talons) { talons += talon }
    }

    operator fun BreakWhenStarted.minusAssign(talon: Talon) {
        synchronized(talons) { talons -= talon }
    }

    override fun onStart() {
        updateTask?.cancel()
        set(true)
    }

    override fun onStop() {
        updateTask = Timer().schedule(TimeUnit.SECONDS.toMillis(5)) { set(false) }
    }

    private fun set(`break`: Boolean) {
        synchronized(talons) { for (talon in talons) talon.setBreak(`break`) }
    }
}
