package app.calculator.ui

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import app.calculator.ui.theme.LocalExpressiveShapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material3.Icon

import androidx.compose.foundation.layout.size

@Composable
fun CalculatorButton(
    symbol: String,
    style: ButtonStyle,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    onClick: () -> Unit
) {
    var pressed by remember { mutableStateOf(false) }
    val haptic = LocalHapticFeedback.current

    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.88f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "buttonScale"
    )

    val containerColor = when (style) {
        ButtonStyle.Number -> MaterialTheme.colorScheme.surfaceVariant
        ButtonStyle.Operator -> MaterialTheme.colorScheme.secondaryContainer
        ButtonStyle.Action -> MaterialTheme.colorScheme.tertiaryContainer
        ButtonStyle.Primary -> MaterialTheme.colorScheme.primary
    }

    val contentColor = when (style) {
        ButtonStyle.Number -> MaterialTheme.colorScheme.onSurfaceVariant
        ButtonStyle.Operator -> MaterialTheme.colorScheme.onSecondaryContainer
        ButtonStyle.Action -> MaterialTheme.colorScheme.onTertiaryContainer
        ButtonStyle.Primary -> MaterialTheme.colorScheme.onPrimary
    }

    val expressiveShapes = LocalExpressiveShapes.current
    val shape: Shape = when (style) {
        ButtonStyle.Number -> expressiveShapes.number
        ButtonStyle.Operator -> expressiveShapes.operator
        ButtonStyle.Action -> expressiveShapes.action
        ButtonStyle.Primary -> expressiveShapes.primary
    }

    Surface(
        shape = shape,
        color = containerColor,
        contentColor = contentColor,
        modifier = modifier
            .aspectRatio(1f)
            .scale(scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        pressed = true
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        tryAwaitRelease()
                        pressed = false
                        onClick()
                    }
                )
            }
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = symbol,
                    modifier = Modifier.size(32.dp)
                )
            } else {
                Text(
                    text = symbol,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
