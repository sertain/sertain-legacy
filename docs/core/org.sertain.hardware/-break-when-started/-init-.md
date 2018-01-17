---
title: BreakWhenStarted.<init> - core
---

[core](../../index.md) / [org.sertain.hardware](../index.md) / [BreakWhenStarted](index.md) / [&lt;init&gt;](.)

# &lt;init&gt;

`BreakWhenStarted(vararg talons: <ERROR CLASS>)`

A lifecycle command which will put all specified talons in break mode when the robot is
enabled in either teleop or autonomous, and will disable break mode 5 seconds after the robot
is disabled in order to allow the robot to be pushed around on the field.

### Parameters

`talons` - the Talons to enable Auto Break on

**See Also**

[autoBreak](../auto-break.md)

