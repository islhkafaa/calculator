package app.calculator.ui

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
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import app.calculator.viewmodel.CalculatorViewModel

@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints(modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing)) {
            if (maxWidth > maxHeight) {
                Row(modifier = Modifier.fillMaxSize()) {
                    CalculatorDisplay(
                        state = state,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                    )
                    CalculatorButtonGrid(
                        onAction = viewModel::onInput,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                    )
                }
            } else {
                Column(modifier = Modifier.fillMaxSize()) {
                    CalculatorDisplay(
                        state = state,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    CalculatorButtonGrid(
                        onAction = viewModel::onInput,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
