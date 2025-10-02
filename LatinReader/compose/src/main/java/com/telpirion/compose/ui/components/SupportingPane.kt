package com.telpirion.compose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ericmschmidt.classicsreader.data.TOCEntry
import com.telpirion.compose.ui.theme.PurpleGrey80
import com.telpirion.compose.viewmodels.ReadingViewModel

@Composable
fun TableOfContentsPane(
    viewModel: ReadingViewModel = viewModel(),
    onTocEntryClick: (TOCEntry) -> Unit,
    onClose: () -> Unit,
) {
    val readingUiState = viewModel.uiState.collectAsStateWithLifecycle()
    val toc = readingUiState.value.toc

    if (toc == null) {
        return
    }
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardColors(
            containerColor = PurpleGrey80,
            contentColor = lightColorScheme().onSurface,
            disabledContainerColor = lightColorScheme().onSurface.copy(alpha = 0.12f),
            disabledContentColor = lightColorScheme().onSurface.copy(alpha = 0.38f)
        )
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            IconButton(onClick = onClose) {
                Icon(Icons.Default.Close, contentDescription = "Close")
            }
            LazyColumn(modifier = Modifier.weight(1f)) {
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
    poemLines: Int,
    textSizeSp: Float,
    lineHeight: Float,
    onClose: () -> Unit,
    viewModel: ReadingViewModel = viewModel(),
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardColors(
            containerColor = PurpleGrey80,
            contentColor = lightColorScheme().onSurface,
            disabledContainerColor = lightColorScheme().onSurface.copy(alpha = 0.12f),
            disabledContentColor = lightColorScheme().onSurface.copy(alpha = 0.38f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            IconButton(onClick = onClose) {
                Icon(Icons.Default.Close, contentDescription = "Close")
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = uiState.info,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = uiState.content,
                    fontSize = textSizeSp.sp,
                    lineHeight = lineHeight.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}