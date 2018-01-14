package org.sertain.path

import jaci.pathfinder.Pathfinder
import jaci.pathfinder.Trajectory
import jaci.pathfinder.Waypoint

typealias PathFitMethod = Trajectory.FitMethod

fun ConfigureTrajectory(maximumVelocity: Double,
                        maximumAcceleration: Double,
                        maximumJerk: Double,
                        deltaTime: Double = 0.05,
                        fitMethod: PathFitMethod = PathFitMethod.HERMITE_CUBIC,
                        sampleCount: Int = Trajectory.Config.SAMPLES_HIGH): Trajectory.Config {
    return Trajectory.Config(fitMethod, sampleCount, deltaTime, maximumVelocity, maximumAcceleration, maximumJerk)
}

fun Trajectory.Config.generate(points: Array<Waypoint>): Trajectory = Pathfinder.generate(points, this)
