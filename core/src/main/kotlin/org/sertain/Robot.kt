@file:Suppress("unused", "RedundantVisibilityModifier")
package org.sertain

import android.support.annotation.VisibleForTesting
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

    public fun executeDisabled() = Unit

    public fun executeTeleop() = Unit

    public fun executeAuto() = Unit

    public fun onTeleopStop() = Unit

    public fun onAutoStop() = Unit

    public fun onDisabledStop() = Unit

    public fun onStop() = Unit

    companion object {
        @VisibleForTesting
        internal val listeners: MutableList<RobotLifecycle> = CopyOnWriteArrayList()

        public fun addListener(lifecycle: RobotLifecycle) {
            listeners += lifecycle
        }

        public fun removeListener(lifecycle: RobotLifecycle) {
            listeners -= lifecycle
        }

        internal object Distributor : RobotLifecycle {
            override fun onCreate() = notify { onCreate() }

            override fun onStart() = notify { onStart() }

            override fun onTeleopStart() = notify { onTeleopStart() }

            override fun onAutoStart() = notify { onAutoStart() }

            override fun execute() = notify { execute() }

            override fun executeDisabled() = notify { executeDisabled() }

            override fun executeTeleop() = notify { executeTeleop() }

            override fun executeAuto() = notify { executeAuto() }

            override fun onTeleopStop() = notify { onTeleopStop() }

            override fun onAutoStop() = notify { onAutoStop() }

            override fun onDisabledStop() = notify { onDisabledStop() }

            override fun onStop() = notify { onStop() }

            private inline fun notify(block: RobotLifecycle.() -> Unit) {
                for (listener in listeners) listener.block()
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

    override fun robotPeriodic() {
        Scheduler.getInstance().run()
        LifecycleDistributor.execute()
    }

    override fun disabledInit() = LifecycleDistributor.onStop()

    override fun disabledPeriodic() {
        mode = Mode.DISABLED
        LifecycleDistributor.executeDisabled()
    }

    override fun autonomousInit() {
        LifecycleDistributor.onStart()
        LifecycleDistributor.onAutoStart()
    }

    override fun autonomousPeriodic() {
        mode = Mode.AUTO
        LifecycleDistributor.executeAuto()
    }

    override fun teleopInit() {
        LifecycleDistributor.onStart()
        LifecycleDistributor.onTeleopStart()
    }

    override fun teleopPeriodic() {
        mode = Mode.TELEOP
        LifecycleDistributor.executeTeleop()
    }

    private enum class Mode {
        AUTO, TELEOP, DISABLED
    }
}
