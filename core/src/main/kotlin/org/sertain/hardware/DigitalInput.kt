@file:Suppress("unused", "RedundantVisibilityModifier")
@file:JvmName("DigitalInput")
package org.sertain.hardware

import edu.wpi.first.wpilibj.DigitalInput as WpiLibDigitalInput

class DigitalInput constructor(channel: Int) : WpiLibDigitalInput(channel) {
    private var inverted = false

    override fun get() = if (inverted) !super.get() else super.get()

    fun invert(inverted: Boolean = true) = apply { this.inverted = inverted }
}
