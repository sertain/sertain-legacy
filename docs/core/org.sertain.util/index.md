---
title: org.sertain.util - core
---

[core](../index.md) / [org.sertain.util](.)

## Package org.sertain.util

### Types

| [Alliance](-alliance/index.md) | `enum class Alliance` |
| [AllianceStation](-alliance-station/index.md) | `data class AllianceStation` |
| [PathInitializer](-path-initializer/index.md) | `abstract class PathInitializer`<br>An abstract class for initializing a path. |

### Extensions for External Classes

| [kotlin.Array](kotlin.-array/index.md) |  |

### Properties

| [station](station.md) | `val station: `[`AllianceStation`](-alliance-station/index.md)<br>Gets the current alliance station the FMS reports that the team is located at. |

### Functions

| [TankModifier](-tank-modifier.md) | `fun TankModifier(source: <ERROR CLASS>, wheelbaseWidth: Double): <ERROR CLASS>`<br>Creates a tank modifier from the given [source](-tank-modifier.md#org.sertain.util$TankModifier(, kotlin.Double)/source) using [wheelbaseWidth](-tank-modifier.md#org.sertain.util$TankModifier(, kotlin.Double)/wheelbaseWidth). |
| [TrajectoryConfig](-trajectory-config.md) | `fun TrajectoryConfig(maxVelocity: Double, maxAccel: Double, maxJerk: Double, fit: <ERROR CLASS> = Trajectory.FitMethod.HERMITE_CUBIC, samples: Int = Trajectory.Config.SAMPLES_HIGH, ticks: Double = 0.05): <ERROR CLASS>`<br>Creates a configuration for the trajectory. |
| [generate](generate.md) | `fun <ERROR CLASS>.generate(points: Array<out <ERROR CLASS>>): <ERROR CLASS>`<br>Generates a trajectory from the configuration. |
| [split](split.md) | `fun <ERROR CLASS>.split(): Pair<<ERROR CLASS>, <ERROR CLASS>>`<br>Creates a pair of EncoderFollowers. |

