package org.sertain

import org.fest.assertions.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class RobotTest {
    private val testLifecycle = TestLifecycleClass()
    private var listeners = mutableListOf<RobotLifecycle>()

    @Before
    fun beforeEach() {
        RobotLifecycle.addListener(testLifecycle)

        @Suppress("UNCHECKED_CAST")
        listeners = RobotLifecycle.Companion::class.java.getDeclaredField("listeners").let {
            it.isAccessible = true
            it.get(null)
        } as MutableList<RobotLifecycle>
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

    class TestLifecycleClass : RobotLifecycle
}
