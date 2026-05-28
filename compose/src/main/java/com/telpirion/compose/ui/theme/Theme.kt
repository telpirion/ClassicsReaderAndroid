package com.telpirion.compose.ui.theme

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

@Composable
fun ReaderTheme(
    colorScheme: ColorScheme = LightColorScheme,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
