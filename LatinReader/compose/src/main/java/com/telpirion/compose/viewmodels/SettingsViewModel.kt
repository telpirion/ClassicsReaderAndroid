package com.telpirion.compose.viewmodels

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericmschmidt.classicsreader.data.DISPLAY_TYPE
import com.ericmschmidt.classicsreader.data.DISPLAY_TYPE_DEFAULT
import com.ericmschmidt.classicsreader.data.POEM_LINES
import com.ericmschmidt.classicsreader.data.POEM_LINES_DEFAULT
import com.ericmschmidt.classicsreader.data.SHOW_PAGE_CONTROLS
import com.ericmschmidt.classicsreader.data.TEXT_SIZE
import com.ericmschmidt.classicsreader.data.TEXT_SIZE_DEFAULT
import com.telpirion.compose.ui.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

data class SettingsScreenUiState(
    val textSize: Int = TEXT_SIZE_DEFAULT.toInt(),
    val poemLines: Int = POEM_LINES_DEFAULT.toInt(),
    val showPageControls: Boolean = true,
    val displayType: String = "List"
)

class SettingsViewMode(context: Context) : ViewModel() {

    // Get show page controls from preferences
    val showPageControlsKey = booleanPreferencesKey(SHOW_PAGE_CONTROLS)
    val showPageControls: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[showPageControlsKey] ?: true }

    // Get display type from preferences
    val displayTypeKey = stringPreferencesKey(DISPLAY_TYPE)
    val displayTypeValue: Flow<String> = context.dataStore.data
        .map { preferences -> preferences[displayTypeKey] ?: DISPLAY_TYPE_DEFAULT }

    // Get poem lines from preferences
    val poemLinesKey = intPreferencesKey(POEM_LINES)
    val poemLines: Flow<Int> = context.dataStore.data
        .map { preferences ->
            // No type safety.
            preferences[poemLinesKey] ?: (POEM_LINES_DEFAULT).toInt()
        }

    // Get text size from preferences
    val textSizeKey = intPreferencesKey(TEXT_SIZE)
    val textSize: Flow<Int> = context.dataStore.data
        .map { preferences ->
            // No type safety.
            preferences[textSizeKey] ?: TEXT_SIZE_DEFAULT.toInt()
        }

    private val settingsUiState = MutableStateFlow(SettingsScreenUiState())
    val settingsScreenUiState: StateFlow<SettingsScreenUiState> = settingsUiState.asStateFlow()

    private fun updateSettings(settingsKey: Preferences.Key<Any>, newValue: Any) {
        viewModelScope.launch {
            // How to access datastore from here???
        }
    }

}