package structural_patterns.adapter

import kotlin.test.Test
import kotlin.test.assertEquals

//3rd party functionality

data class DisplayDataType(val index: Float, val data: String)

class DataDisplay {
    fun displayData(data: DisplayDataType) {
        println("Data is displayed: ${data.index} - ${data.data}")
    }
}

//Our code

data class DatabaseData(val position: Int, val amount: Int)

class DatabaseDataGenerator {
    fun generateData(): List<DatabaseData> =
        arrayListOf(
            DatabaseData(1, 100),
            DatabaseData(2, 200),
            DatabaseData(3, 300)
        )
}

interface DatabaseDataConverter {
    fun convertData(data: List<DatabaseData>): List<DisplayDataType>
}

class DataDisplayAdapter(private val display: DataDisplay) : DatabaseDataConverter {
    override fun convertData(data: List<DatabaseData>): List<DisplayDataType> {
        val convertedData = arrayListOf<DisplayDataType>()
        data.forEach {
            val ddt = DisplayDataType(it.position.toFloat(), it.amount.toString())
            display.displayData(ddt)
            convertedData.add(ddt)
        }
        return convertedData
    }
}

class AdapterTest {
    @Test
    fun adapterTest() {
        val dataGenerator = DatabaseDataGenerator()
        val data = dataGenerator.generateData()

        val adapter = DataDisplayAdapter(display = DataDisplay())
        val convertedData = adapter.convertData(data)

        assertEquals(expected = 3, actual = convertedData.size)
        assertEquals(expected = 2f, actual = convertedData[1].index)
        assertEquals(expected = "200", actual = convertedData[1].data)
    }
}
