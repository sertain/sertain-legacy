@file:Suppress("unused", "RedundantVisibilityModifier")
package org.sertain

import edu.wpi.first.wpilibj.IterativeRobot
import edu.wpi.first.wpilibj.command.Scheduler
import java.util.concurrent.CopyOnWriteArrayList

private typealias LifecycleDistributor = RobotLifecycle.Companion.Distributor

public interface RobotLifecycle {
    public fun onCreate() = Unit

    public fun onStart() = Unit

    public fun onTeleopStart() = Unit

    public fun onAutoStart() = Unit

    public fun execute() = Unit

    public fun executeTeleop() = Unit

    public fun executeAuto() = Unit

    public fun onTeleopStop() = Unit

    public fun onAutoStop() = Unit

    public fun onDisabledStop() = Unit

    public fun onStop() = Unit

    companion object {
        private val listeners: MutableList<RobotLifecycle> = CopyOnWriteArrayList()

        public fun addListener(lifecycle: RobotLifecycle) {
            listeners += lifecycle
        }

        public fun removeListener(lifecycle: RobotLifecycle) {
            listeners -= lifecycle
        }

        internal object Distributor : RobotLifecycle {
            override fun onCreate() {
                for (listener in listeners) listener.onCreate()
            }

            override fun onStart() {
                for (listener in listeners) listener.onStart()
            }

            override fun onTeleopStart() {
                for (listener in listeners) listener.onTeleopStart()
            }

            override fun onAutoStart() {
                for (listener in listeners) listener.onAutoStart()
            }

            override fun execute() {
                for (listener in listeners) listener.execute()
            }

            override fun executeTeleop() {
                for (listener in listeners) listener.executeTeleop()
            }

            override fun executeAuto() {
                for (listener in listeners) listener.executeAuto()
            }

            override fun onTeleopStop() {
                for (listener in listeners) listener.onTeleopStop()
            }

            override fun onAutoStop() {
                for (listener in listeners) listener.onAutoStop()
            }

            override fun onDisabledStop() {
                for (listener in listeners) listener.onDisabledStop()
            }

            override fun onStop() {
                for (listener in listeners) listener.onStop()
            }
        }
    }
}

public abstract class Robot : IterativeRobot(), RobotLifecycle {
    private var mode = Mode.DISABLED
        set(value) {
            if (value != field) {
                field = value
                when (field) {
                    Mode.TELEOP -> LifecycleDistributor.onTeleopStop()
                    Mode.AUTO -> LifecycleDistributor.onAutoStop()
                    Mode.DISABLED -> LifecycleDistributor.onDisabledStop()
                }
            }
        }

    init {
        @Suppress("LeakingThis") // Invoked through reflection and initialized later
        RobotLifecycle.addListener(this)
    }

    override fun robotInit() = LifecycleDistributor.onCreate()

    override fun teleopInit() {
        LifecycleDistributor.onStart()
        LifecycleDistributor.onTeleopStart()
    }

    override fun autonomousInit() {
        LifecycleDistributor.onStart()
        LifecycleDistributor.onAutoStart()
    }

    override fun disabledInit() = LifecycleDistributor.onStop()

    override fun robotPeriodic() {
        Scheduler.getInstance().run()
        LifecycleDistributor.execute()
    }

    override fun teleopPeriodic() {
        mode = Mode.TELEOP
        LifecycleDistributor.executeTeleop()
    }

    override fun autonomousPeriodic() {
        mode = Mode.AUTO
        LifecycleDistributor.executeAuto()
    }

    override fun disabledPeriodic() {
        mode = Mode.DISABLED
    }

    private enum class Mode {
        AUTO, TELEOP, DISABLED
    }
}
