package com.ericmschmidt.classicsreader.data

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException

// Define the DataStore instance once in a central file.
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "dictionary_prefs")

/**
 * Repository to manage dictionary data, including search history and definitions.
 */
class DictionaryRepository(context: Context) {

    private val dictionary = Dictionary() // Legacy Java dictionary class
    private val dataStore = context.dataStore

    private object PreferencesKeys {
        val SEARCH_HISTORY = stringSetPreferencesKey("search_history")
    }

    /**
     * A flow that emits the user's search history.
     */
    val searchHistoryFlow: Flow<List<String>> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            (preferences[PreferencesKeys.SEARCH_HISTORY] ?: emptySet()).sorted()
        }

    /**
     * Adds a new term to the search history in DataStore.
     */
    suspend fun addSearchTerm(term: String) {
        dataStore.edit { preferences ->
            val currentHistory = preferences[PreferencesKeys.SEARCH_HISTORY] ?: emptySet()
            // Add the new term, ensuring it's clean and lowercase.
            preferences[PreferencesKeys.SEARCH_HISTORY] = currentHistory + term.trim().lowercase()
        }
    }

    /**
     * Retrieves a definition using the legacy Dictionary class.
     * This is a suspending function to allow moving the work off the main thread.
     */
    suspend fun getDefinition(entry: String): String? = withContext(Dispatchers.IO) {
        dictionary.getEntry(entry)
    }

    /**
     * Retrieves the dictionary's title.
     */
    fun getDictionaryInfo(): String {
        return dictionary.dictionaryInfo?.title as String
    }

    fun getRandom(): String {
        return dictionary.getRandomEntry() as String
    }
}