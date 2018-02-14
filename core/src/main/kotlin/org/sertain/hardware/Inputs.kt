@file:Suppress("unused", "RedundantVisibilityModifier")
@file:JvmName("Inputs")
package org.sertain.hardware

import edu.wpi.first.wpilibj.DigitalInput as WpiLibDigitalInput

class DigitalInput @JvmOverloads constructor(
        channel: Int,
        private val inverted: Boolean = false
) : WpiLibDigitalInput(channel) {
    override fun get() = if (inverted) !super.get() else super.get()
}
