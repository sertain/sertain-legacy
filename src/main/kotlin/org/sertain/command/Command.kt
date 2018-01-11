package org.sertain.command

import edu.wpi.first.wpilibj.command.Subsystem
import java.util.concurrent.TimeUnit

private typealias WpiLibCommand = edu.wpi.first.wpilibj.command.Command

abstract class Command(timeout: Long = 0, unit: TimeUnit = TimeUnit.MILLISECONDS) {
    private val command = CommandMirror(this, timeout, unit)

    fun requires(subsystem: Subsystem) = command.requires(subsystem)

    fun start() = command.start()

    fun cancel() = command.cancel()

    fun onCreate() = Unit

    abstract fun execute(): Boolean

    fun onDestroy() = Unit
}

private interface Requirable {
    fun requires(subsystem: Subsystem)
}

private class CommandMirror(
        private val command: Command,
        timeout: Long,
        unit: TimeUnit
) : WpiLibCommand(unit.toSeconds(timeout).toDouble()), Requirable {
    @Suppress("RedundantOverride") // Needed for visibility override
    override fun requires(subsystem: Subsystem) = super.requires(subsystem)

    override fun initialize() = command.onCreate()

    override fun isFinished(): Boolean = command.execute()

    override fun end() = command.onDestroy()

    override fun execute() = Unit
}
