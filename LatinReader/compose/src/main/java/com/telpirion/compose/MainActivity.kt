package com.telpirion.compose

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.preference.PreferenceManager
import com.ericmschmidt.classicsreader.ui.fragments.ReadingFragment.RECENTLY_READ
import com.ericmschmidt.classicsreader.ui.fragments.SettingsFragment.DISPLAY_TYPE
import com.ericmschmidt.classicsreader.ui.fragments.SettingsFragment.POEM_LINES
import com.ericmschmidt.classicsreader.ui.fragments.SettingsFragment.SHOW_PAGE_CONTROLS
import com.ericmschmidt.classicsreader.ui.fragments.SettingsFragment.TEXT_SIZE
import com.telpirion.compose.ui.ReaderApp
import com.telpirion.compose.ui.theme.LatinReaderTheme
import com.telpirion.compose.viewmodels.SharedPreferencesMigration

@Suppress("unused")
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        val keysToMigrate: Set<String> = mutableSetOf(POEM_LINES, TEXT_SIZE, SHOW_PAGE_CONTROLS, DISPLAY_TYPE, RECENTLY_READ)
        SharedPreferencesMigration(
            produceSharedPreferences = { PreferenceManager.getDefaultSharedPreferences(this) },
            keysToMigrate = keysToMigrate
        )

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
