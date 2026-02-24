package app.calculator.domain

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

object CalculatorEngine {

    private const val MAX_DIGITS = 12
    private const val ERROR = "Error"

    fun enterNumber(state: CalculatorState, digit: Int): CalculatorState {
        if (state.primaryNumber == ERROR) return state
        if (state.primaryNumber.replace("-", "").replace(".", "").length >= MAX_DIGITS) return state
        val newPrimary = when {
            state.primaryNumber == "0" -> digit.toString()
            state.primaryNumber == "-0" -> "-$digit"
            else -> state.primaryNumber + digit.toString()
        }
        return state.copy(primaryNumber = newPrimary)
    }

    fun enterDecimal(state: CalculatorState): CalculatorState {
        if (state.primaryNumber == ERROR) return state
        if (state.primaryNumber.contains(".")) return state
        val newPrimary = if (state.primaryNumber.isEmpty()) "0." else "${state.primaryNumber}."
        return state.copy(primaryNumber = newPrimary)
    }

    fun enterOperation(state: CalculatorState, operation: CalculatorOperation): CalculatorState {
        if (state.primaryNumber == ERROR) return state
        return when {
            state.primaryNumber.isEmpty() && state.secondaryNumber.isNotEmpty() ->
                state.copy(operation = operation)
            state.secondaryNumber.isNotEmpty() && state.primaryNumber.isNotEmpty() -> {
                val result = evaluate(state)
                if (result.primaryNumber == ERROR) result
                else CalculatorState(
                    primaryNumber = "",
                    secondaryNumber = result.primaryNumber,
                    operation = operation
                )
            }
            state.primaryNumber.isNotEmpty() ->
                CalculatorState(
                    primaryNumber = "",
                    secondaryNumber = state.primaryNumber,
                    operation = operation
                )
            else -> state
        }
    }

    fun calculate(state: CalculatorState): CalculatorState {
        if (state.primaryNumber.isEmpty() || state.secondaryNumber.isEmpty() || state.operation == null) {
            return state
        }
        return evaluate(state).copy(secondaryNumber = "", operation = null)
    }

    fun delete(state: CalculatorState): CalculatorState {
        if (state.primaryNumber == ERROR) return CalculatorState()
        if (state.primaryNumber.isEmpty()) return state
        val newPrimary = state.primaryNumber.dropLast(1)
        return state.copy(primaryNumber = newPrimary)
    }

    fun clear(state: CalculatorState): CalculatorState = CalculatorState()

    fun clearEntry(state: CalculatorState): CalculatorState {
        return if (state.primaryNumber.isNotEmpty()) {
            state.copy(primaryNumber = "")
        } else {
            state
        }
    }

    fun toggleSign(state: CalculatorState): CalculatorState {
        if (state.primaryNumber.isEmpty() || state.primaryNumber == ERROR) return state
        val toggled = if (state.primaryNumber.startsWith("-")) {
            state.primaryNumber.removePrefix("-")
        } else {
            "-${state.primaryNumber}"
        }
        return state.copy(primaryNumber = toggled)
    }

    fun percentage(state: CalculatorState): CalculatorState {
        if (state.primaryNumber.isEmpty() || state.primaryNumber == ERROR) return state
        return try {
            val primary = BigDecimal(state.primaryNumber)
            val result = if (state.secondaryNumber.isNotEmpty() && state.operation != null) {
                val secondary = BigDecimal(state.secondaryNumber)
                secondary.multiply(primary.divide(BigDecimal("100"), MathContext.DECIMAL128))
            } else {
                primary.divide(BigDecimal("100"), MathContext.DECIMAL128)
            }
            state.copy(primaryNumber = format(result))
        } catch (e: Exception) {
            state.copy(primaryNumber = ERROR)
        }
    }

    private fun evaluate(state: CalculatorState): CalculatorState {
        return try {
            val a = BigDecimal(state.secondaryNumber)
            val b = BigDecimal(state.primaryNumber)
            val result = when (state.operation) {
                CalculatorOperation.Add -> a.add(b, MathContext.DECIMAL128)
                CalculatorOperation.Subtract -> a.subtract(b, MathContext.DECIMAL128)
                CalculatorOperation.Multiply -> a.multiply(b, MathContext.DECIMAL128)
                CalculatorOperation.Divide -> {
                    if (b.compareTo(BigDecimal.ZERO) == 0) return state.copy(primaryNumber = ERROR)
                    a.divide(b, MathContext.DECIMAL128)
                }
                CalculatorOperation.Percent -> {
                    if (b.compareTo(BigDecimal.ZERO) == 0) return state.copy(primaryNumber = ERROR)
                    a.remainder(b, MathContext.DECIMAL128)
                }
                null -> return state
            }
            state.copy(primaryNumber = format(result))
        } catch (e: Exception) {
            state.copy(primaryNumber = ERROR)
        }
    }

    private fun format(value: BigDecimal): String {
        if (value.compareTo(BigDecimal.ZERO) == 0) return "0"
        val stripped = value.stripTrailingZeros()
        return if (stripped.scale() <= 0) {
            stripped.toPlainString()
        } else {
            val formatted = stripped.toPlainString()
            if (formatted.length > MAX_DIGITS + 1) {
                stripped.round(MathContext(MAX_DIGITS)).stripTrailingZeros().toPlainString()
            } else {
                formatted
            }
        }
    }
}
