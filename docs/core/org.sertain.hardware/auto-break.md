---
title: autoBreak - core
---

[core](../index.md) / [org.sertain.hardware](index.md) / [autoBreak](.)

# autoBreak

`fun <ERROR CLASS>.autoBreak(): <ERROR CLASS>`

Sets the Talon to use Auto Break mode. Auto Break mode will enable the Talons break mode while
the robot is enabled in either the teleoperated or autonomous modes, but will disable break
mode after 5 seconds of being disabled. This mode is intended to be used on the drivetrain so
that the robot may be pushed around the field when it is disabled.

**Return**
the original Talon

**See Also**

[BreakWhenStarted](-break-when-started/index.md)

