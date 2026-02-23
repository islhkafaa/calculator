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

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.*

data class ButtonData(
    val symbol: String,
    val style: ButtonStyle,
    val action: CalculatorAction,
    val icon: ImageVector? = null
)

private val rows = listOf(
    listOf(
        ButtonData("AC", ButtonStyle.Action, CalculatorAction.Clear),
        ButtonData("+/-", ButtonStyle.Action, CalculatorAction.ToggleSign, Icons.Default.Exposure),
        ButtonData("%", ButtonStyle.Operator, CalculatorAction.EnterOperation(CalculatorOperation.Percent), Icons.Default.Percent),
        ButtonData("÷", ButtonStyle.Operator, CalculatorAction.EnterOperation(CalculatorOperation.Divide))
    ),
    listOf(
        ButtonData("7", ButtonStyle.Number, CalculatorAction.EnterNumber(7)),
        ButtonData("8", ButtonStyle.Number, CalculatorAction.EnterNumber(8)),
        ButtonData("9", ButtonStyle.Number, CalculatorAction.EnterNumber(9)),
        ButtonData("×", ButtonStyle.Operator, CalculatorAction.EnterOperation(CalculatorOperation.Multiply), Icons.Default.Close)
    ),
    listOf(
        ButtonData("4", ButtonStyle.Number, CalculatorAction.EnterNumber(4)),
        ButtonData("5", ButtonStyle.Number, CalculatorAction.EnterNumber(5)),
        ButtonData("6", ButtonStyle.Number, CalculatorAction.EnterNumber(6)),
        ButtonData("-", ButtonStyle.Operator, CalculatorAction.EnterOperation(CalculatorOperation.Subtract), Icons.Default.Remove)
    ),
    listOf(
        ButtonData("1", ButtonStyle.Number, CalculatorAction.EnterNumber(1)),
        ButtonData("2", ButtonStyle.Number, CalculatorAction.EnterNumber(2)),
        ButtonData("3", ButtonStyle.Number, CalculatorAction.EnterNumber(3)),
        ButtonData("+", ButtonStyle.Operator, CalculatorAction.EnterOperation(CalculatorOperation.Add), Icons.Default.Add)
    ),
    listOf(
        ButtonData("←", ButtonStyle.Action, CalculatorAction.Delete, Icons.AutoMirrored.Filled.Backspace),
        ButtonData("0", ButtonStyle.Number, CalculatorAction.EnterNumber(0)),
        ButtonData(".", ButtonStyle.Number, CalculatorAction.EnterDecimal),
        ButtonData("=", ButtonStyle.Primary, CalculatorAction.Calculate)
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
                row.forEach { button ->
                    CalculatorButton(
                        symbol = button.symbol,
                        style = button.style,
                        modifier = Modifier.weight(1f),
                        icon = button.icon,
                        onClick = { onAction(button.action) }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}
