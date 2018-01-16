@file:Suppress("unused", "RedundantVisibilityModifier")
package org.sertain.command

import edu.wpi.first.wpilibj.command.Subsystem
import java.util.concurrent.TimeUnit

private typealias WpiLibCommand = edu.wpi.first.wpilibj.command.Command

/**
 * A command to run on the robot. The command may require a subsystem in order to ensure that only one command is
 * running for a given subsytem at a time.
 *
 * @property timeout the maximum amount of time this command should be run for
 * @property unit the unit of time for timeout
 */
public abstract class Command(timeout: Long = 0, unit: TimeUnit = TimeUnit.MILLISECONDS) {
    private val command = CommandMirror(this, timeout, unit)

    /**
     * The subsystem this command requires or depends upon. If used, this command will interrupt and be interrupted by
     * other commands requiring the same subsystem.
     *
     * @param subsystem the subsystem this command requires
     */
    public fun requires(subsystem: Subsystem) = command.requires(subsystem)

    /**
     * Start the command.
     */
    public fun start() = command.start()

    /**
     * Cancel the command.
     */
    public fun cancel() = command.cancel()

    /**
     * Lifecycle method which is executed immediately upon the creation of the command.
     */
    public open fun onCreate() = Unit

    /**
     * Lifecycle method which is executed periodically (every 20ms) throughout the command's execution.
     */
    public abstract fun execute(): Boolean

    /**
     * Lifecycle method which is executed immediately upon the destruction of the command.
     */
    public open fun onDestroy() = Unit
}

/**
 * A mirror of WPILib's Command class.
 */
private class CommandMirror(
        private val command: Command,
        timeout: Long,
        unit: TimeUnit
) : WpiLibCommand(unit.toSeconds(timeout).toDouble()) {
    public override fun requires(subsystem: Subsystem) = super.requires(subsystem)

    override fun initialize() = command.onCreate()

    override fun isFinished(): Boolean = command.execute()

    override fun end() = command.onDestroy()

    override fun execute() = Unit
}
