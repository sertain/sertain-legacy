package org.sertain.util

import edu.wpi.first.wpilibj.DriverStation

val station: AllianceStation
    get() = AllianceStation(when (DriverStation.getInstance().alliance) {
        DriverStation.Alliance.Blue -> Alliance.BLUE
        DriverStation.Alliance.Red -> Alliance.RED
        else -> Alliance.INVALID
    }, DriverStation.getInstance().location)

enum class Alliance {
    RED,
    BLUE,
    INVALID
}

data class AllianceStation(val alliance: Alliance, val station: Int)
