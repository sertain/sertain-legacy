package org.sertain.path

import jaci.pathfinder.Pathfinder
import jaci.pathfinder.Trajectory
import jaci.pathfinder.Waypoint
import jaci.pathfinder.followers.EncoderFollower
import jaci.pathfinder.modifiers.TankModifier

object PathController {
    val pathCoordinates = ArrayList<Triple<Double, Double, Double>>()

    var dt = 0.05
    var max_vel = 1.7
    var max_accel = 2.0
    var max_jerk = 60.0


    fun addPath(xcoord: Double, ycoord: Double, radians: Double) {
        pathCoordinates += Triple(xcoord, ycoord, radians)
    }

    fun configTrajectory(dt: Double, maximumVelocity: Double, maximumAcceleration: Double, maximumJerk: Double) {
        this.dt = dt
        this.max_vel = maximumVelocity
        this.max_accel = maximumAcceleration
        this.max_jerk = maximumJerk
    }

    fun generateEncoderFollowers() {
        val points = ArrayList<Waypoint>()
        for (coordset: Triple<Double, Double, Double> in pathCoordinates) {
            points.add(Waypoint(coordset.first, coordset.second, coordset.third))
        }
        // Create Trajectory
        val config = Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, this.dt, this.max_vel, this.max_accel, this.max_jerk)
        val pointArray: Array<Waypoint> = points.toTypedArray()
        val trajectory = Pathfinder.generate(pointArray, config)

        val tank_drive_modifier: TankModifier = TankModifier(trajectory).modify(0.5)

        // Setup EncoderFollowers

        var left = tank_drive_modifier.leftTrajectory
        var right = tank_drive_modifier.rightTrajectory
    }
}
