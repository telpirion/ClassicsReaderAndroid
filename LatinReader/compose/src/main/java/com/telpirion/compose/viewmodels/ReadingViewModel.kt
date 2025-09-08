package com.telpirion.compose.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ericmschmidt.classicsreader.R as CoreResources
import com.ericmschmidt.classicsreader.data.Library
import com.ericmschmidt.classicsreader.data.ReadingViewModel as RVM
import com.ericmschmidt.classicsreader.data.WorkInfo
import com.ericmschmidt.classicsreader.data.placeholders.PseudoManifest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


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
    private var currentPageIndex = 0
    private val linesPerPage = if (poemLines > 0) poemLines else 5

    init {
        if (workId != null) {
            val manifest = PseudoManifest()
            val library = Library(manifest.collection)
            workInfo = library.getWorkInfoByID(workId)
            // In a real app, you would parse the XML file from work.location
            // For this example, we'll use placeholder content.

            // TODO(telpirion): integrate old ReaderViewModel with new one
            val content = RVM(workInfo, isTranslation, poemLines, application)

            contentLines = listOf(content.currentPage)

            updateState()
        } else {
            _uiState.value = ReadingUiState(
                content = application.getString(CoreResources.string.reading_no_book_open)
            )
        }
    }

    fun goToPage(isNext: Boolean) {
        val newIndex = if (isNext) currentPageIndex + 1 else currentPageIndex - 1
        val totalPages = (contentLines.size + linesPerPage - 1) / linesPerPage
        if (newIndex in 0 until totalPages) {
            currentPageIndex = newIndex
            updateState()
        }
    }

    private fun updateState() {
        val start = currentPageIndex * linesPerPage
        val end = (start + linesPerPage).coerceAtMost(contentLines.size)
        val totalPages = (contentLines.size + linesPerPage - 1) / linesPerPage

        _uiState.value = ReadingUiState(
            content = contentLines.subList(start, end).joinToString("\n"),
            info = workInfo?.title ?: "Unknown Work",
            position = "Page ${currentPageIndex + 1} of $totalPages",
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
