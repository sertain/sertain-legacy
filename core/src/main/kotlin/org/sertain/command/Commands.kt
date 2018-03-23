@file:Suppress("unused", "RedundantVisibilityModifier")
@file:JvmName("CommandUtils")
package org.sertain.command

import android.support.annotation.VisibleForTesting
import edu.wpi.first.wpilibj.command.Command as WpiLibCommand
import edu.wpi.first.wpilibj.command.CommandGroup as WpiLibCommandGroup
import edu.wpi.first.wpilibj.command.PIDCommand as WpiLibPidCommand
import edu.wpi.first.wpilibj.command.Subsystem as WpiLibSubsystem

/** @see CommandGroup.addSequential */
public infix fun CommandBridgeMirror.then(command: CommandBridgeMirror) =
        CommandGroup().addSequential(this).addSequential(command)

/** @see CommandGroup.addParallel */
public infix fun CommandBridgeMirror.and(command: CommandBridgeMirror) =
        CommandGroup().addParallel(this).addParallel(command)

/** @see CommandGroup.addSequential */
public infix fun CommandGroup.then(command: CommandBridgeMirror) = addSequential(command)

/** @see CommandGroup.addParallel */
public infix fun CommandGroup.and(command: CommandBridgeMirror) = addParallel(command)

public interface Requirable {
    /** @see edu.wpi.first.wpilibj.command.Command.requires */
    fun requires(subsystem: Subsystem)
}

public interface CommandBridge : Requirable {
    /** @see edu.wpi.first.wpilibj.command.Command.start */
    fun start()

    /** @see edu.wpi.first.wpilibj.command.Command.cancel */
    fun cancel()

    /** @see edu.wpi.first.wpilibj.command.Command.initialize */
    fun onCreate() = Unit

    /** @see edu.wpi.first.wpilibj.command.Command.execute */
    fun execute(): Boolean

    /** @see edu.wpi.first.wpilibj.command.Command.end */
    fun onDestroy() = Unit
}

public abstract class CommandBridgeMirror : CommandBridge {
    internal abstract val mirror: WpiLibCommand

    override fun start() = mirror.start()

    override fun cancel() = mirror.cancel()
}

/** @see edu.wpi.first.wpilibj.command.Command */
public abstract class Command @JvmOverloads constructor(
        timeoutMillis: Long? = null
) : CommandBridgeMirror() {
    override val mirror = if (timeoutMillis == null || timeoutMillis <= 0) {
        CommandMirror(this)
    } else {
        CommandMirror(this, timeoutMillis)
    }

    override fun requires(subsystem: Subsystem) = mirror.requires(subsystem)
}

/** @see edu.wpi.first.wpilibj.command.PIDCommand */
public abstract class PidCommand @JvmOverloads constructor(
        p: Double,
        i: Double = 0.0,
        d: Double = 0.0
) : CommandBridgeMirror() {
    override val mirror = PidCommandMirror(this, p, i, d)

    /**
     * @see edu.wpi.first.wpilibj.command.PIDCommand.getSetpoint
     * @see edu.wpi.first.wpilibj.command.PIDCommand.setSetpoint
     */
    protected var setpoint: Double
        get() = mirror.setpoint
        set(value) {
            mirror.setpoint = value
        }

    /** @see edu.wpi.first.wpilibj.command.PIDCommand.setInputRange */
    protected var inputRange: ClosedRange<Double> = 0.0..0.0
        set(value) {
            field = value
            mirror.setInputRange(value.start, value.endInclusive)
        }

    override fun requires(subsystem: Subsystem) = mirror.requires(subsystem)

    override fun execute() = execute(mirror.pidOutput)

    /**
     * @see edu.wpi.first.wpilibj.command.PIDCommand.execute
     * @see edu.wpi.first.wpilibj.command.PIDCommand.usePIDOutput
     */
    public abstract fun execute(output: Double): Boolean

    /** @see edu.wpi.first.wpilibj.command.PIDCommand.returnPIDInput */
    public abstract fun returnPidInput(): Double
}

public class CommandGroup : CommandBridgeMirror() {
    private val _mirror by lazy {
        CommandGroupMirror(this).apply { addQueuedCommands() }
    }
    override val mirror get() = _mirror
    @VisibleForTesting
    internal val entries = mutableListOf<Entry>()

    override fun requires(subsystem: Subsystem) = mirror.requires(subsystem)

    override fun execute() = false

    /** @see edu.wpi.first.wpilibj.command.CommandGroup.addSequential */
    public fun addSequential(command: CommandBridgeMirror, timeout: Double? = null): CommandGroup {
        entries += Entry(true, command, timeout)
        return this
    }

