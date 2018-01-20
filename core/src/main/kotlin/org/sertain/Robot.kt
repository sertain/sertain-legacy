@file:Suppress("unused", "RedundantVisibilityModifier")
package org.sertain

import android.support.annotation.VisibleForTesting
import edu.wpi.first.wpilibj.IterativeRobot
import edu.wpi.first.wpilibj.command.Scheduler

private typealias LifecycleDistributor = RobotLifecycle.Companion.Distributor

/**
 * Represents the standard FRC robot lifecycle.
 *
 * Note: "Lifecycle" in this context means the different states the robot can be in such as enabled
 * or disabled.
 */
public interface RobotLifecycle {
    /**
     * Indicates robot creation. This method will be called exactly once right after basic robot
     * initialization has occurred. This is a good time to perform any setup necessary for the
     * entire robot's lifetime.
     */
    public fun onCreate() = Unit

    /**
     * Indicates that the robot is being enabled. This method will be called before the robot
     * becomes enabled in either the teleoperated or autonomous mode. This would be a good time to
     * perform actions to prepare the robot for movement.
     */
    public fun onStart() = Unit

    /**
     * Indicates that the robot is being enabled in the teleoperated mode. This method will be
     * called before the robot becomes enabled in teleoperated mode.
     */
    public fun onTeleopStart() = Unit

    /**
     * Indicates that the robot is being enabled in the autonomous mode. This method will be called
     * before the robot becomes enabled in autonomous mode.
     */
    public fun onAutoStart() = Unit

    /**
     * Runs periodically (default = every 20ms) while the robot is turned on. It need not be enabled
     * for this method to be called.
     */
    public fun execute() = Unit

    /**
     * Runs periodically (default = every 20ms) while the robot is in the disabled state.
     */
    public fun executeDisabled() = Unit

    /**
     * Runs periodically (default = every 20ms) while the robot is in the teleoperated mode.
     */
    public fun executeTeleop() = Unit

    /**
     * Runs periodically (default = every 20ms) while the robot is in the autonomous mode.
     */
    public fun executeAuto() = Unit

    /**
     * Indicates that the teleoperated mode has just terminated. This method will be called after
     * the teleoperated mode has terminated.
     */
    public fun onTeleopStop() = Unit

    /**
     * Indicates that the autonomous mode has just terminated. This method will be called after the
     * autonomous mode has terminated.
     */
    public fun onAutoStop() = Unit

    /**
     * Indicates that the disabled state has just terminated. This method will be called after the
     * disabled state has terminated. This method should be equivalent to [onStart].
     */
    public fun onDisabledStop() = Unit

    /**
     * Indicates that the robot has become disabled. This method will be called upon entering the
     * disabled state.
     */
    public fun onStop() = Unit

    companion object {
        @VisibleForTesting
        internal val listeners = mutableSetOf<RobotLifecycle>()

        /**
         * Adds a listener for [RobotLifecycle] events.
         *
         * @param lifecycle the lifecycle object to receive callbacks
         */
        public fun addListener(lifecycle: RobotLifecycle) {
            synchronized(listeners) { listeners += lifecycle }
        }

        /**
         * Removes a listener for [RobotLifecycle] events.
         *
         * @param lifecycle the object to stop receiving callbacks
         */
        public fun removeListener(lifecycle: RobotLifecycle) {
            synchronized(listeners) { listeners -= lifecycle }
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
                synchronized(listeners) { for (listener in listeners) listener.block() }
            }
        }
    }
}

/**
 * Base robot class which must be used for [RobotLifecycle] callbacks to work.
 */
public abstract class Robot : IterativeRobot(), RobotLifecycle {
    private var mode = Mode.DISABLED
        set(value) {
            if (value != field) {
                when (field) {
                    Mode.TELEOP -> LifecycleDistributor.onTeleopStop()
                    Mode.AUTO -> LifecycleDistributor.onAutoStop()
                    Mode.DISABLED -> LifecycleDistributor.onDisabledStop()
                }
                field = value
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

    override fun disabledInit() {
        mode = Mode.DISABLED
        LifecycleDistributor.onStop()
    }

    override fun disabledPeriodic() = LifecycleDistributor.executeDisabled()

    override fun autonomousInit() {
        mode = Mode.AUTO
        LifecycleDistributor.onStart()
        LifecycleDistributor.onAutoStart()
    }

    override fun autonomousPeriodic() = LifecycleDistributor.executeAuto()

    override fun teleopInit() {
        mode = Mode.TELEOP
        LifecycleDistributor.onStart()
        LifecycleDistributor.onTeleopStart()
    }

    override fun teleopPeriodic() = LifecycleDistributor.executeTeleop()

    private enum class Mode {
        AUTO, TELEOP, DISABLED
    }
}
