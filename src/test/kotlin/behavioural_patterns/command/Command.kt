package behavioural_patterns.command

import kotlin.test.Test

interface Command {
    fun execute()
}

class OrderAddCommand(private val id: Long) : Command {
    override fun execute() {
        println("Adding order with id $id")
    }
}

class OrderPayCommand(private val id: Long) : Command {
    override fun execute() {
        println("Paying for order with id $id")
    }
}

class CommandProcessor {
    private val queue = mutableListOf<Command>()

    fun addToQueue(command: Command): CommandProcessor = apply { queue.add(command) }

    fun processCommands(): CommandProcessor = apply {
        queue.forEach { it.execute() }
        queue.clear()
    }
}

class CommandTest {
    @Test
    fun testCommand() {
        val commandProcessor = CommandProcessor()
        commandProcessor
            .addToQueue(command = OrderAddCommand(id = 1L))
            .addToQueue(command = OrderAddCommand(id = 2L))
            .addToQueue(command = OrderPayCommand(id = 2L))
            .addToQueue(command = OrderPayCommand(id = 1L))
            .processCommands()
    }
}
