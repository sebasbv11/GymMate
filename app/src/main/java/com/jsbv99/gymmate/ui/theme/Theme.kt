package com.jsbv99.gymmate.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = GymGreen,
    onPrimary = GymNavy,
    secondary = GymNavyLight,
    onSecondary = GymWhite,
    background = GymDarkSurface,
    onBackground = GymWhite,
    surface = GymDarkCard,
    onSurface = GymWhite,
    error = GymError
)

private val LightColorScheme = lightColorScheme(
    primary = GymNavy,
    onPrimary = GymWhite,
    secondary = GymGreenDark,
    onSecondary = GymNavy,
    tertiary = GymGreen,
    onTertiary = GymNavy,
    background = GymWhite,
    onBackground = GymText,
    surface = GymSurface,
    onSurface = GymText,
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
