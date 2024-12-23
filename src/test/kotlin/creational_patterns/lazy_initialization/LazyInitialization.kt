package creational_patterns.lazy_initialization

import kotlin.test.Test
import kotlin.test.assertNotNull

class AlertBox {
    var message: String? = null

    fun show() {
        println("AlertBox $this:$message")
    }
}

class Window {
    val box by lazy { AlertBox() }

    fun showMessage(message: String) {
        box.message = message
        box.show()
    }
}

class Window2 {
    lateinit var box: AlertBox

    fun showMessage(message: String) {
        box = AlertBox()
        box.message = message
        box.show()
    }
}

class WindowTest {

    @Test
    fun windowTest() {
        val window = Window()
        window.showMessage("Hello window")
        assertNotNull(window.box)

        val window2 = Window2()
//        println(window2.box)
        window2.showMessage("Second window")
        assertNotNull(window2.box)
    }
}
