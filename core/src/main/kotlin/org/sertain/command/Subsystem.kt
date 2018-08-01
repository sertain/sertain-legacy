@file:Suppress("unused", "RedundantVisibilityModifier")
@file:JvmName("SubsystemUtils")
package org.sertain.command

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder
import org.sertain.RobotLifecycle
import edu.wpi.first.wpilibj.command.Subsystem as WpiLibSubsystem

/** @see edu.wpi.first.wpilibj.command.Subsystem */
public abstract class Subsystem : WpiLibSubsystem(), RobotLifecycle {
    /** @see edu.wpi.first.wpilibj.command.Subsystem.setDefaultCommand */
    open val defaultCommand: CommandBase? = null

    override fun initDefaultCommand() = setDefaultCommand(defaultCommand?.mirror)
}
