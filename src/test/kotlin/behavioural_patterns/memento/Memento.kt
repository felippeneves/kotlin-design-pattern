package behavioural_patterns.memento

import kotlin.test.Test
import kotlin.test.assertEquals

data class Memento(val state: String)

class Originator(var state: String) {
    fun createMemento(): Memento = Memento(state)
    fun restoreMemento(memento: Memento) {
        state = memento.state
        println("State restored to: $state")
    }
}

class CareTaker {
    private val mementoList = mutableListOf<Memento>()

    fun saveState(state: Memento) {
        mementoList.add(state)
    }
    fun restore(index: Int): Memento = mementoList[index]
}

class MementoTest() {
    @Test
    fun testMemento() {
        val originator = Originator(state = "Initial state")
        val careTaker = CareTaker()
        careTaker.saveState(state = originator.createMemento())

        originator.state = "State 1"
        careTaker.saveState(state = originator.createMemento())

        originator.state = "State 2"
        careTaker.saveState(state = originator.createMemento())

        println("Current state: ${originator.state}")
        assertEquals(expected = "State 2", actual = originator.state)

        originator.restoreMemento(memento = careTaker.restore(index = 1))
        println("Current state: ${originator.state}")
        assertEquals(expected = "State 1", actual = originator.state)

        originator.restoreMemento(memento = careTaker.restore(index = 0))
        println("Current state: ${originator.state}")
        assertEquals(expected = "Initial state", actual = originator.state)

        originator.restoreMemento(memento = careTaker.restore(index = 2))
        println("Current state: ${originator.state}")
        assertEquals(expected = "State 2", actual = originator.state)
    }
}
