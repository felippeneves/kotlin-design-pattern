package behavioural_patterns.strategy

import kotlin.test.Test

class Printer(private val stringFormatterStrategy: (String) -> String) {
    fun printString(message: String) {
        println(stringFormatterStrategy(message))
    }
}

val lowercaseFormatter = { it: String -> it.lowercase() }
val uppercaseFormatter = { it: String -> it.uppercase() }

class StrategyTest {
    @Test
    fun testStrategy() {
        val inputString = "LOREM ipsum DOLOR sit amet"

        val lowercasePrinter = Printer(lowercaseFormatter)
        lowercasePrinter.printString(message = inputString)

        println("-------------------")

        val uppercasePrinter = Printer(uppercaseFormatter)
        uppercasePrinter.printString(message = inputString)
    }
}
