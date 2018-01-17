---
title: Robot - core
---

[core](../../index.md) / [org.sertain](../index.md) / [Robot](.)

# Robot

`abstract class Robot : IterativeRobot, `[`RobotLifecycle`](../-robot-lifecycle/index.md)

### Constructors

| [&lt;init&gt;](-init-.md) | `Robot()` |

### Functions

| [autonomousInit](autonomous-init.md) | `open fun autonomousInit(): Unit` |
| [autonomousPeriodic](autonomous-periodic.md) | `open fun autonomousPeriodic(): Unit` |
| [disabledInit](disabled-init.md) | `open fun disabledInit(): <ERROR CLASS>` |
| [disabledPeriodic](disabled-periodic.md) | `open fun disabledPeriodic(): Unit` |
| [robotInit](robot-init.md) | `open fun robotInit(): <ERROR CLASS>` |
| [robotPeriodic](robot-periodic.md) | `open fun robotPeriodic(): Unit` |
| [teleopInit](teleop-init.md) | `open fun teleopInit(): Unit` |
| [teleopPeriodic](teleop-periodic.md) | `open fun teleopPeriodic(): Unit` |

### Inherited Functions

| [execute](../-robot-lifecycle/execute.md) | `open fun execute(): Unit`<br>Runs periodically (every 20ms) while the robot is turned on. It need not be enabled for this
method to be called. |
| [executeAuto](../-robot-lifecycle/execute-auto.md) | `open fun executeAuto(): Unit`<br>Runs periodically (every 20ms) while the robot is in the autonomous mode. |
| [executeDisabled](../-robot-lifecycle/execute-disabled.md) | `open fun executeDisabled(): Unit`<br>Runs periodically (every 20ms) while the robot is in the disabled state. |
| [executeTeleop](../-robot-lifecycle/execute-teleop.md) | `open fun executeTeleop(): Unit`<br>Runs periodically (every 20ms) while the robot is in the teleoperated mode. |
| [onAutoStart](../-robot-lifecycle/on-auto-start.md) | `open fun onAutoStart(): Unit`<br>Indicates that the robot is being enabled in the autonomous mode. This method will be
called exactly once before the robot becomes enabled in autonomous. |
| [onAutoStop](../-robot-lifecycle/on-auto-stop.md) | `open fun onAutoStop(): Unit`<br>Indicates that the autonomous mode has just terminated. This method will be called once
immediately after the autonomous mode has terminated. |
| [onCreate](../-robot-lifecycle/on-create.md) | `open fun onCreate(): Unit`<br>Indicates robot creation. This method will be called exactly once right after basic robot
initialization has occurred. This is a good time to perform any setup necessary for the
entire robots lifetime. |
| [onDisabledStop](../-robot-lifecycle/on-disabled-stop.md) | `open fun onDisabledStop(): Unit`<br>Indicates that the disabled state has just terminated. This method will be called once
immediately after the disabled state has terminated. This method should be equivalent to
[onStart](../-robot-lifecycle/on-start.md). |
| [onStart](../-robot-lifecycle/on-start.md) | `open fun onStart(): Unit`<br>Indicates that the robot is being enabled. This method will be called once before the
robot becomes enabled in either the teleoperated or autonomous mode. This would be a good
time to perform actions to prepare the robot for movement. |
| [onStop](../-robot-lifecycle/on-stop.md) | `open fun onStop(): Unit`<br>Indicates that the robot has become disabled. This method will be called once upon
entering the disabled state. |
| [onTeleopStart](../-robot-lifecycle/on-teleop-start.md) | `open fun onTeleopStart(): Unit`<br>Indicates that the robot is being enabled in the teleoperated mode. This method will be
called exactly once immediately before the robot becomes enabled in teleoperated. |
| [onTeleopStop](../-robot-lifecycle/on-teleop-stop.md) | `open fun onTeleopStop(): Unit`<br>Indicates that the teleoperated mode has just terminated. This method will be called once
immediately after the teleoperated mode has terminated. |

