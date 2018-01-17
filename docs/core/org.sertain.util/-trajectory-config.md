---
title: TrajectoryConfig - core
---

[core](../index.md) / [org.sertain.util](index.md) / [TrajectoryConfig](.)

# TrajectoryConfig

`@JvmOverloads fun TrajectoryConfig(maxVelocity: Double, maxAccel: Double, maxJerk: Double, fit: <ERROR CLASS> = Trajectory.FitMethod.HERMITE_CUBIC, samples: Int = Trajectory.Config.SAMPLES_HIGH, ticks: Double = 0.05): <ERROR CLASS>`

Creates a configuration for the trajectory.

### Parameters

`maxVelocity` - the maximum allowable velocity for the robot

`maxAccel` - the maximum allowable acceleration for the robot

`maxJerk` - the maximum allowable jerk for the robot

`fit` - the method to fit the path to

`samples` - the number of samples to calculate

`ticks` - the speed at which the robot will take in the generated points