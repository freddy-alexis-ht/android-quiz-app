package com.sunday.quiz1.ui.common

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.sunday.quiz1.ui.theme.Green900
import com.sunday.quiz1.ui.theme.Grey300
import com.sunday.quiz1.ui.theme.Grey900

@Immutable
data class CustomColors(
    val background1: Color = Color.Unspecified,
    val background2: Color = Color.Unspecified,
    val onBackground1: Color = Color.Unspecified,
    val onBackground2: Color = Color.Unspecified,
)

val CustomColorsPalette = staticCompositionLocalOf {
    CustomColors()
}

val LightCustomColorsPalette = CustomColors(
    background1 = Green900,
    onBackground1 = Grey900,
    background2 = Grey300,
)

val DarkCustomColorsPalette = CustomColors(
    background1 = Green900,
    background2 = Grey900,
)