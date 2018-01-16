@file:Suppress("unused", "RedundantVisibilityModifier")
package org.sertain.util

import jaci.pathfinder.Pathfinder
import jaci.pathfinder.Trajectory
import jaci.pathfinder.Waypoint
import jaci.pathfinder.followers.EncoderFollower
import jaci.pathfinder.modifiers.TankModifier

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

public fun Trajectory.Config.generate(points: Array<out Waypoint>): Trajectory =
        Pathfinder.generate(points, this)

@Suppress("FunctionName")
public fun TankModifier(source: Trajectory, wheelbaseWidth: Double): TankModifier =
        TankModifier(source).modify(wheelbaseWidth)

public fun TankModifier.split(): Pair<EncoderFollower, EncoderFollower> =
        EncoderFollower(leftTrajectory) to EncoderFollower(rightTrajectory)

public fun Array<Trajectory.Segment>.reduce(n: Int): List<Trajectory.Segment> {
    var result = toList()
    while (result.size > n) result = result.filterIndexed { i, _ -> i % 2 == 0 }
    return result
}

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
