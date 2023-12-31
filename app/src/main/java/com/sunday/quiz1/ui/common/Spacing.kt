package com.sunday.quiz1.ui.common

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val default: Dp = 0.dp,
    val simple: Dp = 1.dp,
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val mediumPlus: Dp = 24.dp,
    val large: Dp = 32.dp,
    val largePlus: Dp = 48.dp,
    val extraLarge: Dp = 64.dp,
    /* Others */
    val lottieAnimation: Dp = 400.dp,
)
val LocalSpacing = compositionLocalOf { Spacing() }