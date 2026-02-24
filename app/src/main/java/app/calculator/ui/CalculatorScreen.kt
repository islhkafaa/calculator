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
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.calculator.viewmodel.CalculatorViewModel

@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()
    val history by viewModel.history.collectAsState()
    var showHistory by remember { mutableStateOf(false) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            BoxWithConstraints(modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing)) {
                if (maxWidth > maxHeight) {
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

            HistoryPanel(
                history = history,
                visible = showHistory,
                onRestore = { entry ->
                    viewModel.restoreEntry(entry)
                    showHistory = false
                },
                onClear = { viewModel.clearHistory() },
                onDismiss = { showHistory = false },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
