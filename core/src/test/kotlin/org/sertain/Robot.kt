package org.sertain

import org.fest.assertions.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.sertain.RobotLifecycle.Companion.listeners

class RobotTest {
    private val testLifecycle = object : RobotLifecycle {}

    @Before
    fun beforeEach() {
        RobotLifecycle.addListener(testLifecycle)
    }

    @After
    fun afterEach() {
        RobotLifecycle.removeListener(testLifecycle)
    }

    @Test
    fun `should contain lifecycle when added`() {
        assertThat(listeners).contains(testLifecycle)
    }

    @Test
    fun `should not contain lifecycle when removed`() {
        RobotLifecycle.removeListener(testLifecycle)

        assertThat(listeners).isEmpty()
    }
}
