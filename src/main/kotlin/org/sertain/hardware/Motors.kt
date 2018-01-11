package org.sertain.hardware

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX

typealias Talon = WPI_TalonSRX
typealias Victor = WPI_VictorSPX

operator fun Talon.plus (other: Talon) = apply { other.follow(this) }
operator fun Victor.plus (other: Victor) = apply { other.follow(this) }
