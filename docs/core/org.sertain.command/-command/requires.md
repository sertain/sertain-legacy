---
title: Command.requires - core
---

[core](../../index.md) / [org.sertain.command](../index.md) / [Command](index.md) / [requires](.)

# requires

`fun requires(subsystem: Subsystem): Unit`

The subsystem this command requires or depends upon. If used, this command will interrupt
and be interrupted by other commands requiring the same subsystem.

### Parameters

`subsystem` - the subsystem this command requires