package structural_patterns.proxy

import kotlin.test.Test

interface Image {
    fun display()
}

class RealImage(private val fileName: String) : Image {
    init {
        loadFromDisk()
    }

    override fun display() {
        println("RealImage: Displaying $fileName")
    }

    private fun loadFromDisk() {
        println("RealImage: Loading $fileName")
    }
}

class ProxyImage(private val fileName: String) : Image {

    private var realImage: RealImage? = null

    override fun display() {
        println("ProxyImage: Displaying $fileName")

        if (realImage == null) {
            realImage = RealImage(fileName)
        }

        realImage!!.display()
    }
}

class ProxyTest {
    @Test
    fun testProxy() {
        val image = ProxyImage(fileName = "test.jpg")

        // load image from disk
        image.display()

        println("-------------------")

        // load image from "cache"
        image.display()
    }
}
