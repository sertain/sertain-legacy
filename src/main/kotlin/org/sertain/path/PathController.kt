package org.sertain.path

/**
 * Created by jacksoncoder on 1/10/18.
 */

object PathController {
    val pathCoordinates = ArrayList<Triple<Float,Float,Float>>()

    fun addPath(xcoord: Float,ycoord: Float,radians: Float) {
        pathCoordinates += Triple(xcoord,ycoord,radians)
    }

    fun executePath() {
        // Add stuff after we make command handler
    }
}