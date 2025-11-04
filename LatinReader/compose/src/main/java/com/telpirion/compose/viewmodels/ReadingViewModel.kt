package com.telpirion.compose.viewmodels

import android.app.Application
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ericmschmidt.classicsreader.MyApplication
import com.ericmschmidt.classicsreader.data.Library
import com.ericmschmidt.classicsreader.data.TOCEntry
import com.ericmschmidt.classicsreader.data.WorkInfo
import com.ericmschmidt.classicsreader.ui.fragments.ReadingFragment.RECENTLY_READ
import com.telpirion.compose.utils.writeStringSetting
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import com.ericmschmidt.classicsreader.R as CoreResources
import com.ericmschmidt.classicsreader.data.ReadingViewModel as RVM

data class ReadingUiState(
    val content: String = "",
    val translationContent: String = "",
    val info: String = "",
    val position: String = "",
    val tocAvailable: Boolean = false,
    val isTranslation: Boolean = false,
    val toc: Array<TOCEntry>? = emptyList<TOCEntry>().toTypedArray(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReadingUiState

        if (tocAvailable != other.tocAvailable) return false
        if (isTranslation != other.isTranslation) return false
        if (content != other.content) return false
        if (info != other.info) return false
        if (position != other.position) return false
        if (!toc.contentEquals(other.toc)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = tocAvailable.hashCode()
        result = 31 * result + isTranslation.hashCode()
        result = 31 * result + content.hashCode()
        result = 31 * result + info.hashCode()
        result = 31 * result + position.hashCode()
        result = 31 * result + (toc?.contentHashCode() ?: 0)
        return result
    }
}

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

    private var translationContentLines: List<String> = emptyList()

    private var content: RVM? = null
    private var translationContent: RVM? = null

    val recentlyReadKey = stringPreferencesKey(RECENTLY_READ)

    init {
        if (!workId.isNullOrEmpty()) {
            val manifest = MyApplication.getManifest()
            val library = Library(manifest.collection)
            workInfo = library.getWorkInfoByID(workId)

            // TODO(telpirion): integrate old ReaderViewModel with new one
            content = RVM(workInfo, isTranslation, poemLines, application)
            translationContent = RVM(workInfo, !isTranslation, poemLines, application)

            @Suppress("UNCHECKED_CAST")
            contentLines = listOf(content?.currentPage) as List<*> as List<String>
            @Suppress("UNCHECKED_CAST")
            translationContentLines = listOf(translationContent?.currentPage) as List<*> as List<String>

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

    fun goToChapter(entry: TOCEntry){
        val book = entry.book
        val page = entry.line
        this.content?.setCurrentBook(book)
        this.content?.setCurrentLine(page)
        updateState()
    }

    @Suppress("UNCHECKED_CAST")
    private fun updateState() {
        contentLines = listOf(content?.currentPage) as List<*> as List<String>
        translationContentLines = listOf(translationContent?.currentPage) as List<*> as List<String>
        _uiState.value = ReadingUiState(
            content = contentLines.joinToString("\n"),
            translationContent = translationContentLines.joinToString("\n"),
            info = workInfo?.title ?: "Unknown Work",
            position = content?.readingPositionString as String,
            tocAvailable = workInfo?.getTocEntries()?.isNotEmpty() ?: false,
            isTranslation = isTranslation,
            toc = workInfo?.getTocEntries()
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
