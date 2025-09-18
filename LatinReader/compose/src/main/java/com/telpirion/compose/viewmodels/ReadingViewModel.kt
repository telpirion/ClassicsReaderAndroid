package com.telpirion.compose.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ericmschmidt.classicsreader.MyApplication
import com.ericmschmidt.classicsreader.R as CoreResources
import com.ericmschmidt.classicsreader.data.Library
import com.ericmschmidt.classicsreader.data.ReadingViewModel as RVM
import com.ericmschmidt.classicsreader.data.WorkInfo
import com.ericmschmidt.classicsreader.ui.fragments.ReadingFragment.RECENTLY_READ
import com.telpirion.compose.ui.dataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

data class ReadingUiState(
    val content: String = "",
    val info: String = "",
    val position: String = "",
    val tocAvailable: Boolean = false,
    val isTranslation: Boolean = false
)

class ReadingViewModel(
    application: Application,
    workId: String?,
    private val isTranslation: Boolean,
    poemLines: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReadingUiState())
    val uiState: StateFlow<ReadingUiState> = _uiState

    private var workInfo: WorkInfo? = null
    private var contentLines: List<String> = emptyList()

    private var content: RVM? = null

    val recentlyReadKey = stringPreferencesKey(RECENTLY_READ)

    init {
        if (!workId.isNullOrEmpty()) {
            val manifest = MyApplication.getManifest()
            val library = Library(manifest.collection)
            workInfo = library.getWorkInfoByID(workId)

            // TODO(telpirion): integrate old ReaderViewModel with new one
            content = RVM(workInfo, isTranslation, poemLines, application)

            @Suppress("UNCHECKED_CAST")
            contentLines = listOf(content?.currentPage) as List<*> as List<String>

            updateState()

            // Update the recently read
            runBlocking {
                launch {
                    writeStringSetting(
                        context = application.baseContext,
                        recentlyReadKey,
                        newValue = workId)
                }
            }
        } else {
            _uiState.value = ReadingUiState(
                content = application.getString(CoreResources.string.reading_no_book_open)
            )
        }
    }

    fun goToPage(isNext: Boolean) {
        this.content?.goToPage(isNext)
        updateState()
    }

    // Store recently read in preferences here
    suspend fun writeStringSetting(context: Context, key: Preferences.Key<String>, newValue: String) {
        Log.d("writeStringSetting", "writeStringSetting: $newValue")
        context.dataStore.edit { settings ->
            settings[key] = newValue
        }
    }

    private fun updateState() {
        contentLines = listOf(content?.currentPage) as List<*> as List<String>
        _uiState.value = ReadingUiState(
            content = contentLines.joinToString("\n"),
            info = workInfo?.title ?: "Unknown Work",
            position = content?.readingPositionString as String,
            tocAvailable = workInfo?.tocEntries?.isNotEmpty() ?: false,
            isTranslation = isTranslation
        )
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val application: Application,
        private val workId: String?,
        private val isTranslation: Boolean,
        private val poemLines: Int
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ReadingViewModel(application, workId, isTranslation, poemLines) as T
        }
    }
}
