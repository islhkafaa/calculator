package app.calculator.ui.theme

import androidx.compose.ui.graphics.Color

val Purple10 = Color(0xFF21005D)
val Purple20 = Color(0xFF381E72)
val Purple30 = Color(0xFF4F378B)
val Purple40 = Color(0xFF6650A4)
val Purple80 = Color(0xFFD0BCFF)
val Purple90 = Color(0xFFEADDFF)

val PurpleGrey10 = Color(0xFF21005D)
val PurpleGrey20 = Color(0xFF332D41)
val PurpleGrey30 = Color(0xFF4A4458)
val PurpleGrey40 = Color(0xFF625B71)
val PurpleGrey80 = Color(0xFFCCC2DC)
val PurpleGrey90 = Color(0xFFEDDEF8)

val Pink10 = Color(0xFF370B1E)
val Pink20 = Color(0xFF7D2949)
val Pink30 = Color(0xFF984061)
val Pink40 = Color(0xFFB3587A)
val Pink80 = Color(0xFFEFB8C8)
val Pink90 = Color(0xFFFFD8E4)

val LightColorScheme = androidx.compose.material3.lightColorScheme(
    primary = Purple40,
    onPrimary = Color.White,
    primaryContainer = Purple90,
    onPrimaryContainer = Purple10,
    secondary = PurpleGrey40,
    onSecondary = Color.White,
    secondaryContainer = PurpleGrey90,
    onSecondaryContainer = PurpleGrey10,
    tertiary = Pink40,
    onTertiary = Color.White,
    tertiaryContainer = Pink90,
    onTertiaryContainer = Pink10,
)

val DarkColorScheme = androidx.compose.material3.darkColorScheme(
    primary = Purple80,
    onPrimary = Purple20,
    primaryContainer = Purple30,
    onPrimaryContainer = Purple90,
    secondary = PurpleGrey80,
    onSecondary = PurpleGrey20,
    secondaryContainer = PurpleGrey30,
    onSecondaryContainer = PurpleGrey90,
    tertiary = Pink80,
    onTertiary = Pink20,
    tertiaryContainer = Pink30,
    onTertiaryContainer = Pink90,
)
