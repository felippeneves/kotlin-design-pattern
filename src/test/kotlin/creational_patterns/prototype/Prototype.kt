package creational_patterns.prototype

import kotlin.test.Test
import kotlin.test.assertEquals

abstract class Shape : Cloneable {
    var id: String? = null
    var type: String? = null

    abstract fun draw()

    public override fun clone(): Any {
        var clone: Any? = null
        try {
            clone = super.clone()
        } catch (e: CloneNotSupportedException) {
            e.printStackTrace()
        }
        return clone!!
    }

    companion object {
        const val RECTANGLE = "Rectangle"
        const val SQUARE = "Square"
        const val CIRCLE = "Circle"
    }
}

class Rectangle : Shape() {
    init {
        type = RECTANGLE
    }

    override fun draw() {
        println("Inside Rectangle::draw() method.")
    }
}

class Square : Shape() {
    init {
        type = SQUARE
    }

    override fun draw() {
        println("Inside Square::draw() method.")
    }
}

class Circle : Shape() {
    init {
        type = CIRCLE
    }

    override fun draw() {
        println("Inside Circle::draw() method.")
    }
}

object ShapeCache {
    private val shapeMap = mutableMapOf<String?, Shape>()

    fun loadCache() {
        val circle = Circle()
        val square = Square()
        val rectangle = Rectangle()

        shapeMap["1"] = circle
        shapeMap["2"] = square
        shapeMap["3"] = rectangle
    }

    fun getShape(shapeId: String): Shape {
        val cachedShape = shapeMap[shapeId]
        return cachedShape?.clone() as Shape
    }
}

class PrototypeTest {
    @Test
    fun testPrototype() {
        ShapeCache.loadCache()

        val clonedShape1 = ShapeCache.getShape("1")
        val clonedShape2 = ShapeCache.getShape("2")
        val clonedShape3 = ShapeCache.getShape("3")

        println("Shape: ${clonedShape1.type}")
        clonedShape1.draw()
        println("Shape: ${clonedShape2.type}")
        clonedShape2.draw()
        println("Shape: ${clonedShape3.type}")
        clonedShape3.draw()

        assertEquals(expected = Shape.CIRCLE, actual = clonedShape1.type)
        assertEquals(expected = Shape.SQUARE, actual = clonedShape2.type)
        assertEquals(expected = Shape.RECTANGLE, actual = clonedShape3.type)
    }
}
