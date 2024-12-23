import kotlin.test.Test
import kotlin.test.assertEquals

class Calculator {
    fun sum (a: Int, b: Int) = a + b
}

class CalculatorTest {
    @Test
    fun testSum() {
        val calc = Calculator()
        assertEquals(expected = 8, actual = calc.sum(3, 5))
    }
}