package app.calculator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.calculator.viewmodel.CalculatorViewModel

@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()
    val history by viewModel.history.collectAsState()
    var showHistory by remember { mutableStateOf(false) }

    val bgColor1 = MaterialTheme.colorScheme.surface
    val bgColor2 = when (state.operation) {
        app.calculator.domain.CalculatorOperation.Add -> MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
        app.calculator.domain.CalculatorOperation.Subtract -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.05f)
        app.calculator.domain.CalculatorOperation.Multiply -> MaterialTheme.colorScheme.tertiary.copy(alpha = 0.05f)
        app.calculator.domain.CalculatorOperation.Divide -> MaterialTheme.colorScheme.error.copy(alpha = 0.05f)
        else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.05f)
    }

    val animatedBgColor2 by animateColorAsState(
        targetValue = bgColor2,
        label = "bgColorAnimation"
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(bgColor1, animatedBgColor2)
                    )
                )
        ) {
            BoxWithConstraints(modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing)) {
                AnimatedContent(
                    targetState = maxWidth > maxHeight,
                    transitionSpec = {
                        if (targetState) {
                            (slideInHorizontally { width -> width } + fadeIn())
                                .togetherWith(slideOutHorizontally { width -> -width } + fadeOut())
                        } else {
                            (slideInHorizontally { width -> -width } + fadeIn())
                                .togetherWith(slideOutHorizontally { width -> width } + fadeOut())
                        }.using(SizeTransform(clip = false))
                    },
                    label = "layoutTransition"
                ) { isLandscape ->
                    if (isLandscape) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            CalculatorDisplay(
                                state = state,
                                onHistoryClick = { showHistory = true },
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f)
                            )
                            CalculatorButtonGrid(
                                onAction = viewModel::onInput,
                                state = state,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f)
                            )
                        }
                    } else {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            CalculatorDisplay(
                                state = state,
                                onHistoryClick = { showHistory = true },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            )
                            CalculatorButtonGrid(
                                onAction = viewModel::onInput,
                                state = state,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }

            HistoryPanel(
                history = history,
                visible = showHistory,
                onRestore = { entry ->
                    viewModel.restoreEntry(entry)
                    showHistory = false
                },
                onClear = { viewModel.clearHistory() },
                onDeleteEntry = { id -> viewModel.deleteHistoryEntry(id) },
                onDismiss = { showHistory = false },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
