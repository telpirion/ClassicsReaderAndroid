package com.telpirion.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.telpirion.compose.ui.components.ReaderApp
import com.telpirion.compose.ui.theme.LatinReaderTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LatinReaderTheme {
                val windowSizeClass =
                    calculateWindowSizeClass(this)
                ReaderApp(windowSizeClass = windowSizeClass)
            }
        }
    }
}
