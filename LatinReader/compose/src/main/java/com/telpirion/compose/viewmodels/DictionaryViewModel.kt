package com.telpirion.compose.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ericmschmidt.classicsreader.data.DictionaryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Represents the state of the dictionary UI
data class DictionaryUiState(
    val searchQuery: String = "",
    val searchResult: String? = null,
    val searchHistory: List<String> = emptyList(),
    val isResultVisible: Boolean = false
)

class DictionaryViewModel(application: Application) : AndroidViewModel(application) {

    private val dictionaryRepository = DictionaryRepository(application)

    private val _uiState = MutableStateFlow(DictionaryUiState())
    val uiState = _uiState.asStateFlow()

    // We use a second UIState field to expose values to the ReadingScreen.
    private val _readingUiState = MutableStateFlow(ReadingUiState())
    val readingUiState = _readingUiState.asStateFlow()


    init {
        // Observe the search history from the repository
        viewModelScope.launch {
            dictionaryRepository.searchHistoryFlow.collect { history ->
                _uiState.update { it.copy(searchHistory = history) }
                if (history.isNotEmpty()){
                    _readingUiState.update {it.copy(
                        info = "",
                        content = "",
                        position = "",
                    )}
                }
            }
        }
    }

    /** Updates the search query in the UI state. */
    fun onQueryChange(query: String) {
        _uiState.update {
            currentState -> currentState.copy(searchQuery = query)
        }
        _readingUiState.update {
            currentState -> currentState.copy(
                info = query,
                content = "",
            )
        }
    }

    /** Performs a search for the given query. */
    fun search(query: String) {
        val trimmedQuery = query.trim()
        if (trimmedQuery.isBlank()) return

        viewModelScope.launch {
            // Get definition from the repository
            val definition = dictionaryRepository.getDefinition(trimmedQuery)
            // Persist the search term
            dictionaryRepository.addSearchTerm(trimmedQuery)

            _uiState.update {
                it.copy(
                    searchResult = definition ?: "Entry not found.",
                    isResultVisible = true
                )
            }
            _readingUiState.update {
                it.copy(
                    content = definition ?: "",
                    info = trimmedQuery,
                    position = dictionaryRepository.getDictionaryInfo()
                    )
            }
        }
    }

    /** Clears the search query text. */
    fun clearSearch() {
        _uiState.update { it.copy(searchQuery = "") }
        _readingUiState.update { it.copy(
            content = "",
            info = "",
            position = "",
        ) }

    }

    /** Hides the search result dialog. */
    @Suppress("unused")
    fun dismissResult() {
        _uiState.update { it.copy(isResultVisible = false, searchResult = null) }
        _readingUiState.update { it.copy(
            content = "",
            info = "",
            position = "",
        ) }
    }

    // A companion object for the factory is a common pattern for easy access.
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as Application)
                DictionaryViewModel(application)
            }
        }
    }
}