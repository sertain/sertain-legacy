@file:Suppress("unused", "RedundantVisibilityModifier")
package org.sertain

import edu.wpi.first.wpilibj.IterativeRobot
import edu.wpi.first.wpilibj.command.Scheduler
import java.util.concurrent.CopyOnWriteArrayList

private typealias LifecycleDistributor = RobotLifecycle.Companion.Distributor

/**
 * An interface which allows for easy access to different lifecycle methods within the robot.
 */
public interface RobotLifecycle {
    /**
     * Lifecycle method which is executed immediately upon the creation of the robot.
     */
    public fun onCreate() = Unit

    /**
     * Lifecycle method which is executed immediately upon the robot becoming enabled.
     */
    public fun onStart() = Unit

    /**
     * Lifecycle method which is executed immediately upon the robot entering the teloperated mode.
     */
    public fun onTeleopStart() = Unit

    /**
     * Lifecycle method which is executed immediately upon the robot entering the autonomous mode.
     */
    public fun onAutoStart() = Unit

    /**
     * Lifecycle method which is executed periodically (every 20ms) on the robot.
     */
    public fun execute() = Unit

    /**
    * Lifecycle method which is executed periodically (every 20ms) while the robot is in the teleoperated mode.
    */
    public fun executeTeleop() = Unit

    /**
     * Lifecycle method which is executed periodically (every 20ms) while the robot is in the autonomous mode.
     */
    public fun executeAuto() = Unit

    /**
     * Lifecycle method which is executed immediately upon the end of the teleoperated mode.
     */
    public fun onTeleopStop() = Unit

    /**
     * Lifecycle method which is executed immediately upon the end of the autonomous mode.
     */
    public fun onAutoStop() = Unit

    /**
     * Lifecycle method which is executed immediately upon the end of disabled.
     */
    public fun onDisabledStop() = Unit

    /**
     * Lifecycle method which is executed immediately upon the robot becoming disabled.
     */
    public fun onStop() = Unit

    companion object {
        private val listeners: MutableList<RobotLifecycle> = CopyOnWriteArrayList()

        /**
         * Adds a new listener for [lifecycle].
         *
         * @param lifecycle the lifecycle to listen for
         */
        public fun addListener(lifecycle: RobotLifecycle) {
            listeners += lifecycle
        }

        /**
         * Removes a new listener for [lifecycle].
         *
         * @param lifecycle the lifecycle to remove a listener for
         */
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
