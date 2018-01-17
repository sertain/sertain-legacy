---
title: RobotLifecycle - core
---

[core](../../index.md) / [org.sertain](../index.md) / [RobotLifecycle](.)

# RobotLifecycle

`interface RobotLifecycle`

Allows for easy access to different lifecycle methods within the robot.

### Functions

| [execute](execute.md) | `open fun execute(): Unit`<br>Runs periodically (every 20ms) while the robot is turned on. It need not be enabled for this
method to be called. |
| [executeAuto](execute-auto.md) | `open fun executeAuto(): Unit`<br>Runs periodically (every 20ms) while the robot is in the autonomous mode. |
| [executeDisabled](execute-disabled.md) | `open fun executeDisabled(): Unit`<br>Runs periodically (every 20ms) while the robot is in the disabled state. |
| [executeTeleop](execute-teleop.md) | `open fun executeTeleop(): Unit`<br>Runs periodically (every 20ms) while the robot is in the teleoperated mode. |
| [onAutoStart](on-auto-start.md) | `open fun onAutoStart(): Unit`<br>Indicates that the robot is being enabled in the autonomous mode. This method will be
called exactly once before the robot becomes enabled in autonomous. |
| [onAutoStop](on-auto-stop.md) | `open fun onAutoStop(): Unit`<br>Indicates that the autonomous mode has just terminated. This method will be called once
immediately after the autonomous mode has terminated. |
| [onCreate](on-create.md) | `open fun onCreate(): Unit`<br>Indicates robot creation. This method will be called exactly once right after basic robot
initialization has occurred. This is a good time to perform any setup necessary for the
entire robots lifetime. |
| [onDisabledStop](on-disabled-stop.md) | `open fun onDisabledStop(): Unit`<br>Indicates that the disabled state has just terminated. This method will be called once
immediately after the disabled state has terminated. This method should be equivalent to
[onStart](on-start.md). |
| [onStart](on-start.md) | `open fun onStart(): Unit`<br>Indicates that the robot is being enabled. This method will be called once before the
robot becomes enabled in either the teleoperated or autonomous mode. This would be a good
time to perform actions to prepare the robot for movement. |
| [onStop](on-stop.md) | `open fun onStop(): Unit`<br>Indicates that the robot has become disabled. This method will be called once upon
entering the disabled state. |
| [onTeleopStart](on-teleop-start.md) | `open fun onTeleopStart(): Unit`<br>Indicates that the robot is being enabled in the teleoperated mode. This method will be
called exactly once immediately before the robot becomes enabled in teleoperated. |
| [onTeleopStop](on-teleop-stop.md) | `open fun onTeleopStop(): Unit`<br>Indicates that the teleoperated mode has just terminated. This method will be called once
immediately after the teleoperated mode has terminated. |

### Companion Object Functions

| [addListener](add-listener.md) | `fun addListener(lifecycle: RobotLifecycle): Unit`<br>Adds a new listener for [lifecycle](add-listener.md#org.sertain.RobotLifecycle.Companion$addListener(org.sertain.RobotLifecycle)/lifecycle). |
| [removeListener](remove-listener.md) | `fun removeListener(lifecycle: RobotLifecycle): Unit`<br>Removes a new listener for [lifecycle](remove-listener.md#org.sertain.RobotLifecycle.Companion$removeListener(org.sertain.RobotLifecycle)/lifecycle). |

### Inheritors

| [BreakWhenStarted](../../org.sertain.hardware/-break-when-started/index.md) | `class BreakWhenStarted : RobotLifecycle`<br>A lifecycle command which will put all specified talons in break mode when the robot is
enabled in either teleop or autonomous, and will disable break mode 5 seconds after the robot
is disabled in order to allow the robot to be pushed around on the field. |
| [Robot](../-robot/index.md) | `abstract class Robot : IterativeRobot, RobotLifecycle` |

