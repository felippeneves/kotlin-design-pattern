package behavioural_patterns.visitor

import kotlin.test.Test
import kotlin.test.assertEquals

interface ReportElement {
    fun <T> accept(visitor: ReportVisitor<T>): T
}

class FixedPriceContract(val costPerYear: Long) : ReportElement {
    override fun <T> accept(visitor: ReportVisitor<T>): T = visitor.visit(contract = this)
}

class TimeAndMaterialContract(val costPerHour: Long, val hours: Long) : ReportElement {
    override fun <T> accept(visitor: ReportVisitor<T>): T = visitor.visit(contract = this)
}

class SupportContract(val costPerMonth: Long) : ReportElement {
    override fun <T> accept(visitor: ReportVisitor<T>): T = visitor.visit(contract = this)
}

interface ReportVisitor<T> {
    fun visit(contract: FixedPriceContract): T
    fun visit(contract: TimeAndMaterialContract): T
    fun visit(contract: SupportContract): T
}

class MonthlyCostReportVisitor : ReportVisitor<Long> {
    override fun visit(contract: FixedPriceContract): Long = contract.costPerYear / 12

    override fun visit(contract: TimeAndMaterialContract): Long = contract.costPerHour * contract.hours

    override fun visit(contract: SupportContract): Long = contract.costPerMonth
}

class YearlyReportVisitor : ReportVisitor<Long> {
    override fun visit(contract: FixedPriceContract): Long = contract.costPerYear

    override fun visit(contract: TimeAndMaterialContract): Long = contract.costPerHour * contract.hours

    override fun visit(contract: SupportContract): Long = contract.costPerMonth * 12
}

class VisitorTest {
    @Test
    fun testVisitor() {
        val contracts = listOf(
            FixedPriceContract(costPerYear = 10000),
            TimeAndMaterialContract(costPerHour = 150, hours = 10),
            SupportContract(costPerMonth = 500),
            TimeAndMaterialContract(costPerHour = 50, hours = 50),
        )

        val monthlyCostVisitor = MonthlyCostReportVisitor()
        val monthlyCost = contracts.sumOf { it.accept(visitor = monthlyCostVisitor) }
        println("Monthly cost: $monthlyCost")
        assertEquals(expected = 5333, actual = monthlyCost)


        println("-------------------")

        val yearlyCostVisitor = YearlyReportVisitor()
        val yearlyCost = contracts.sumOf { it.accept(visitor = yearlyCostVisitor) }
        println("Yearly cost: $yearlyCost")
        assertEquals(expected = 20000, actual = yearlyCost)
    }
}