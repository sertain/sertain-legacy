@file:Suppress("unused", "RedundantVisibilityModifier")
package org.sertain.command

import edu.wpi.first.wpilibj.command.Subsystem
import java.util.concurrent.TimeUnit

private typealias WpiLibCommand = edu.wpi.first.wpilibj.command.Command

/**
 * A command to run on the robot. The command may require a subsystem in order to ensure that only one command is
 * running for a given subsystem at a time.
 *
 * @param timeout the maximum amount of time this command should be run for
 * @param unit the unit of time for timeout
 */
public abstract class Command(timeout: Long = 0, unit: TimeUnit = TimeUnit.MILLISECONDS) {
    internal val mirror = CommandMirror(this, timeout, unit)

    /**
     * The subsystem this command requires or depends upon. If used, this command will interrupt
     * and be interrupted by other commands requiring the same subsystem.
     *
     * @param subsystem the subsystem this command requires
     */
    public fun requires(subsystem: Subsystem) = mirror.requires(subsystem)

    /**
     * Starts the command.
     */
    public fun start() = mirror.start()

    /**
     * Cancels the command.
     */
    public fun cancel() = mirror.cancel()

    /**
     * Indicates command creation. This method will be called exactly once right after the command
     * has been created. This is a good time to perform any setup necessary for the entire
     * command's lifetime.
     */
    public open fun onCreate() = Unit

    /**
     * Runs periodically (every 20ms) while the command is running.
     *
     * @return whether the command is finished
     */
    public abstract fun execute(): Boolean

    /**
     * Indicates command deletion. This method will be called exactly once right after the command
     * has been destroyed.
     */
    public open fun onDestroy() = Unit
}

private interface Requirable {
    fun requires(subsystem: Subsystem)
}

/**
 * A mirror of WPILib's Command class.
 */
internal class CommandMirror(
        private val command: Command,
        timeout: Long,
        unit: TimeUnit
) : WpiLibCommand(unit.toSeconds(timeout).toDouble()), Requirable {
    @Suppress("RedundantOverride") // Needed for visibility override
    public override fun requires(subsystem: Subsystem) = super.requires(subsystem)

    override fun initialize() = command.onCreate()

    override fun isFinished(): Boolean = command.execute()

    override fun end() = command.onDestroy()

    override fun execute() = Unit
}
