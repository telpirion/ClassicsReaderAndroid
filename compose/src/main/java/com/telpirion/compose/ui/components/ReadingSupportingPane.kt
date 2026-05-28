package com.telpirion.compose.ui.components

import android.util.Log
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import com.ericmschmidt.classicsreader.data.TOCEntry
import com.telpirion.compose.ui.screens.SupportingPaneContent

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ReadingSupportingPane(
    supportingPaneContent: SupportingPaneContent,
    currentWorkId: String,
    onClose: () -> Unit,
    onTocEntryClick: (TOCEntry) -> Unit,
) {
    Log.i("ReadingSupportingPane", "Expanded: ${supportingPaneContent == SupportingPaneContent.Translation}")

    when (supportingPaneContent) {
        SupportingPaneContent.Translation -> {
            if (currentWorkId.isNotEmpty()) {
                TranslationPane(
                    onClose = onClose,
                )
            }
        }

        SupportingPaneContent.TableOfContents -> {
            TableOfContentsPane(
                onTocEntryClick = onTocEntryClick,
                onClose = onClose
            )
        }
    }
}