package behavioural_patterns.observer

import java.io.File
import kotlin.test.Test

interface EventListener {
    fun update(eventType: String?, file: File?)
}

class EventManager(vararg operations: String) {
    private var listeners = hashMapOf<String, MutableList<EventListener>>()

    init {
        for (operation in operations) {
            listeners[operation] = mutableListOf()
        }
    }

    fun subscribe(eventType: String?, listener: EventListener) {
        val users = listeners[eventType]
        users?.add(listener)
    }

    fun unsubscribe(eventType: String?, listener: EventListener) {
        val users = listeners[eventType]
        users?.remove(listener)
    }

    fun notify(eventType: String?, file: File?) {
        val users = listeners[eventType]
        users?.forEach { it.update(eventType, file) }
    }
}

class Editor {
    var events = EventManager(OPEN_OPERATION, SAVE_OPERATION)
    private var file: File? = null

    fun openFile(filePath: String) {
        file = File(filePath)
        events.notify(OPEN_OPERATION, file)
    }

    fun saveFile() {
        file?.let {
            events.notify(SAVE_OPERATION, it)
        }
    }

    companion object {
        const val OPEN_OPERATION = "open"
        const val SAVE_OPERATION = "save"
    }
}

class EmailNotificationListener(private val email: String) : EventListener {
    override fun update(eventType: String?, file: File?) {
        println("Email to $email: Someone has performed $eventType operation with the file ${file?.name}")
    }
}

class LogOpenListener(private val filename: String) : EventListener {
    override fun update(eventType: String?, file: File?) {
        println("Save to log $filename: Someone has performed $eventType operation with the file ${file?.name}")
    }
}

class ObserverTest {
    @Test
    fun testObserver() {
        val editor = Editor()

        val logListener = LogOpenListener(filename = "path/to/log/file.txt")
        val emailListener = EmailNotificationListener(email = "test@test.com")

        editor.events.subscribe(Editor.OPEN_OPERATION, logListener)
        editor.events.subscribe(Editor.OPEN_OPERATION, emailListener)
        editor.events.subscribe(Editor.SAVE_OPERATION, emailListener)

        editor.openFile(filePath = "test.txt")
        editor.saveFile()

        println("-----------------------")

        editor.events.unsubscribe(Editor.OPEN_OPERATION, emailListener)
        editor.openFile(filePath = "test.txt")
        editor.saveFile()
    }
}
