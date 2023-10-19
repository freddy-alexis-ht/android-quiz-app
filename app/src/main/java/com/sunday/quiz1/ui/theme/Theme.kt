package com.sunday.quiz1.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import com.sunday.quiz1.ui.common.*

private val LightColorPalette = lightColors(
    primary = Orange400,
    primaryVariant = Indigo300,
    secondary = DeepPurple400,
    secondaryVariant = Grey400,
    error = Red500,
    surface = Grey400,
    onSurface = Grey900,
//    background = Grey300,
)

private val DarkColorPalette = darkColors(
    primary = Amber600,
    primaryVariant = Indigo400,
    secondary = DeepPurple600,
    secondaryVariant = Grey500,
    error = Red400,
    surface = Grey500,
    onSurface = Grey900,
//    background = Grey900,
)

val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current

val MaterialTheme.customColors: CustomColors
    @Composable
    @ReadOnlyComposable
    get() = CustomColorsPalette.current

@Composable
fun Quiz1Theme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors =
        if (darkTheme) DarkColorPalette
        else LightColorPalette

    val customColors =
        if (darkTheme) DarkCustomColorsPalette
        else LightCustomColorsPalette

    CompositionLocalProvider(
        CustomColorsPalette provides customColors
    ) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}