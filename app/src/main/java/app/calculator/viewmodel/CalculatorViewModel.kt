package app.calculator.viewmodel

import androidx.lifecycle.ViewModel
import app.calculator.domain.CalculatorAction
import app.calculator.domain.CalculatorEngine
import app.calculator.domain.CalculatorState
import app.calculator.domain.HistoryEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CalculatorViewModel : ViewModel() {

    private val _state = MutableStateFlow(CalculatorState())
    val state: StateFlow<CalculatorState> = _state.asStateFlow()

    private val _history = MutableStateFlow<List<HistoryEntry>>(emptyList())
    val history: StateFlow<List<HistoryEntry>> = _history.asStateFlow()

    fun onInput(action: CalculatorAction) {
        val current = _state.value
        val next = when (action) {
            is CalculatorAction.EnterNumber -> CalculatorEngine.enterNumber(current, action.number)
            is CalculatorAction.EnterDecimal -> CalculatorEngine.enterDecimal(current)
            is CalculatorAction.EnterOperation -> CalculatorEngine.enterOperation(current, action.operation)
            is CalculatorAction.Calculate -> {
                val result = CalculatorEngine.calculate(current)
                if (result.primaryNumber.isNotEmpty() && result.primaryNumber != current.primaryNumber) {
                    val expression = buildString {
                        append(current.secondaryNumber)
                        current.operation?.let { append(" ${it.symbol} ") }
                        append(current.primaryNumber)
                    }
                    _history.value = listOf(HistoryEntry(expression, result.primaryNumber)) + _history.value
                }
                result
            }
            is CalculatorAction.Delete -> CalculatorEngine.delete(current)
            is CalculatorAction.Clear -> CalculatorEngine.clear(current)
            is CalculatorAction.ToggleSign -> CalculatorEngine.toggleSign(current)
        }
        _state.value = next
    }

    fun restoreEntry(entry: HistoryEntry) {
        _state.value = CalculatorState(primaryNumber = entry.result)
    }

    fun clearHistory() {
        _history.value = emptyList()
    }
}
