package com.telpirion.compose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val LightColorScheme = lightColorScheme(
    primary = TelpirionWhite,
    secondary = TelpirionOrange,
    tertiary = TelpirionGray,
    background = TGray40,
    onBackground = TelpirionWhite,
    onPrimary = TGray80,
    onSecondary = TWhite40,
    onTertiary = TOrange40,
    onSurface = TGray40,
)

private val DarkColorScheme = lightColorScheme().copy()

@Composable
fun LatinReaderTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

object LatinReaderTheme {
    val colorScheme: ColorScheme
        @Composable
        get() = MaterialTheme.colorScheme
}