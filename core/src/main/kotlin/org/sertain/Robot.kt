@file:Suppress("unused", "RedundantVisibilityModifier")
@file:JvmName("RobotUtils")
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
     * Indicates that the robot is being enabled in the autonomous mode. This method will be called
     * before the robot becomes enabled in autonomous mode.
     */
    public fun onAutoStart() = Unit

    /**
     * Indicates that the robot is being enabled in the teleoperated mode. This method will be
     * called before the robot becomes enabled in teleoperated mode.
     */
    public fun onTeleopStart() = Unit

    /**
     * Runs periodically (default = every 20ms) while the robot is turned on. It need not be enabled
     * for this method to be called.
     */
    public fun execute() = Unit

    /** Runs periodically (default = every 20ms) while the robot is in the disabled state. */
    public fun executeDisabled() = Unit

    /** Runs periodically (default = every 20ms) while the robot is in the teleoperated mode. */
    public fun executeTeleop() = Unit

    /** Runs periodically (default = every 20ms) while the robot is in the autonomous mode. */
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
     * Indicates that the robot has become disabled. This method will be called upon entering the
     * disabled state.
     */
    public fun onStop() = Unit

    companion object {
        internal var state = Robot.State.DISABLED
        @VisibleForTesting
        internal val listeners = mutableSetOf<RobotLifecycle>()

        /**
         * Adds a listener for [RobotLifecycle] events.
         *
         * @param lifecycle the lifecycle object to receive callbacks
         */
        internal fun addListener(lifecycle: RobotLifecycle) {
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

            override fun onTeleopStart() = notify(Robot.State.TELEOP) { onTeleopStart() }

            override fun onAutoStart() = notify(Robot.State.AUTO) { onAutoStart() }

            override fun execute() = notify { execute() }

            override fun executeDisabled() = notify { executeDisabled() }

            override fun executeTeleop() = notify { executeTeleop() }

            override fun executeAuto() = notify { executeAuto() }

            override fun onTeleopStop() = notify { onTeleopStop() }

            override fun onAutoStop() = notify { onAutoStop() }

            override fun onStop() = notify(Robot.State.DISABLED) { onStop() }

            private inline fun notify(
                    state: Robot.State? = null,
                    block: RobotLifecycle.() -> Unit
            ) {
                state?.let { RobotLifecycle.state = it }
                synchronized(listeners) { for (listener in listeners.toList()) listener.block() }
            }
        }
    }
}

/** Base robot class which must be used for [RobotLifecycle] callbacks to work. */
public abstract class Robot(vararg listeners: RobotLifecycle) : IterativeRobot(), RobotLifecycle {
    private var mode = State.DISABLED
        set(value) {
            if (value != field) {
                when (field) {
                    State.TELEOP -> LifecycleDistributor.onTeleopStop()
                    State.AUTO -> LifecycleDistributor.onAutoStop()
                    State.DISABLED -> Unit
                }
                field = value
            }
        }

    init {
        @Suppress("LeakingThis") // Invoked through reflection and initialized later
        add(this)

        listeners.forEach { add(it) }

        val existingHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            println("Exception occurred on thread $t:")
            e.printStackTrace()
            existingHandler.uncaughtException(t, e)
        }
    }

    override fun robotInit() = LifecycleDistributor.onCreate()

    override fun robotPeriodic() {
        Scheduler.getInstance().run()
        LifecycleDistributor.execute()
    }

    override fun disabledInit() {
        mode = State.DISABLED
        LifecycleDistributor.onStop()
    }

    override fun disabledPeriodic() = LifecycleDistributor.executeDisabled()

    override fun autonomousInit() {
        mode = State.AUTO
        LifecycleDistributor.onStart()
        LifecycleDistributor.onAutoStart()
    }

    override fun autonomousPeriodic() = LifecycleDistributor.executeAuto()

    override fun teleopInit() {
        mode = State.TELEOP
        LifecycleDistributor.onStart()
        LifecycleDistributor.onTeleopStart()
    }

    override fun teleopPeriodic() = LifecycleDistributor.executeTeleop()

    internal enum class State {
        DISABLED, AUTO, TELEOP
    }
}

/**
 * @see RobotLifecycle.addListener
 */
public fun add(lifecycle: RobotLifecycle) {
    RobotLifecycle.addListener(lifecycle)
}

public operator fun RobotLifecycle.unaryPlus() = add(this)

/**
 * @see RobotLifecycle.removeListener
 */
public fun remove(lifecycle: RobotLifecycle) {
    RobotLifecycle.removeListener(lifecycle)
}

public operator fun RobotLifecycle.unaryMinus() = remove(this)
