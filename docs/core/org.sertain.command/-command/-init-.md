---
title: Command.<init> - core
---

[core](../../index.md) / [org.sertain.command](../index.md) / [Command](index.md) / [&lt;init&gt;](.)

# &lt;init&gt;

`Command(timeout: Long = 0, unit: `[`TimeUnit`](http://docs.oracle.com/javase/6/docs/api/java/util/concurrent/TimeUnit.html)` = TimeUnit.MILLISECONDS)`

A command to run on the robot. The command may require a subsystem in order to ensure that only one command is
running for a given subsystem at a time.

### Parameters

`timeout` - the maximum amount of time this command should be run for

`unit` - the unit of time for timeout