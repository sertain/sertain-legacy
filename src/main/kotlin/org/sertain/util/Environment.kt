@file:Suppress("unused", "RedundantVisibilityModifier")
package org.sertain.util

import edu.wpi.first.wpilibj.DriverStation

public val station: AllianceStation
    get() = AllianceStation(when (DriverStation.getInstance().alliance) {
        DriverStation.Alliance.Blue -> Alliance.BLUE
        DriverStation.Alliance.Red -> Alliance.RED
        else -> Alliance.INVALID
    }, DriverStation.getInstance().location)

public enum class Alliance {
    RED,
    BLUE,
    INVALID
}

public data class AllianceStation(public val alliance: Alliance, public val station: Int)
