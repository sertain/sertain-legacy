package org.sertain

import edu.wpi.first.wpilibj.IterativeRobot
import edu.wpi.first.wpilibj.command.Scheduler

open class Robot : IterativeRobot() {
    private enum class Mode {
        AUTO, TELEOP, DISABLED
    }

    private var previousMode: Mode = Mode.DISABLED

    private fun checkModeComplete(currentMode: Mode) {
        if (currentMode != previousMode) {
            when (previousMode) {
                Mode.TELEOP -> onTeleopEnd()
                Mode.AUTO -> onAutoEnd()
                Mode.DISABLED -> onDisabledEnd()
            }
        }
    }

    override fun robotInit() = onStart()

    override fun teleopInit() = onTeleopStart()

    override fun autonomousInit() = onAutoStart()

    override fun disabledInit() = onDisabledStart()

    override fun robotPeriodic() = execute()

    override fun teleopPeriodic() {
        Scheduler.getInstance().run()

        checkModeComplete(Mode.TELEOP)
        executeTeleop()

        previousMode = Mode.TELEOP
    }

    override fun autonomousPeriodic() {
        Scheduler.getInstance().run()

        checkModeComplete(Mode.AUTO)
        executeAuto()

        previousMode = Mode.AUTO
    }

    override fun disabledPeriodic() {
        checkModeComplete(Mode.DISABLED)
        executeDisabled()

        previousMode = Mode.DISABLED
    }

    open fun onStart() {}

    open fun onTeleopStart() {}

    open fun onAutoStart() {}

    open fun onDisabledStart() {}

    open fun execute() {}

    open fun executeTeleop() {}

    open fun executeAuto() {}

    open fun executeDisabled() {}

    open fun onTeleopEnd() {}

    open fun onAutoEnd() {}

    open fun onDisabledEnd() {}
}
