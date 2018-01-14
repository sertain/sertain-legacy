package org.sertain.path

import jaci.pathfinder.Pathfinder
import jaci.pathfinder.Trajectory
import jaci.pathfinder.Waypoint

typealias PathFitMethod = Trajectory.FitMethod

fun ConfigureTrajectory(deltaTime: Double = 0.05,
                        maximumVelocity: Double = 1.7,
                        maximumAcceleration: Double = 2.0,
                        maximumJerk: Double = 60.0,
                        fitMethod: PathFitMethod = PathFitMethod.HERMITE_CUBIC): Trajectory.Config {
    return Trajectory.Config(fitMethod,Trajectory.Config.SAMPLES_HIGH,deltaTime,maximumVelocity,maximumAcceleration,maximumJerk)
}

fun Trajectory.Config.generate(points: Array<Waypoint>): Trajectory {
    return Pathfinder.generate(points,this)
}
