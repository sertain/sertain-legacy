@file:Suppress("unused", "RedundantVisibilityModifier")
package org.sertain.hardware

import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import org.sertain.RobotLifecycle
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.TimeUnit
import kotlin.concurrent.schedule

public typealias Talon = WPI_TalonSRX

public val Talon.encoderPosition: Int get() = getSelectedSensorPosition(0)

public operator fun Talon.plus(other: Talon) = apply { other.follow(this) }

public fun Talon.setBreak(enable: Boolean = true) = apply {
    setNeutralMode(if (enable) NeutralMode.Brake else NeutralMode.Coast)
}

public fun Talon.autoBreak() = apply { BreakWhenStarted(this) }

public fun Talon.resetEncoder(device: FeedbackDevice = FeedbackDevice.QuadEncoder) = apply {
    configSelectedFeedbackSensor(device, 0, Int.MAX_VALUE)
    setSelectedSensorPosition(0, 0, Int.MAX_VALUE)
}

public fun Talon.inverted(inverted: Boolean = true) = apply { this.inverted = inverted }

public fun Talon.stop() = apply { stopMotor() }

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
