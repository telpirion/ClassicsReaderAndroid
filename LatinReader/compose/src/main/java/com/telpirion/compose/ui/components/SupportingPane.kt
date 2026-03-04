@file:Suppress("SpellCheckingInspection")

package com.telpirion.compose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ericmschmidt.classicsreader.data.TOCEntry
import com.telpirion.compose.R
import com.telpirion.compose.viewmodels.ReadingViewModel

const val SUPPORTING_PANE_TAG = "SupportPaneTag"

class TOCEntryParameterProvider : PreviewParameterProvider<List<TOCEntry>> {
    override val values = sequenceOf(
        listOf(
            TOCEntry("Book Primus", 0, 0),
            TOCEntry("Book Secundus", 1, 0),
            TOCEntry("Book Tertius", 2, 0)
        )
    )
}

@Preview
@Composable
fun TableOfContentsPanePreview(
    @PreviewParameter(TOCEntryParameterProvider::class) toc: List<TOCEntry>
) {
    TableOfContentsPane(toc, onTocEntryClick = {}, onClose = {})
}

@Preview
@Composable
fun TranslationPanePreview() {
    TranslationPane(
        onClose = {},
        translationContent = "Content",
        translationInfo = "Author, Title"
    )
}

@Composable
fun TableOfContentsPane(
    onClose: () -> Unit,
    onTocEntryClick: (TOCEntry) -> Unit,
    viewModel: ReadingViewModel = viewModel(),
) {
    val readingUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val toc = readingUiState.toc ?: return
    TableOfContentsPane(toc.toList(), onTocEntryClick, onClose)
}

@Composable
fun TableOfContentsPane(
    toc: List<TOCEntry>,
    onTocEntryClick: (TOCEntry) -> Unit,
    onClose: () -> Unit,
) {
    SupportingPaneTemplate(
        onClose,
        paneTitle = stringResource(R.string.screen_toc)
    ) {
        Column(
            Modifier.padding(horizontal = 16.dp)
        ){
            LazyColumn {
                items(toc) { entry ->
                    Text(
                        text = entry.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onTocEntryClick(entry) }
                            .padding(vertical = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TranslationPane(
    onClose: () -> Unit,
    viewModel: ReadingViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TranslationPane(
        onClose, uiState.translationContent, uiState.info
    )
}

@Composable
fun TranslationPane(
    onClose: () -> Unit,
    translationContent: String,
    translationInfo: String,
) {
    SupportingPaneTemplate(
        onClose,
        paneTitle = translationInfo
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.padding(top = 16.dp))
            Text(translationContent)
        }
    }
}

@Composable
fun SupportingPaneTemplate(
    onClose: () -> Unit,
    paneTitle: String = "",
    content: @Composable () -> Unit,
){
    Box(modifier = Modifier.fillMaxSize()
        .testTag(SUPPORTING_PANE_TAG)) {
        Card(
            modifier = Modifier
                .fillMaxSize(),
            colors = CardColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                disabledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.12f),
                disabledContentColor =  MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
            )
        ) {
            Column(
                Modifier.padding(horizontal = 16.dp)
            ){
                Spacer(modifier = Modifier.padding(top = 30.dp))
                Text(paneTitle)
                Spacer(modifier = Modifier.padding(top = 16.dp))
                HorizontalDivider(
                    thickness = 3.dp,
                    color = MaterialTheme.colorScheme.secondary
                )
                content()
            }
        }
        IconButton(
            onClick = onClose,
            modifier = Modifier.align(Alignment.TopEnd).padding(16.dp)
        ) {
            Icon(
                Icons.Default.Close,
                contentDescription = "Close",
            )
        }
    }
}