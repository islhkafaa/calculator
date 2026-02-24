package app.calculator.domain

sealed class CalculatorAction {
    data class EnterNumber(val number: Int) : CalculatorAction()
    data object EnterDecimal : CalculatorAction()
    data class EnterOperation(val operation: CalculatorOperation) : CalculatorAction()
    data object Calculate : CalculatorAction()
    data object Clear : CalculatorAction()
    data object ClearEntry : CalculatorAction()
    data object Delete : CalculatorAction()
    data object ToggleSign : CalculatorAction()
    data object Percent : CalculatorAction()
    data object SquareRoot : CalculatorAction()
}
