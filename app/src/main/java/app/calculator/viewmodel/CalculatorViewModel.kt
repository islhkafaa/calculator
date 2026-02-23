package app.calculator.viewmodel

import androidx.lifecycle.ViewModel
import app.calculator.domain.CalculatorAction
import app.calculator.domain.CalculatorEngine
import app.calculator.domain.CalculatorState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CalculatorViewModel : ViewModel() {

    private val _state = MutableStateFlow(CalculatorState())
    val state: StateFlow<CalculatorState> = _state.asStateFlow()

    fun onInput(action: CalculatorAction) {
        val current = _state.value
        _state.value = when (action) {
            is CalculatorAction.EnterNumber -> CalculatorEngine.enterNumber(current, action.number)
            is CalculatorAction.EnterDecimal -> CalculatorEngine.enterDecimal(current)
            is CalculatorAction.EnterOperation -> CalculatorEngine.enterOperation(current, action.operation)
            is CalculatorAction.Calculate -> CalculatorEngine.calculate(current)
            is CalculatorAction.Delete -> CalculatorEngine.delete(current)
            is CalculatorAction.Clear -> CalculatorEngine.clear(current)
            is CalculatorAction.ToggleSign -> CalculatorEngine.toggleSign(current)
        }
    }
}
