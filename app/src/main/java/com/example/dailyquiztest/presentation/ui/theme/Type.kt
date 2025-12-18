package com.example.dailyquiztest.presentation.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class DailyQuizTypography(
    val title: TextStyle = TextStyle(
        color = Black,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.5.sp
    ),
    val titleTopBar: TextStyle = TextStyle(
        color = Color.White,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.5.sp
    ),
    val regular: TextStyle = TextStyle(
        color = Color.Black,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    val body: TextStyle = TextStyle(
        color = Black,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.5.sp
    ),
    val questionTitle: TextStyle = TextStyle(
        color = Black,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.5.sp
    ),
    val numberOfQuestions: TextStyle = TextStyle(
        color = SecondaryBlue,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.5.sp
    ),
    val dropDownItem: TextStyle = TextStyle(
        color = TertiaryBlue,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.5.sp
    ),
    val button: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

internal val LocalCustomTypography = staticCompositionLocalOf {
    DailyQuizTypography()
}