package app.calculator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.calculator.domain.CalculatorAction
import app.calculator.domain.CalculatorOperation

private val rows = listOf(
    listOf(
        Triple("AC", ButtonStyle.Action, CalculatorAction.Clear),
        Triple("+/-", ButtonStyle.Action, CalculatorAction.Clear),
        Triple("%", ButtonStyle.Operator, CalculatorAction.EnterOperation(CalculatorOperation.Percent)),
        Triple("÷", ButtonStyle.Operator, CalculatorAction.EnterOperation(CalculatorOperation.Divide))
    ),
    listOf(
        Triple("7", ButtonStyle.Number, CalculatorAction.EnterNumber(7)),
        Triple("8", ButtonStyle.Number, CalculatorAction.EnterNumber(8)),
        Triple("9", ButtonStyle.Number, CalculatorAction.EnterNumber(9)),
        Triple("×", ButtonStyle.Operator, CalculatorAction.EnterOperation(CalculatorOperation.Multiply))
    ),
    listOf(
        Triple("4", ButtonStyle.Number, CalculatorAction.EnterNumber(4)),
        Triple("5", ButtonStyle.Number, CalculatorAction.EnterNumber(5)),
        Triple("6", ButtonStyle.Number, CalculatorAction.EnterNumber(6)),
        Triple("-", ButtonStyle.Operator, CalculatorAction.EnterOperation(CalculatorOperation.Subtract))
    ),
    listOf(
        Triple("1", ButtonStyle.Number, CalculatorAction.EnterNumber(1)),
        Triple("2", ButtonStyle.Number, CalculatorAction.EnterNumber(2)),
        Triple("3", ButtonStyle.Number, CalculatorAction.EnterNumber(3)),
        Triple("+", ButtonStyle.Operator, CalculatorAction.EnterOperation(CalculatorOperation.Add))
    ),
    listOf(
        Triple("←", ButtonStyle.Action, CalculatorAction.Delete),
        Triple("0", ButtonStyle.Number, CalculatorAction.EnterNumber(0)),
        Triple(".", ButtonStyle.Number, CalculatorAction.EnterDecimal),
        Triple("=", ButtonStyle.Primary, CalculatorAction.Calculate)
    )
)

@Composable
fun CalculatorButtonGrid(
    onAction: (CalculatorAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        rows.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                row.forEach { (symbol, style, action) ->
                    CalculatorButton(
                        symbol = symbol,
                        style = style,
                        modifier = Modifier.weight(1f),
                        onClick = { onAction(action) }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}
