package com.example.todolist.ui.theme

import android.app.Activity

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.todolist.ui.theme.Typography

private val BlackWhiteDarkColorScheme = darkColorScheme(
    primary = TextBlack,
    onPrimary = TextBlack,
    primaryContainer = CardColor,
    onPrimaryContainer = TextBlack,
    secondary = CardColor,
    onSecondary = CardColor,
    background = AppBackground,
    onBackground = TextBlack,
    surface = SoftSurface,
    onSurface = TextBlack,
    error = TextBlack,
    onError = SoftSurface
)

private val BlackWhiteLightColorScheme = lightColorScheme(
    primary = TextBlack,
    onPrimary = SoftSurface,
    primaryContainer = CardColor,
    onPrimaryContainer = TextBlack,
    secondary = CardColor,
    onSecondary = CardColor,
    background = AppBackground,
    onBackground = TextBlack,
    surface = SoftSurface,
    onSurface = TextBlack,
    error = TextBlack,
    onError = SoftSurface
)

@Composable
fun TodoappTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        BlackWhiteDarkColorScheme
    } else {
        BlackWhiteLightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
