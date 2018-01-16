@file:Suppress("unused", "RedundantVisibilityModifier")
package org.sertain

import edu.wpi.first.wpilibj.IterativeRobot
import edu.wpi.first.wpilibj.command.Scheduler

public abstract class Robot : IterativeRobot() {
    private var mode = Mode.DISABLED
        set(value) {
            if (value != field) {
                field = value
                when (field) {
                    Mode.TELEOP -> onTeleopStop()
                    Mode.AUTO -> onAutoStop()
                    Mode.DISABLED -> onDisabledStop()
                }
            }
        }

    override fun robotInit() = onCreate()

    override fun teleopInit() {
        onStart()
        onTeleopStart()
    }

    override fun autonomousInit() {
        onStart()
        onAutoStart()
    }

    override fun disabledInit() = onStop()

    override fun robotPeriodic() {
        Scheduler.getInstance().run()
        execute()
    }

    override fun teleopPeriodic() {
        mode = Mode.TELEOP
        executeTeleop()
    }

    override fun autonomousPeriodic() {
        mode = Mode.AUTO
        executeAuto()
    }

    override fun disabledPeriodic() {
        mode = Mode.DISABLED
    }

    public open fun onCreate() = Unit

    public open fun onStart() = Unit

    public open fun onTeleopStart() = Unit

    public open fun onAutoStart() = Unit

    public open fun execute() = Unit

    public open fun executeTeleop() = Unit

    public open fun executeAuto() = Unit

    public open fun onTeleopStop() = Unit

    public open fun onAutoStop() = Unit

    public open fun onDisabledStop() = Unit

    public open fun onStop() = Unit

    private enum class Mode {
        AUTO, TELEOP, DISABLED
    }
}
