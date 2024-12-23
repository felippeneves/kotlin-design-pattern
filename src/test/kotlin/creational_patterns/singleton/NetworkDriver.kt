package creational_patterns.singleton

import kotlin.test.Test
import kotlin.test.assertEquals

object NetworkDriver {
    init {
        println("Initializing: $this")
    }

    fun log(): NetworkDriver = apply {
        println("Network driver: $this")
    }
}

class SingletonTest {
    @Test
    fun testSingleton() {
        println("Start")
        val networkDriver1 = NetworkDriver.log()
        val networkDriver2 = NetworkDriver.log()

        assertEquals(expected = NetworkDriver, actual = networkDriver1)
        assertEquals(expected = NetworkDriver, actual = networkDriver2)
    }
}
