package com.example.dailyquiztest.presentation.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * @property default 0 dp.
 * @property topPadding 48 dp.
*/
data class Dimensions(
    val default: Dp = 0.dp,
    val topPadding: Dp = 48.dp
)

val LocalSpacing = compositionLocalOf { Dimensions() }