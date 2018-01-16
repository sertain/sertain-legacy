@file:Suppress("unused", "RedundantVisibilityModifier")
package org.sertain.util

import jaci.pathfinder.Pathfinder
import jaci.pathfinder.Trajectory
import jaci.pathfinder.Waypoint
import jaci.pathfinder.followers.EncoderFollower
import jaci.pathfinder.modifiers.TankModifier

/**
 * Creates a configuration for the trajectory.
 *
 * @param maxVelocity the maximum allowable velocity for the robot
 * @param maxAccel the maximum allowable acceleration for the robot
 * @param maxJerk the maximum allowable jerk for the robot
 * @param fit the method to fit the path to
 * @param samples the number of samples to calculate
 * @param ticks the speed at which the robot will take in the generated points
 */
@Suppress("FunctionName")
@JvmOverloads
public fun TrajectoryConfig(
    maxVelocity: Double,
    maxAccel: Double,
    maxJerk: Double,
    fit: Trajectory.FitMethod = Trajectory.FitMethod.HERMITE_CUBIC,
    samples: Int = Trajectory.Config.SAMPLES_HIGH,
    ticks: Double = 0.05 // 20 millis
): Trajectory.Config = Trajectory.Config(
    fit,
    samples,
    ticks,
    maxVelocity,
    maxAccel,
    maxJerk
)

/**
 * Generates a trajectory from the configuration.
 *
 * @param points an array of [Waypoint]s that the robot should move between
 * @return the freshly generated [Trajectory]
 */
public fun Trajectory.Config.generate(points: Array<out Waypoint>): Trajectory =
        Pathfinder.generate(points, this)

/**
 * Creates a tank modifier from the given [source] using [wheelbaseWidth].
 *
 * @param source the source [Trajectory]
 * @param wheelbaseWidth the width between left and right wheels
 */
@Suppress("FunctionName")
public fun TankModifier(source: Trajectory, wheelbaseWidth: Double): TankModifier =
        TankModifier(source).modify(wheelbaseWidth)

/**
 * Creates a pair of [EncoderFollower]s.
 *
 * @return a paid of [EncoderFollower]s
 */
public fun TankModifier.split(): Pair<EncoderFollower, EncoderFollower> =
        EncoderFollower(leftTrajectory) to EncoderFollower(rightTrajectory)

/**
 * Reduces an array of [Trajectory.Segment]s to a more manageable size of [n].
 *
 * @param n the length to reduce the array to
 * @return a list of [Trajectory.Segment]s
 */
public fun Array<Trajectory.Segment>.reduce(n: Int): List<Trajectory.Segment> {
    var result = toList()
    while (result.size > n) result = result.filterIndexed { i, _ -> i % 2 == 0 }
    return result
}

/**
 * An abstract class for initializing a path.
 */
public abstract class PathInitializer {
    protected abstract val trajectory: Trajectory
    protected abstract val followers: Pair<EncoderFollower, EncoderFollower>

    public val left get() = followers.first
    public val right get() = followers.second
    public val isFinished get() = left.isFinished
    public val heading get() = left.heading

    protected fun logGeneratedPoints() {
        println(
            """
            |Generated ${trajectory.segments.size} points:
            ${trajectory.segments.reduce(50).joinToString("\n") { "${it.x}, ${it.y}" }}
            |""".trimMargin()
        )
    }
}
