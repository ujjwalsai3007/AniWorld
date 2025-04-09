package com.example.aniworld.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = WarmDeep,
    secondary = WarmLight,
    tertiary = WarmDark,
    background = WarmBackground.copy(alpha = 0.7f),
    surface = WarmSurface.copy(alpha = 0.7f),
    onPrimary = WarmText,
    onSecondary = WarmText,
    onTertiary = WarmBackground,
    onBackground = WarmText,
    onSurface = WarmText
)

private val LightColorScheme = lightColorScheme(
    primary = WarmDeep,
    secondary = WarmLight,
    tertiary = WarmDark,
    background = WarmBackground,
    surface = WarmSurface,
    onPrimary = WarmBackground,
    onSecondary = WarmText,
    onTertiary = WarmBackground,
    onBackground = WarmText,
    onSurface = WarmText
)

@Composable
fun AniWorldTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Set to false to ensure our custom colors are used
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Set transparent status bar for edge-to-edge experience
            window.statusBarColor = Color.Transparent.toArgb()
            // Enable edge-to-edge layout
            WindowCompat.setDecorFitsSystemWindows(window, false)
            // Light or dark status bar icons based on theme
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}