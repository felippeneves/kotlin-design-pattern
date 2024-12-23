package creational_patterns.builder

import kotlin.test.Test

class Component private constructor(builder: Builder) {
    var param1: String? = null
    var param2: Int? = null
    var param3: Boolean = false

    internal class Builder {
        var param1: String? = null
        var param2: Int? = null
        var param3: Boolean = false

        //Apply returns the same instance of the builder
        fun setParam1(param1: String) = apply { this.param1 = param1 }
        fun setParam2(param2: Int) = apply { this.param2 = param2 }
        fun setParam3(param3: Boolean) = apply { this.param3 = param3 }

        fun build() = Component(builder = this)
    }

    init {
        param1 = builder.param1
        param2 = builder.param2
        param3 = builder.param3
    }
}

class ComponentTest {
    @Test
    fun builderTest() {
        val component = Component.Builder()
            .setParam1("param1")
            .setParam3(true)
            .build()

        println("Param1: ${component.param1}")
        println("Param2: ${component.param2}")
        println("Param3: ${component.param3}")

        assert(component.param1 == "param1")
        assert(component.param2 == null)
        assert(component.param3)
    }
}
