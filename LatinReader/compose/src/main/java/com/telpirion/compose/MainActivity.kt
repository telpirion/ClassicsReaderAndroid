package com.telpirion.compose

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.ericmschmidt.classicsreader.MyApplication
import com.telpirion.compose.ui.ReaderApp
import com.telpirion.compose.ui.theme.ReaderTheme

@Suppress("unused")
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings",
    produceMigrations = { context ->
        listOf(SharedPreferencesMigration(context, "settings"))
    })
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val colorScheme = MyApplication.instance.themeColors
        setContent {
            ReaderTheme(colorScheme) {
                val windowSizeClass =
                    calculateWindowSizeClass(this)
                ReaderApp(windowSizeClass = windowSizeClass)
            }
        }
    }
}
