package org.sertain.hardware

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX

typealias Talon = WPI_TalonSRX

operator fun Talon.plus (other: Talon) = apply { other.follow(this) }
