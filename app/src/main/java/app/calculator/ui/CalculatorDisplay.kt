package app.calculator.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.togetherWith
import androidx.compose.ui.unit.IntOffset
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
    onHistoryClick: () -> Unit,
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onHistoryClick) {
                Icon(
                    imageVector = Icons.Default.History,
                    contentDescription = "History",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        AnimatedContent(
            targetState = secondaryText,
            transitionSpec = {
                (slideInVertically { height -> height / 2 } + fadeIn())
                    .togetherWith(slideOutVertically { height -> -height / 2 } + fadeOut())
                    .using(SizeTransform(clip = false))
            },
            label = "secondaryTextAnimation",
            modifier = Modifier.fillMaxWidth()
        ) { text ->
            if (text.isNotEmpty()) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        AnimatedContent(
            targetState = primaryText,
            transitionSpec = {
                val springSpec = spring<IntOffset>(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
                (slideInVertically(animationSpec = springSpec) { height -> height / 3 } + fadeIn())
                    .togetherWith(slideOutVertically(animationSpec = springSpec) { height -> -height / 3 } + fadeOut())
                    .using(SizeTransform(clip = false))
            },
            label = "primaryTextAnimation",
            modifier = Modifier.fillMaxWidth()
        ) { text ->
            val fontSize = when {
                text.length > 12 -> 36.sp
                text.length > 8 -> 48.sp
                else -> 64.sp
            }

            val isResult = state.secondaryNumber.isEmpty() && state.operation == null && state.primaryNumber.isNotEmpty()
            val textColor by animateColorAsState(
                targetValue = if (isResult) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                label = "textColorAnimation"
            )

            Text(
                text = text,
                fontSize = fontSize,
                fontWeight = FontWeight.Light,
                color = textColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
