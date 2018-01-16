@file:Suppress("unused", "RedundantVisibilityModifier")
package org.sertain.hardware

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX

public typealias Talon = WPI_TalonSRX
public typealias Victor = WPI_VictorSPX

public operator fun Talon.plus(other: Talon) = apply { other.follow(this) }
public operator fun Victor.plus(other: Victor) = apply { other.follow(this) }

public fun Talon.stop() = apply { this.stopMotor() }
public fun Victor.stop() = apply { this.stopMotor() }
