package com.sunday.quiz1.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.sunday.quiz1.ui.common.LocalSpacing
import com.sunday.quiz1.ui.common.Spacing

private val LightColorPalette = lightColors(
    primary = Amber400,
    primaryVariant = Indigo300,
    secondary = DeepPurple400,
    secondaryVariant = Grey400,
    surface = Green900,
    background = Grey300,
    error = Red500
)

private val DarkColorPalette = darkColors(
    primary = Amber600,
    primaryVariant = Indigo400,
    secondary = DeepPurple600,
    secondaryVariant = Grey500,
    surface = Green900,
    background = Grey900,
    error = Red400
)

val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current

@Composable
fun Quiz1Theme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}