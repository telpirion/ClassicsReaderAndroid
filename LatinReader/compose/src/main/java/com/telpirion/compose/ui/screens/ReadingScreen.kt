@file:Suppress("AssignedValueIsNeverRead")

package com.telpirion.compose.ui.screens

import android.app.Application
import android.content.Context
import android.util.Log
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
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.BackNavigationBehavior
import androidx.compose.material3.adaptive.navigation.NavigableSupportingPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ericmschmidt.classicsreader.data.PreferencesDataStore
import com.ericmschmidt.classicsreader.data.PreferencesState
import com.telpirion.compose.MainActivity
import com.telpirion.compose.ui.components.Screen
import com.telpirion.compose.ui.components.TableOfContentsPane
import com.telpirion.compose.ui.components.TranslationPane
import com.telpirion.compose.viewmodels.DictionaryViewModel
import com.telpirion.compose.viewmodels.ReadingUiState
import com.telpirion.compose.viewmodels.ReadingViewModel
import kotlinx.coroutines.launch
import com.ericmschmidt.classicsreader.R as CoreResources

private sealed class SupportingPaneContent {
    object Hidden : SupportingPaneContent()
    object Translation : SupportingPaneContent()
    object TableOfContents : SupportingPaneContent()
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Suppress("unused")
@Composable
fun ReadingScreen(
    navController: NavController,
    workId: String? = "",
    context: Context = LocalContext.current,
    isTranslation: Boolean = false,
    dictionaryViewModel: DictionaryViewModel = viewModel(
        viewModelStoreOwner = (context as MainActivity)
    ),
    screen: Screen = Screen.Recent
) {

    val context = LocalContext.current
    val preferencesDataStore = remember(context) { PreferencesDataStore(context) }

    val preferences = preferencesDataStore.preferencesFlow().collectAsState(
        initial = PreferencesState()
    ).value

    val recentlyRead = preferences.recentlyRead
    val textSize = preferences.textSize
    val poemLines = preferences.poemLines
    val showPageControls = preferences.showPageControls

    var currentWorkId: String? = workId
    if (screen == Screen.Recent) {
        if (recentlyRead.isNotEmpty()) {
            currentWorkId = recentlyRead
        }
    }

    val textSizeSp = textSize.toFloat()
    var lineSpacing = 30.0f

    // If the font size is too big, then the text gets scrunched.
    if (textSizeSp > 28.0) {
        lineSpacing = 50.0f
    }

    var uiState: ReadingUiState
    var onPageTurn: (Boolean) -> Unit
    var onPrev: () -> Unit
    var onNext: () -> Unit
    val viewModel: ReadingViewModel?

    Log.i("ReadingScreen", "screen: $screen")
    if (screen == Screen.Vocab || screen == Screen.Dictionary){
        val dictionaryUiState = dictionaryViewModel.readingUiState.collectAsStateWithLifecycle()
        uiState = dictionaryUiState.value
        viewModel = null
        onPageTurn = {
            dictionaryViewModel.clearSearch()
        }
        onPrev = {
            dictionaryViewModel.clearSearch()
        }
        onNext = {
            dictionaryViewModel.clearSearch()
        }
    } else {
        if (currentWorkId.isNullOrEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(stringResource(CoreResources.string.reading_no_book_open))
            }
            return
        }

        val readingViewModel: ReadingViewModel = viewModel(
            factory = ReadingViewModel.Factory(
                application = context.applicationContext as Application,
                workId = currentWorkId,
                isTranslation = isTranslation,
                poemLines = poemLines,
                preferencesDataStore = preferencesDataStore
            )
        )
        viewModel = readingViewModel
        val readingUiState by viewModel.uiState.collectAsStateWithLifecycle()
        uiState = readingUiState

        onPageTurn = {
                isNext -> viewModel.goToPage(isNext)
        }

        onPrev = {
            viewModel.goToPage(false)
        }

        onNext = {
            viewModel.goToPage(true)
        }
    }

    val scaffoldNavigator = rememberSupportingPaneScaffoldNavigator()
    val scope = rememberCoroutineScope()
    var supportingPaneContent by remember { mutableStateOf<SupportingPaneContent>(SupportingPaneContent.Hidden) }
    val backNavigationBehavior = BackNavigationBehavior.PopUntilScaffoldValueChange

    NavigableSupportingPaneScaffold(
        navigator = scaffoldNavigator,
        mainPane = {
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
                    onPageTurn = onPageTurn,
                    onSwitchView = {
                        supportingPaneContent = SupportingPaneContent.Translation
                        scope.launch {
                            scaffoldNavigator.navigateTo(SupportingPaneScaffoldRole.Supporting)
                        }
                    },
                    onShowToc = {
                        supportingPaneContent = SupportingPaneContent.TableOfContents
                        scope.launch {
                            scaffoldNavigator.navigateTo(SupportingPaneScaffoldRole.Supporting)
                        }
                    },
                    modifier = Modifier.weight(1f),
                    lineHeight = lineSpacing
                )

                Text(
                    text = uiState.position,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )

                if ((showPageControls)
                    && (screen != Screen.Vocab)
                    && (screen != Screen.Dictionary)) {
                    PageControls(
                        onPrev = onPrev,
                        onNext = onNext,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        },
        supportingPane = {
            if (scaffoldNavigator.scaffoldValue[SupportingPaneScaffoldRole.Supporting] == PaneAdaptedValue.Expanded) {
                when (supportingPaneContent) {
                    SupportingPaneContent.Translation -> {
                        if (currentWorkId != null) {
                            TranslationPane(
                                onClose = {
                                    scope.launch {
                                        supportingPaneContent = SupportingPaneContent.Hidden
                                        scaffoldNavigator.navigateBack(backNavigationBehavior)
                                    }
                                }
                            )
                        }
                    }

                    SupportingPaneContent.TableOfContents -> {
                        if (viewModel != null) {
                            TableOfContentsPane(
                                onTocEntryClick = { index ->
                                    viewModel.goToChapter(index)
                                    scope.launch {
                                        supportingPaneContent = SupportingPaneContent.Hidden
                                        scaffoldNavigator.navigateBack(backNavigationBehavior)
                                    }
                                },
                                onClose = {
                                    scope.launch {
                                        supportingPaneContent = SupportingPaneContent.Hidden
                                        scaffoldNavigator.navigateBack(backNavigationBehavior)
                                    }
                                }
                            )
                        }
                    }

                    SupportingPaneContent.Hidden -> {
                        // Empty pane
                    }
                }
            }
        }
    )
}

@Composable
private fun ReadingContent(
    text: String,
    textSizeSp: Float,
    onPageTurn: (isNext: Boolean) -> Unit,
    onSwitchView: () -> Unit,
    onShowToc: () -> Unit,
    modifier: Modifier = Modifier,
    switchText: String = "Switch View",
    lineHeight: Float = 1.2f,

    ) {
    @Suppress("COMPOSE_APPLIER_CALL_MISMATCH")
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
            lineHeight = lineHeight.sp,
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
                text = { Text(switchText) },
                onClick = {
                    onSwitchView()
                    showContextMenu = false
                }
            )
            DropdownMenuItem(
                text = { Text("Table of Contents") },
                onClick = {
                    onShowToc()
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

        val dictionaryViewModel: DictionaryViewModel = viewModel(
            factory = DictionaryViewModel.Factory
        )
        ReadingScreen(
            workId = null,
            navController = NavController(LocalContext.current),
            dictionaryViewModel = dictionaryViewModel,
            isTranslation = false
        )
    }
}