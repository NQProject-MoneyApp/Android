package com.nqproject.MoneyApp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color


object AppTheme {

    val colors: AppThemeColors
        @Composable
        @ReadOnlyComposable
        get() = AppThemeColors(
            primary = AppColors.Yellow,
            primaryVariant = AppColors.YellowDark,
            secondary = AppColors.Gray,
            background = AppColors.Black,
            surface = AppColors.DarkGray,
            secondaryVariant = AppColors.OrangeMedium,
            error = Color(0xFFCF6679),
            onPrimary = Color.Black,
            onSecondary = Color.Black,
            onBackground = Color.White,
            onSurface = Color.White,
            onError = Color.Black,
            hintText = AppColors.MiddleGray,
        )

}

class AppThemeColors(
    val primary: Color,
    val primaryVariant: Color,
    val secondary: Color,
    val secondaryVariant: Color,
    val background: Color,
    val surface: Color,
    val error: Color,
    val onPrimary: Color,
    val onSecondary: Color,
    val onBackground: Color,
    val onSurface: Color,
    val onError: Color,

    val hintText: Color,
) {
    fun toMaterialColors(darkTheme: Boolean): Colors {
        return Colors(
            primary = this.primary,
            primaryVariant = this.primaryVariant,
            secondary = this.secondary,
            background = this.background,
            surface = this.surface,
            onSurface = this.onSurface,
            onPrimary = this.onPrimary,
            onSecondary = this.onSecondary,
            error = this.error,
            onBackground = this.onBackground,
            onError = this.onError,
            secondaryVariant = this.secondaryVariant,
            isLight = !darkTheme,
        )
    }
}

@Composable
fun MoneyAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    CompositionLocalProvider(LocalContentColor provides Color.White) {
        MaterialTheme(
            colors = AppTheme.colors.toMaterialColors(true),
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}