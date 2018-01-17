---
title: BreakWhenStarted - core
---

[core](../../index.md) / [org.sertain.hardware](../index.md) / [BreakWhenStarted](.)

# BreakWhenStarted

`class BreakWhenStarted : `[`RobotLifecycle`](../../org.sertain/-robot-lifecycle/index.md)

A lifecycle command which will put all specified talons in break mode when the robot is
enabled in either teleop or autonomous, and will disable break mode 5 seconds after the robot
is disabled in order to allow the robot to be pushed around on the field.

### Parameters

`talons` - the Talons to enable Auto Break on

**See Also**

[autoBreak](../auto-break.md)

### Constructors

| [&lt;init&gt;](-init-.md) | `BreakWhenStarted(vararg talons: <ERROR CLASS>)`<br>A lifecycle command which will put all specified talons in break mode when the robot is
enabled in either teleop or autonomous, and will disable break mode 5 seconds after the robot
is disabled in order to allow the robot to be pushed around on the field. |

### Functions

| [onStart](on-start.md) | `fun onStart(): Unit`<br>Indicates that the robot is being enabled. This method will be called once before the
robot becomes enabled in either the teleoperated or autonomous mode. This would be a good
time to perform actions to prepare the robot for movement. |
| [onStop](on-stop.md) | `fun onStop(): Unit`<br>Indicates that the robot has become disabled. This method will be called once upon
entering the disabled state. |

### Inherited Functions

| [execute](../../org.sertain/-robot-lifecycle/execute.md) | `open fun execute(): Unit`<br>Runs periodically (every 20ms) while the robot is turned on. It need not be enabled for this
method to be called. |
| [executeAuto](../../org.sertain/-robot-lifecycle/execute-auto.md) | `open fun executeAuto(): Unit`<br>Runs periodically (every 20ms) while the robot is in the autonomous mode. |
| [executeDisabled](../../org.sertain/-robot-lifecycle/execute-disabled.md) | `open fun executeDisabled(): Unit`<br>Runs periodically (every 20ms) while the robot is in the disabled state. |
| [executeTeleop](../../org.sertain/-robot-lifecycle/execute-teleop.md) | `open fun executeTeleop(): Unit`<br>Runs periodically (every 20ms) while the robot is in the teleoperated mode. |
| [onAutoStart](../../org.sertain/-robot-lifecycle/on-auto-start.md) | `open fun onAutoStart(): Unit`<br>Indicates that the robot is being enabled in the autonomous mode. This method will be
called exactly once before the robot becomes enabled in autonomous. |
| [onAutoStop](../../org.sertain/-robot-lifecycle/on-auto-stop.md) | `open fun onAutoStop(): Unit`<br>Indicates that the autonomous mode has just terminated. This method will be called once
immediately after the autonomous mode has terminated. |
| [onCreate](../../org.sertain/-robot-lifecycle/on-create.md) | `open fun onCreate(): Unit`<br>Indicates robot creation. This method will be called exactly once right after basic robot
initialization has occurred. This is a good time to perform any setup necessary for the
entire robots lifetime. |
| [onDisabledStop](../../org.sertain/-robot-lifecycle/on-disabled-stop.md) | `open fun onDisabledStop(): Unit`<br>Indicates that the disabled state has just terminated. This method will be called once
immediately after the disabled state has terminated. This method should be equivalent to
[onStart](../../org.sertain/-robot-lifecycle/on-start.md). |
| [onTeleopStart](../../org.sertain/-robot-lifecycle/on-teleop-start.md) | `open fun onTeleopStart(): Unit`<br>Indicates that the robot is being enabled in the teleoperated mode. This method will be
called exactly once immediately before the robot becomes enabled in teleoperated. |
| [onTeleopStop](../../org.sertain/-robot-lifecycle/on-teleop-stop.md) | `open fun onTeleopStop(): Unit`<br>Indicates that the teleoperated mode has just terminated. This method will be called once
immediately after the teleoperated mode has terminated. |

