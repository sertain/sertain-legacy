@file:Suppress("unused", "RedundantVisibilityModifier")
package org.sertain.command

import org.sertain.RobotLifecycle

private typealias WpiLibSubsystem = edu.wpi.first.wpilibj.command.Subsystem

/**
 * @see edu.wpi.first.wpilibj.command.Subsystem
 */
public abstract class Subsystem : WpiLibSubsystem(), RobotLifecycle {
    init {
        RobotLifecycle.addListener(this)
    }
}
