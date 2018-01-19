@file:Suppress("unused", "RedundantVisibilityModifier")
package org.sertain.command

import edu.wpi.first.wpilibj.command.Subsystem
import java.util.concurrent.TimeUnit

private typealias WpiLibCommand = edu.wpi.first.wpilibj.command.Command

/**
 * @see edu.wpi.first.wpilibj.command.Command
 */
public abstract class Command(timeout: Long = 0, unit: TimeUnit = TimeUnit.MILLISECONDS) {
    internal val mirror = CommandMirror(this, timeout, unit)

    /**
     * @see edu.wpi.first.wpilibj.command.Command.requires
     */
    public fun requires(subsystem: Subsystem) = mirror.requires(subsystem)

    /**
     * @see edu.wpi.first.wpilibj.command.Command.start
     */
    public fun start() = mirror.start()

    /**
     * @see edu.wpi.first.wpilibj.command.Command.cancel
     */
    public fun cancel() = mirror.cancel()

    /**
     * @see edu.wpi.first.wpilibj.command.Command.requires
     */
    public open fun onCreate() = Unit

    /**
     * @see edu.wpi.first.wpilibj.command.Command.execute
     */
    public abstract fun execute(): Boolean

    /**
     * @see edu.wpi.first.wpilibj.command.Command.end
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
