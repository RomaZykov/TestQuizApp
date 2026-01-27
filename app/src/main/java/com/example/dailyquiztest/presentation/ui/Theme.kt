package com.example.dailyquiztest.presentation.ui

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBlue,
    secondary = Color.White,
    tertiary = TertiaryBlue,
    onPrimary = SecondaryBlue,
    background = Grey,
    surface = Yellow,
    onSurface = Green,
    onBackground = Black,
    onSecondary = SecondaryWhite,
    error = Red
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    secondary = Color.White,
    tertiary = TertiaryBlue,
    onPrimary = SecondaryBlue,
    background = Grey,
    surface = Yellow,
    onSurface = Green,
    onBackground = Black,
    onSecondary = SecondaryWhite,
    error = Red
)

@Composable
fun DailyQuizeTestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> LightColorScheme
        else -> LightColorScheme
    }

    CompositionLocalProvider(
        LocalCustomColorScheme provides colorScheme,
        content = content
    )
}

internal val LocalCustomColorScheme = staticCompositionLocalOf { LightColorScheme }

object DailyQuizTheme {
    val colorScheme: ColorScheme
        @Composable @ReadOnlyComposable get() = LocalCustomColorScheme.current
    val typography: DailyQuizTypography
        @Composable @ReadOnlyComposable
        get() = LocalCustomTypography.current
    val dimensions: Dimensions
        @Composable @ReadOnlyComposable
        get() = LocalSpacing.current
}
