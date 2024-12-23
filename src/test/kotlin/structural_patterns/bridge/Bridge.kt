package structural_patterns.bridge

import kotlin.test.Test
import kotlin.test.assertEquals

interface Device {
    var volume: Int
    fun getName(): String
}

class Radio : Device {
    override var volume: Int = 0
    override fun getName() = "Radio $this"
}

class TV : Device {
    override var volume: Int = 0
    override fun getName() = "TV $this"
}

interface Remote {
    fun volumeUp()
    fun volumeDown()
}

class RemoteControl(private val device: Device) : Remote {
    override fun volumeUp() {
        device.volume++
        println("${device.getName()} volume up: ${device.volume}")
    }

    override fun volumeDown() {
        device.volume--
        println("${device.getName()} volume down: ${device.volume}")
    }
}

class BridgeTest {
    @Test
    fun testBridge() {
        val radio = Radio()
        val tv = TV()

        val remoteRadio = RemoteControl(device = radio)
        val remoteTV = RemoteControl(device = tv)

        remoteRadio.volumeUp()
        remoteRadio.volumeUp()
        remoteRadio.volumeDown()

        remoteTV.volumeUp()
        remoteTV.volumeUp()
        remoteTV.volumeUp()
        remoteTV.volumeDown()

        assertEquals(expected = 1, actual = radio.volume)
        assertEquals(expected = 2, actual = tv.volume)
    }
}
