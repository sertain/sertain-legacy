---
title: org.sertain.hardware.edu.wpi.first.wpilibj.Joystick - core
---

[core](../../index.md) / [org.sertain.hardware](../index.md) / [edu.wpi.first.wpilibj.Joystick](.)

### Extensions for edu.wpi.first.wpilibj.Joystick

| [whenActive](when-active.md) | `fun Joystick.whenActive(button: Int, command: `[`Command`](../../org.sertain.command/-command/index.md)`): Unit`<br>Executes the given command once when a joysticks [button](when-active.md#org.sertain.hardware$whenActive(edu.wpi.first.wpilibj.Joystick, kotlin.Int, org.sertain.command.Command)/button) is pressed.`fun Joystick.whenActive(button: Int, block: () -> Boolean): Unit`<br>Executes the given lambda function once when a joysticks [button](when-active.md#org.sertain.hardware$whenActive(edu.wpi.first.wpilibj.Joystick, kotlin.Int, kotlin.Function0((kotlin.Boolean)))/button) is pressed. |
| [whileActive](while-active.md) | `fun Joystick.whileActive(button: Int, command: `[`Command`](../../org.sertain.command/-command/index.md)`): Unit`<br>Constantly starts the given command while the joysticks [button](while-active.md#org.sertain.hardware$whileActive(edu.wpi.first.wpilibj.Joystick, kotlin.Int, org.sertain.command.Command)/button) is held.`fun Joystick.whileActive(button: Int, block: () -> Boolean): Unit`<br>Constantly starts the given lambda function while the joysticks [button](while-active.md#org.sertain.hardware$whileActive(edu.wpi.first.wpilibj.Joystick, kotlin.Int, kotlin.Function0((kotlin.Boolean)))/button) is held. |

