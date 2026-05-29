package com.ericmschmidt.classicsreader.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Suppress("KotlinConstantConditions")
data class PreferencesState(
    val textSize: Int = SettingsFields.TEXT_SIZE_DEFAULT.toInt(),
    val poemLines: Int = SettingsFields.POEM_LINES_DEFAULT.toInt(),
    val showPageControls: Boolean = SettingsFields.SHOW_PAGE_CONTROLS_DEFAULT,
    val displayType: String = SettingsFields.DISPLAY_TYPE_DEFAULT,
    val recentlyRead: String = SettingsFields.RECENTLY_READ,
)

class PreferencesDataStore(val context: Context) {
    private object PreferencesKeys {
        val TEXT_SIZE = intPreferencesKey(SettingsFields.TEXT_SIZE)
        val POEM_LINES = intPreferencesKey(SettingsFields.POEM_LINES)
        val SHOW_PAGE_CONTROLS = booleanPreferencesKey(SettingsFields.SHOW_PAGE_CONTROLS)
        val DISPLAY_TYPE = stringPreferencesKey(SettingsFields.DISPLAY_TYPE)
        val RECENTLY_READ = stringPreferencesKey(SettingsFields.RECENTLY_READ)
    }

    fun preferencesFlow(): Flow<PreferencesState> = context.dataStore.data.map { preferences ->
        return@map PreferencesState(
            textSize = preferences[PreferencesKeys.TEXT_SIZE] ?: SettingsFields.TEXT_SIZE_DEFAULT.toInt(),
            poemLines = preferences[PreferencesKeys.POEM_LINES] ?: SettingsFields.POEM_LINES_DEFAULT.toInt(),
            showPageControls = preferences[PreferencesKeys.SHOW_PAGE_CONTROLS] ?: SettingsFields.SHOW_PAGE_CONTROLS_DEFAULT,
            displayType = preferences[PreferencesKeys.DISPLAY_TYPE] ?: SettingsFields.DISPLAY_TYPE_DEFAULT,
            recentlyRead = preferences[PreferencesKeys.RECENTLY_READ] ?: ""
        )
    }

    suspend fun updateTextSize(newValue: Int) {
        writeIntSetting(context, PreferencesKeys.TEXT_SIZE, newValue)
    }

    suspend fun updatePoemLines(newValue: Int) {
        writeIntSetting(context, PreferencesKeys.POEM_LINES, newValue)
    }

    suspend fun updateShowPageControls(newValue: Boolean) {
        writeBoolSetting(context, PreferencesKeys.SHOW_PAGE_CONTROLS, newValue)
    }

    suspend fun updateDisplayType(newValue: String) {
        writeStringSetting(context, PreferencesKeys.DISPLAY_TYPE, newValue)
    }

    suspend fun updateRecentlyRead(newValue: String) {
        writeStringSetting(context, PreferencesKeys.RECENTLY_READ, newValue)
    }

    private suspend fun writeStringSetting(context: Context, key: Preferences.Key<String>, newValue: String) {
        context.dataStore.edit { settings ->
            settings[key] = newValue
        }
    }

    private suspend fun writeIntSetting(context: Context, key: Preferences.Key<Int>, newValue: Int) {
        context.dataStore.edit { settings ->
            settings[key] = newValue
        }
    }

    private suspend fun writeBoolSetting(context: Context, key: Preferences.Key<Boolean>, newValue: Boolean) {
        context.dataStore.edit { settings ->
            settings[key] = newValue
        }
    }
}