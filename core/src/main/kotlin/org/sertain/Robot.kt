@file:Suppress("unused", "RedundantVisibilityModifier")
package org.sertain

import edu.wpi.first.wpilibj.IterativeRobot
import edu.wpi.first.wpilibj.command.Scheduler
import java.util.concurrent.CopyOnWriteArrayList

private typealias LifecycleDistributor = RobotLifecycle.Companion.Distributor

/**
 * Allows for easy access to different lifecycle methods within the robot.
 */
public interface RobotLifecycle {
    /**
     * Indicates robot creation. This method will be called exactly once right after basic robot
     * initialization has occurred. This is a good time to perform any setup necessary for the
     * entire robot's lifetime.
     */
    public fun onCreate() = Unit

    /**
     * Indicates that the robot is being enabled. This method will be called once before the
     * robot becomes enabled in either the teleoperated or autonomous mode. This would be a good
     * time to perform actions to prepare the robot for movement.
     */
    public fun onStart() = Unit

    /**
     * Indicates that the robot is being enabled in the teleoperated mode. This method will be
     * called exactly once immediately before the robot becomes enabled in teleoperated.
     */
    public fun onTeleopStart() = Unit

    /**
     * Indicates that the robot is being enabled in the autonomous mode. This method will be
     * called exactly once before the robot becomes enabled in autonomous.
     */
    public fun onAutoStart() = Unit

    /**
     * Runs periodically (every 20ms) while the robot is turned on. It need not be enabled for this
     * method to be called.
     */
    public fun execute() = Unit

    /**
     * Runs periodically (every 20ms) while the robot is in the teleoperated mode.
     */
    public fun executeTeleop() = Unit

    /**
     * Runs periodically (every 20ms) while the robot is in the autonomous mode.
     */
    public fun executeAuto() = Unit

    /**
     * Indicates that the teleoperated mode has just terminated. This method will be called once
     * immediately after the teleoperated mode has terminated.
     */
    public fun onTeleopStop() = Unit

    /**
     * Indicates that the autonomous mode has just terminated. This method will be called once
     * immediately after the autonomous mode has terminated.
     */
    public fun onAutoStop() = Unit

    /**
     * Indicates that the disabled state has just terminated. This method will be called once
     * immediately after the disabled state has terminated. This method should be equivalent to
     * [onStart].
     */
    public fun onDisabledStop() = Unit

    /**
     * Indicates that the robot has become disabled. This method will be called once upon
     * entering the disabled state.
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
