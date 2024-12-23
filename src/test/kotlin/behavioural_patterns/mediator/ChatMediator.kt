package behavioural_patterns.mediator

import kotlin.test.Test

class ChatUser(private val mediator: ChatMediator, private val name: String) {
    fun send(message: String) {
        println("$name: Sending message: $message")
        mediator.sendMessage(message, user = this)
    }

    fun receive(message: String) {
        println("$name: Received message: $message")
    }
}

class ChatMediator {
    private val users = mutableListOf<ChatUser>()

    fun addUser(user: ChatUser): ChatMediator = apply { users.add(user) }

    fun sendMessage(message: String, user: ChatUser) {
        users
            .filter { it != user }
            .forEach { it.receive(message) }
    }
}

class ChatMediatorTest {
    @Test
    fun testMediator() {
        val chatMediator = ChatMediator()
        val felippe = ChatUser(chatMediator, name = "Felippe")
        val john = ChatUser(chatMediator, name = "John")
        val alice = ChatUser(chatMediator, name = "Alice")

        chatMediator
            .addUser(felippe)
            .addUser(john)
            .addUser(alice)

        john.send(message = "Hi everyone!")
    }
}
