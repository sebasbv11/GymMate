package com.jsbv99.gymmate.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = GymGreen,
    onPrimary = GymNavy,
    primaryContainer = Color(0xFF123D55),
    onPrimaryContainer = Color(0xFFE3F4FA),
    secondary = GymNavyLight,
    onSecondary = GymWhite,
    secondaryContainer = Color(0xFF184A37),
    onSecondaryContainer = Color(0xFFD6F5DA),
    tertiary = Color(0xFFB3D8E8),
    onTertiary = GymNavy,
    tertiaryContainer = Color(0xFF244C5E),
    onTertiaryContainer = Color(0xFFD7EDF5),
    background = GymDarkSurface,
    onBackground = GymWhite,
    surface = GymDarkCard,
    onSurface = GymWhite,
    surfaceVariant = Color(0xFF233B4D),
    onSurfaceVariant = Color(0xFFD0E0E9),
    surfaceContainerLowest = Color(0xFF061725),
    surfaceContainerLow = Color(0xFF0D2538),
    surfaceContainer = GymDarkCard,
    surfaceContainerHigh = Color(0xFF1B3548),
    surfaceContainerHighest = Color(0xFF263E51),
    outline = Color(0xFF9BB0BE),
    outlineVariant = Color(0xFF425B6A),
    error = androidx.compose.ui.graphics.Color(0xFFFFB4AB),
    onError = androidx.compose.ui.graphics.Color(0xFF690005)
)

private val LightColorScheme = lightColorScheme(
    primary = GymNavy,
    onPrimary = GymWhite,
    primaryContainer = Color(0xFFDCECF4),
    onPrimaryContainer = GymText,
    secondary = GymGreenDark,
    onSecondary = GymNavy,
    secondaryContainer = Color(0xFFD8F5DB),
    onSecondaryContainer = Color(0xFF12391C),
    tertiary = GymGreen,
    onTertiary = GymNavy,
    tertiaryContainer = Color(0xFFD4EBF4),
    onTertiaryContainer = Color(0xFF123648),
    background = GymWhite,
    onBackground = GymText,
    surface = GymSurface,
    onSurface = GymText,
    surfaceVariant = Color(0xFFE2EAF0),
    onSurfaceVariant = Color(0xFF354B5C),
    surfaceContainerLowest = Color.White,
    surfaceContainerLow = GymSurface,
    surfaceContainer = Color(0xFFEBF1F5),
    surfaceContainerHigh = Color(0xFFE4EDF2),
    surfaceContainerHighest = Color(0xFFDCE7ED),
    outline = Color(0xFF586E7D),
    outlineVariant = Color(0xFFB7C9D3),
    error = GymError
)

@Composable
fun GymMateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
