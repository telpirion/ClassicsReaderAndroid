package com.telpirion.compose.utils

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.telpirion.compose.ui.dataStore

// Store recently read in preferences here
suspend fun writeStringSetting(context: Context, key: Preferences.Key<String>, newValue: String) {
    context.dataStore.edit { settings ->
        settings[key] = newValue
    }
}

suspend fun writeIntSetting(context: Context, key: Preferences.Key<Int>, newValue: Int) {
    context.dataStore.edit { settings ->
        settings[key] = newValue
    }
}

suspend fun writeBoolSetting(context: Context, key: Preferences.Key<Boolean>, newValue: Boolean) {
    context.dataStore.edit { settings ->
        settings[key] = newValue
    }
}