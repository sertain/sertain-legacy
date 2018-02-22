package org.sertain

import org.junit.Test
import org.sertain.command.Command
import org.sertain.command.CommandGroup
import org.sertain.command.and
import org.sertain.command.then
import kotlin.test.assertTrue

class Commands {
    private val c get() = NoopCommand()

    @Test
    fun `short simple sequential`() {
        val entries = process(c then c)

        assertTrue(entries.size == 2)
        assertTrue(entries.all { it.sequential })
    }

    @Test
    fun `short simple parallel`() {
        val entries = process(c and c)

        assertTrue(entries.size == 2)
        assertTrue(entries.all { it.parallel })
    }

    @Test
    fun `medium simple sequential`() {
        val entries = process(c then c then c)

        assertTrue(entries.size == 3)
        assertTrue(entries.all { it.sequential })
    }

    @Test
    fun `medium simple parallel`() {
        val entries = process(c and c and c)

        assertTrue(entries.size == 3)
        assertTrue(entries.all { it.parallel })
    }

    @Test
    fun `long simple sequential`() {
        val entries = process(c then c then c then c)

        assertTrue(entries.size == 4)
        assertTrue(entries.all { it.sequential })
    }

    @Test
    fun `long simple parallel`() {
        val entries = process(c and c and c and c)

        assertTrue(entries.size == 4)
        assertTrue(entries.all { it.parallel })
    }

    @Test
    fun `start with sequential, then parallel`() {
        val first = c
        val second = c
        val last = c
        val entries = process(first then second and last)

        assertTrue(entries.size == 2)
        assertTrue(entries.all { it.sequential })
        assertTrue(entries.first().command === first)
        assertTrue(entries.last().command is CommandGroup)
        (entries.last().command as CommandGroup).apply {
            assertTrue(this.entries.size == 2)
            assertTrue(this.entries.all { it.parallel })
            assertTrue(this.entries.first().command === second)
            assertTrue(this.entries.last().command === last)
        }
    }

    @Test
    fun `start with parallel, then sequential`() {
        val first = c
        val second = c
        val last = c
        val entries = process(first and second then last)

        assertTrue(entries.size == 2)
        assertTrue(entries.all { it.sequential })
        assertTrue(entries.first().command is CommandGroup)
        (entries.first().command as CommandGroup).apply {
            assertTrue(this.entries.size == 2)
            assertTrue(this.entries.all { it.parallel })
            assertTrue(this.entries.first().command === first)
            assertTrue(this.entries.last().command === second)
        }
        assertTrue(entries.last().command === last)
    }

    @Test
    fun `long, random mix of sequentials and parallels`() {
        val `1st` = c
        val `2nd` = c
        val `3rd` = c
        val `4th` = c
        val `5th` = c
        val `6th` = c
        val entries = process(`1st` then `2nd` and `3rd` and `4th` then `5th` and `6th`)

        assertTrue(entries.size == 3)
        assertTrue(entries.all { it.sequential })
        assertTrue(entries.first().command === `1st`)
        assertTrue(entries[1].command is CommandGroup)
        (entries[1].command as CommandGroup).apply {
            assertTrue(this.entries.size == 3)
            assertTrue(this.entries.all { it.parallel })
            assertTrue(this.entries.first().command === `2nd`)
            assertTrue(this.entries[1].command === `3rd`)
            assertTrue(this.entries.last().command === `4th`)
        }
        (entries[2].command as CommandGroup).apply {
            assertTrue(this.entries.size == 2)
            assertTrue(this.entries.all { it.parallel })
            assertTrue(this.entries.first().command === `5th`)
            assertTrue(this.entries.last().command === `6th`)
        }
    }

    private fun process(group: CommandGroup): List<CommandGroup.Entry> {
        group.postfixQueuedCommands()
        return group.entries
    }

    private class NoopCommand : Command() {
        override fun execute() = true
    }
}
