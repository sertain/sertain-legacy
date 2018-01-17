---
title: whenActive - core
---

[core](../../index.md) / [org.sertain.hardware](../index.md) / [edu.wpi.first.wpilibj.Joystick](index.md) / [whenActive](.)

# whenActive

`fun Joystick.whenActive(button: Int, command: `[`Command`](../../org.sertain.command/-command/index.md)`): Unit`

Executes the given command once when a joysticks [button](when-active.md#org.sertain.hardware$whenActive(edu.wpi.first.wpilibj.Joystick, kotlin.Int, org.sertain.command.Command)/button) is pressed.

### Parameters

`button` - the number of the button to listen for

`command` - the command to execute

`inline fun Joystick.whenActive(button: Int, crossinline block: () -> Boolean): Unit`

Executes the given lambda function once when a joysticks [button](when-active.md#org.sertain.hardware$whenActive(edu.wpi.first.wpilibj.Joystick, kotlin.Int, kotlin.Function0((kotlin.Boolean)))/button) is pressed.

### Parameters

`button` - the number of the button to listen for

`block` - the lambda function to execute