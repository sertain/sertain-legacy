@file:Suppress("unused", "RedundantVisibilityModifier")
package org.sertain.util

import edu.wpi.first.wpilibj.DriverStation

/**
 * Gets the current alliance station the FMS reports that the team is located at.
 */
public val station: AllianceStation
    get() = AllianceStation(when (DriverStation.getInstance().alliance) {
        DriverStation.Alliance.Blue -> Alliance.BLUE
        DriverStation.Alliance.Red -> Alliance.RED
        else -> Alliance.INVALID
    }, DriverStation.getInstance().location)

/**
 * The team's alliance color.
 */
public enum class Alliance {
    RED,
    BLUE,
    INVALID
}

/**
 * The team's exact alliance station position, which consists of the alliance color and station
 * number.
 *
 * @property alliance the [Alliance] of this station.
 * @property station the station number for this color, between 1 and 3.
 */
public data class AllianceStation(public val alliance: Alliance, public val station: Int)
