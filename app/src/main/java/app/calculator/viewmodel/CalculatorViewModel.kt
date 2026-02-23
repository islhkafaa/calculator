package app.calculator.viewmodel

import androidx.lifecycle.ViewModel
import app.calculator.domain.CalculatorAction
import app.calculator.domain.CalculatorState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CalculatorViewModel : ViewModel() {

    private val _state = MutableStateFlow(CalculatorState())
    val state: StateFlow<CalculatorState> = _state.asStateFlow()

    fun onInput(action: CalculatorAction) {}
}
