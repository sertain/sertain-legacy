---
title: Command - core
---

[core](../../index.md) / [org.sertain.command](../index.md) / [Command](.)

# Command

`abstract class Command`

A command to run on the robot. The command may require a subsystem in order to ensure that only one command is
running for a given subsystem at a time.

### Parameters

`timeout` - the maximum amount of time this command should be run for

`unit` - the unit of time for timeout

### Constructors

| [&lt;init&gt;](-init-.md) | `Command(timeout: Long = 0, unit: `[`TimeUnit`](http://docs.oracle.com/javase/6/docs/api/java/util/concurrent/TimeUnit.html)` = TimeUnit.MILLISECONDS)`<br>A command to run on the robot. The command may require a subsystem in order to ensure that only one command is
running for a given subsystem at a time. |

### Functions

| [cancel](cancel.md) | `fun cancel(): <ERROR CLASS>`<br>Cancels the command. |
| [execute](execute.md) | `abstract fun execute(): Boolean`<br>Runs periodically (every 20ms) while the command is running. |
| [onCreate](on-create.md) | `open fun onCreate(): Unit`<br>Indicates command creation. This method will be called exactly once right after the command
has been created. This is a good time to perform any setup necessary for the entire
commands lifetime. |
| [onDestroy](on-destroy.md) | `open fun onDestroy(): Unit`<br>Indicates command deletion. This method will be called exactly once right after the command
has been destroyed. |
| [requires](requires.md) | `fun requires(subsystem: Subsystem): Unit`<br>The subsystem this command requires or depends upon. If used, this command will interrupt
and be interrupted by other commands requiring the same subsystem. |
| [start](start.md) | `fun start(): <ERROR CLASS>`<br>Starts the command. |

