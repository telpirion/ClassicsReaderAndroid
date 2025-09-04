package com.telpirion.compose.ui.components

import android.app.Application
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ericmschmidt.classicsreader.data.Library
import com.ericmschmidt.classicsreader.data.WorkInfo
import com.ericmschmidt.classicsreader.data.placeholders.PseudoManifest
import com.ericmschmidt.classicsreader.ui.fragments.SettingsFragment.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import com.ericmschmidt.classicsreader.R as CoreResources


class ReadingViewModel(
    application: Application,
    workId: String?,
    private val isTranslation: Boolean,
    poemLines: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReadingUiState())
    val uiState: StateFlow<ReadingUiState> = _uiState

    private var work: WorkInfo? = null
    private var contentLines: List<String> = emptyList()
    private var currentPageIndex = 0
    private val linesPerPage = if (poemLines > 0) poemLines else 5

    init {
        if (workId != null) {
            val manifest = PseudoManifest()
            val library = Library(manifest.collection)
            work = library.getWorkInfoByID(workId)
            // In a real app, you would parse the XML file from work.location
            // For this example, we'll use placeholder content.
            contentLines = List(100) { "Line ${it + 1} of the text." }
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
            info = work?.title ?: "Unknown Work",
            position = "Page ${currentPageIndex + 1} of $totalPages",
            tocAvailable = work?.tocEntries?.isNotEmpty() ?: false,
            isTranslation = isTranslation
        )
    }

    data class ReadingUiState(
        val content: String = "",
        val info: String = "",
        val position: String = "",
        val tocAvailable: Boolean = false,
        val isTranslation: Boolean = false
    )

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

@Composable
fun ReadingScreen(
    workId: String?,
    isTranslation: Boolean = false,
    navController: NavController,
) {
    val context = LocalContext.current

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
    val textSizeSp = textSize.collectAsState(
        initial = TEXT_SIZE_DEFAULT.toInt()).value.toFloat()

    // Get show page controls from preferences
    val showPageControlsKey = booleanPreferencesKey(SHOW_PAGE_CONTROLS)
    val showPageControls: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[showPageControlsKey] ?: true }

    val viewModel: ReadingViewModel = viewModel(
        factory = ReadingViewModel.Factory(
            application = context.applicationContext as Application,
            workId = workId,
            isTranslation = isTranslation,
            poemLines = poemLines.collectAsState(
                initial = POEM_LINES_DEFAULT.toInt()).value
        )
    )

    val uiState by viewModel.uiState.collectAsState()

    if (workId == null) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(stringResource(CoreResources.string.reading_no_book_open))
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = uiState.info,
            style = MaterialTheme.typography.titleMedium
        )

        ReadingContent(
            text = uiState.content,
            textSizeSp = textSizeSp,
            onPageTurn = { isNext -> viewModel.goToPage(isNext) },
            onShowMenu = { /* Logic to show menu will be here */ },
            modifier = Modifier.weight(1f)
        )

        Text(
            text = uiState.position,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        if (showPageControls.collectAsState(initial = true).value) {
            PageControls(
                onPrev = { viewModel.goToPage(false) },
                onNext = { viewModel.goToPage(true) },
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
private fun ReadingContent(
    text: String,
    textSizeSp: Float,
    onPageTurn: (isNext: Boolean) -> Unit,
    onShowMenu: () -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        val viewWidth = maxWidth
        val hitArea = viewWidth / 4 // Corresponds to HIT_AREA_RATIO = 4

        var showContextMenu by remember { mutableStateOf(false) }
        var contextMenuOffset by remember { mutableStateOf(DpOffset.Zero) }

        Text(
            text = text,
            fontSize = textSizeSp.sp,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { offset ->
                            when {
                                offset.x < hitArea.toPx() -> onPageTurn(false)
                                offset.x > (viewWidth - hitArea).toPx() -> onPageTurn(true)
                                else -> {
                                    // Tapping in the middle shows the context menu
                                    contextMenuOffset = DpOffset(offset.x.toDp(), offset.y.toDp())
                                    showContextMenu = true
                                }
                            }
                        }
                    )
                }
        )

        DropdownMenu(
            expanded = showContextMenu,
            onDismissRequest = { showContextMenu = false },
            offset = contextMenuOffset
        ) {
            DropdownMenuItem(
                text = { Text("Switch View") }, // Placeholder
                onClick = {
                    /* TODO: navController.navigate(...) */
                    showContextMenu = false
                }
            )
            DropdownMenuItem(
                text = { Text("Table of Contents") }, // Placeholder
                onClick = {
                    /* TODO: navController.navigate(...) */
                    showContextMenu = false
                }
            )
        }
    }
}

@Composable
private fun PageControls(
    onPrev: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = onPrev, modifier = Modifier.weight(1f)) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(CoreResources.string.reading_btn_prev)
            )
        }
        IconButton(onClick = onNext, modifier = Modifier.weight(1f)) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = stringResource(CoreResources.string.reading_btn_next)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReadingScreenPreview() {
    MaterialTheme {
        // This preview shows the "no book open" state.
        ReadingScreen(
            workId = null,
            navController = NavController(LocalContext.current)
        )
    }
}