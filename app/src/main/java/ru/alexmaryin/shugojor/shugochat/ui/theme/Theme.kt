package ru.alexmaryin.shugojor.shugochat.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = primary,
    primaryVariant = textUnderline,
    onPrimary = onPrimary,
    background = darkBackground,
    surface = activeSurface,
    onSurface = textMain,
    secondary = surface,
    onSecondary = textSecondary,
)

@Composable
fun ShugochatTheme(content: @Composable () -> Unit) {
        MaterialTheme(
        colors = DarkColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}