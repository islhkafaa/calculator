package app.calculator.domain

data class CalculatorState(
    val primaryNumber: String = "",
    val secondaryNumber: String = "",
    val operation: CalculatorOperation? = null
)