    /** @see edu.wpi.first.wpilibj.command.CommandGroup.addParallel */
    public fun addParallel(command: CommandBridgeMirror, timeout: Double? = null): CommandGroup {
        entries += Entry(false, command, timeout)
        return this
    }

    private fun addQueuedCommands() {
        postfixQueuedCommands()
        for (entry in entries) {
            entry.apply {
                (command as? CommandGroup)?.addQueuedCommands()
                if (sequential) {
                    if (timeout == null) {
                        mirror.addSequential(command.mirror)
                    } else {
                        mirror.addSequential(command.mirror, timeout)
                    }
                } else {
                    if (timeout == null) {
                        mirror.addParallel(command.mirror)
                    } else {
                        mirror.addParallel(command.mirror, timeout)
                    }
                }
            }
        }
    }

    @VisibleForTesting
    internal fun postfixQueuedCommands() {
        // Avoid infinite recursion with postfixed nested command groups.
        if (entries.all { it.parallel } || entries.all { it.sequential }) return

        for (entry in entries.toList()) {
            val prevIndex = entries.indexOf(entry) - 1
            val isLastParallel = entries.last() === entry && entry.parallel
            if (entry.sequential && entries.getOrNull(prevIndex)?.parallel == true
                    || isLastParallel) {
                val endIndex = prevIndex + if (isLastParallel) 1 else 0
                // Walk back up the stack to find all linear parallel commands
                for ((trace, prev) in entries.slice(0..endIndex).reversed().withIndex()) {
                    // Wait until we reach the first sequential entry
                    if (prev.parallel && trace != endIndex) continue

                    val start = endIndex - trace
                    val parallels = entries.slice(start..endIndex)
                    entries.removeAll(parallels)
                    entries.add(start, Entry(true, CommandGroup().apply {
                        for ((_, command, timeout) in parallels) addParallel(command, timeout)
                    }))

                    break
                }
            }
        }
    }

    @VisibleForTesting
    internal data class Entry(
            val sequential: Boolean,
            val command: CommandBridgeMirror,
            val timeout: Double? = null
    ) {
        inline val parallel get() = !sequential
    }
}

/** A mirror of WPILib's Command class. */
internal class CommandMirror : WpiLibCommand {
    private val command: Command

    constructor(command: Command) : super() {
        this.command = command
    }

    constructor(command: Command, timeoutMillis: Long) : super(timeoutMillis / 1000.0) {
        this.command = command
    }

    @Suppress("RedundantOverride") // Needed for visibility override
    public override fun requires(subsystem: WpiLibSubsystem) = super.requires(subsystem)

    override fun initialize() = command.onCreate()

    override fun isFinished(): Boolean = command.execute() || isTimedOut

    override fun end() = command.onDestroy()

    override fun execute() = Unit
}

/** A mirror of WPILib's PIDCommand class. */
internal class PidCommandMirror(
        private val command: PidCommand,
        p: Double,
        i: Double,
        d: Double
) : WpiLibPidCommand(p, i, d) {
    internal var pidOutput = 0.0

    @Suppress("RedundantOverride") // Needed for visibility override
    public override fun requires(subsystem: WpiLibSubsystem) = super.requires(subsystem)

    @Suppress("RedundantOverride") // Needed for visibility override
    public override fun setSetpoint(setpoint: Double) = super.setSetpoint(setpoint)

    @Suppress("RedundantOverride") // Needed for visibility override
    public override fun getSetpoint() = super.getSetpoint()

    @Suppress("RedundantOverride") // Needed for visibility override
    public override fun setInputRange(minimumInput: Double, maximumInput: Double) =
            super.setInputRange(minimumInput, maximumInput)

    override fun initialize() = command.onCreate()

    override fun isFinished() = command.execute()

    override fun returnPIDInput() = command.returnPidInput()

    override fun usePIDOutput(output: Double) {
        pidOutput = output
    }

    override fun end() = command.onDestroy()

    override fun execute() = Unit
}

/** A mirror of WPILib's CommandGroup class. */
internal class CommandGroupMirror(private val command: CommandGroup) : WpiLibCommandGroup() {
    @Suppress("RedundantOverride") // Needed for visibility override
    public override fun requires(subsystem: WpiLibSubsystem) = super.requires(subsystem)

    override fun initialize() = command.onCreate()

    override fun isFinished() = super.isFinished() || command.execute()

    override fun end() = command.onDestroy()
}
