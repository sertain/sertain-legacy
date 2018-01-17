---
title: org.sertain.hardware - core
---

[core](../index.md) / [org.sertain.hardware](.)

## Package org.sertain.hardware

### Types

| [BreakWhenStarted](-break-when-started/index.md) | `class BreakWhenStarted : `[`RobotLifecycle`](../org.sertain/-robot-lifecycle/index.md)<br>A lifecycle command which will put all specified talons in break mode when the robot is
enabled in either teleop or autonomous, and will disable break mode 5 seconds after the robot
is disabled in order to allow the robot to be pushed around on the field. |

### Extensions for External Classes

| [edu.wpi.first.wpilibj.Joystick](edu.wpi.first.wpilibj.-joystick/index.md) |  |

### Properties

| [encoderPosition](encoder-position.md) | `val <ERROR CLASS>.encoderPosition: Int`<br>Gets the encoder position of the currently selected sensor. |

### Functions

| [autoBreak](auto-break.md) | `fun <ERROR CLASS>.autoBreak(): <ERROR CLASS>`<br>Sets the Talon to use Auto Break mode. Auto Break mode will enable the Talons break mode while
the robot is enabled in either the teleoperated or autonomous modes, but will disable break
mode after 5 seconds of being disabled. This mode is intended to be used on the drivetrain so
that the robot may be pushed around the field when it is disabled. |
| [inverted](inverted.md) | `fun <ERROR CLASS>.inverted(inverted: Boolean = true): <ERROR CLASS>`<br>Sets whether the Talon should be inverted. |
| [plus](plus.md) | `operator fun <ERROR CLASS>.plus(other: <ERROR CLASS>): <ERROR CLASS>`<br>Joins two Talons together by having the second follow the first.`operator fun <ERROR CLASS>.plus(other: <ERROR CLASS>): <ERROR CLASS>`<br>Joins two Victors together by having the second follow the first. |
| [resetEncoder](reset-encoder.md) | `fun <ERROR CLASS>.resetEncoder(device: <ERROR CLASS> = FeedbackDevice.QuadEncoder): <ERROR CLASS>`<br>Resets a given sensor to 0. |
| [setBreak](set-break.md) | `fun <ERROR CLASS>.setBreak(enable: Boolean = true): <ERROR CLASS>`<br>Sets the Talons current mode between either Brake or Coast. |
| [stop](stop.md) | `fun <ERROR CLASS>.stop(): <ERROR CLASS>`<br>Stops the Talon until `set()` is called again.`fun <ERROR CLASS>.stop(): <ERROR CLASS>`<br>Stops the Victor until `set()` is called again. |

