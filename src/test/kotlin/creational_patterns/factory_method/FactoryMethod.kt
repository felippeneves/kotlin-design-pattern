package creational_patterns.factory_method

import kotlin.test.Test
import kotlin.test.assertEquals

sealed class Country {
    data object Canada : Country()
}

data object Spain : Country()
class Greece(val someProperty: String) : Country()
data class USA(val someProperty: String) : Country()

class Currency(val code: String) {
    companion object {
        const val EUR = "EUR"
        const val USD = "USD"
        const val CAD = "USD"
    }
}

object CurrencyFactory {
    fun currencyForCountry(country: Country): Currency =
        when (country) {
            is Spain -> Currency(code = Currency.EUR)
            is Greece -> Currency(code = Currency.EUR)
            is USA -> Currency(code = Currency.USD)
            Country.Canada -> Currency(code = Currency.CAD)
        }
}

class FactoryMethodTest {
    @Test
    fun currencyTest() {
        val greekCurrency = CurrencyFactory.currencyForCountry(Greece("")).code
        println("Greek currency: $greekCurrency")

        val usaCurrency = CurrencyFactory.currencyForCountry(USA("")).code
        println("USA currency: $usaCurrency")

        assertEquals(expected = Currency.EUR, actual = greekCurrency)
        assertEquals(expected = Currency.USD, actual = usaCurrency)
    }
}
