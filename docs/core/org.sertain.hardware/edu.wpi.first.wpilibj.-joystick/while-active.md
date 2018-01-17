---
title: whileActive - core
---

[core](../../index.md) / [org.sertain.hardware](../index.md) / [edu.wpi.first.wpilibj.Joystick](index.md) / [whileActive](.)

# whileActive

`fun Joystick.whileActive(button: Int, command: `[`Command`](../../org.sertain.command/-command/index.md)`): Unit`

Constantly starts the given command while the joysticks [button](while-active.md#org.sertain.hardware$whileActive(edu.wpi.first.wpilibj.Joystick, kotlin.Int, org.sertain.command.Command)/button) is held.

### Parameters

`button` - the number of the button to listen for

`command` - the command to execute

`inline fun Joystick.whileActive(button: Int, crossinline block: () -> Boolean): Unit`

Constantly starts the given lambda function while the joysticks [button](while-active.md#org.sertain.hardware$whileActive(edu.wpi.first.wpilibj.Joystick, kotlin.Int, kotlin.Function0((kotlin.Boolean)))/button) is held.

### Parameters

`button` - the number of the button to listen for

`block` - the lambda function to execute