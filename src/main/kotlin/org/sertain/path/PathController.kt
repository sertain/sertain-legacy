package org.sertain.path

import jaci.pathfinder.Pathfinder
import jaci.pathfinder.Trajectory
import jaci.pathfinder.Waypoint
import jaci.pathfinder.followers.EncoderFollower
import jaci.pathfinder.modifiers.TankModifier

typealias PathFitMethod = Trajectory.FitMethod

class Path {
    var pathWaypoints = ArrayList<Waypoint>()
    var fitMethod = Trajectory.FitMethod.HERMITE_CUBIC
    var dt: Double = 0.05
    var maxVel = 1.7
    var maxAccel = 2.0
    var maxJerk = 60.0
    var wheelDistance = 0.5

    fun setPath(path: Array<Waypoint>) {
        for(waypoint in path) {
            pathWaypoints.add(waypoint)
        }
    }

    fun configureTrajectory(deltaTime: Double?, maximumVelocity: Double?, maximumAcceleration: Double?, maximumJerk: Double?, fitMethod: PathFitMethod?, wheelDistance: Double?) {
        if(deltaTime!=null) dt = deltaTime
        if(maximumVelocity!=null) maxVel = maximumVelocity
        if(maximumAcceleration!=null) maxAccel = maximumAcceleration
        if(maximumJerk!=null) maxJerk = maximumJerk
        if(fitMethod!=null) this.fitMethod = fitMethod
        if(wheelDistance!=null) this.wheelDistance = wheelDistance
    }
}