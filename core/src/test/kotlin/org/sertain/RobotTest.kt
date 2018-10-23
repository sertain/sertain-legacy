package org.sertain

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.sertain.RobotLifecycle.Companion.listeners
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RobotTest {
    private val testLifecycle = object : RobotLifecycle {}

    @Before
    fun beforeEach() {
        +testLifecycle
    }

    @After
    fun afterEach() {
        -testLifecycle
    }

    @Test
    fun `should contain lifecycle when added`() {
        assertTrue(listeners.contains(testLifecycle))
    }

    @Test
    fun `should not contain lifecycle when removed`() {
        RobotLifecycle.removeListener(testLifecycle)

        assertFalse(listeners.contains(testLifecycle))
    }
}
