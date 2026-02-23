package app.calculator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.calculator.domain.CalculatorState

@Composable
fun CalculatorDisplay(
    state: CalculatorState,
    modifier: Modifier = Modifier
) {
    val secondaryText = buildString {
        if (state.secondaryNumber.isNotEmpty()) {
            append(state.secondaryNumber)
            state.operation?.let { append(" ${it.symbol}") }
        }
    }

    val primaryText = state.primaryNumber.ifEmpty { "0" }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        if (secondaryText.isNotEmpty()) {
            Text(
                text = secondaryText,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Normal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }

        val fontSize = when {
            primaryText.length > 12 -> 36.sp
            primaryText.length > 8 -> 48.sp
            else -> 64.sp
        }

        Text(
            text = primaryText,
            fontSize = fontSize,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
