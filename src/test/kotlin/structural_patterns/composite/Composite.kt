package structural_patterns.composite

import kotlin.test.Test
import kotlin.test.assertEquals

open class Equipment(open val price: Int, val name: String)

open class Composite(name: String) : Equipment(price = 0, name) {
    private val equipments = mutableListOf<Equipment>()

    override val price: Int
        get() = equipments.sumOf { it.price }

    fun add(equipment: Equipment) = apply { equipments.add(equipment) }
}

class Computer : Composite(name = "PC")
class Processor : Equipment(price = 1000, name = "Processor")
class HardDrive : Equipment(price = 250, name = "Hard Drive")
class Memory : Composite(name = "Memory")
class ROM : Equipment(price = 100, name = "Read Only Memory")
class RAM : Equipment(price = 75, name = "Random Access Memory")

class CompositeTest {
    @Test
    fun testComposite() {
        val memory = Memory()
            .add(equipment = ROM())
            .add(equipment = RAM())

        val pc = Computer()
            .add(memory)
            .add(Processor())
            .add(HardDrive())

        println("PC price: ${pc.price}")

        assertEquals(expected = "PC", actual = pc.name)
        assertEquals(expected = 1425, actual = pc.price)
    }
}
