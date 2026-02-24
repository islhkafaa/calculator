package app.calculator.ui.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Immutable
data class ExpressiveShapes(
    val number: Shape = RoundedCornerShape(28.dp),
    val operator: Shape = RoundedCornerShape(20.dp),
    val action: Shape = RoundedCornerShape(16.dp),
    val primary: Shape = CircleShape
)

val LocalExpressiveShapes = staticCompositionLocalOf { ExpressiveShapes() }
