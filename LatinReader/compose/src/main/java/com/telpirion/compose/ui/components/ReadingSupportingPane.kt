package com.telpirion.compose.ui.components

import android.util.Log
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.runtime.Composable
import com.ericmschmidt.classicsreader.data.TOCEntry
import com.telpirion.compose.ui.screens.SupportingPaneContent
import com.telpirion.compose.viewmodels.ReadingUiState

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ReadingSupportingPane(
    scaffoldNavigator: ThreePaneScaffoldNavigator<Any>,
    supportingPaneContent: SupportingPaneContent,
    currentWorkId: String,
    uiState: ReadingUiState,
    onClose: () -> Unit,
    onTocEntryClick: (TOCEntry) -> Unit,
) {
    if (scaffoldNavigator.scaffoldValue[SupportingPaneScaffoldRole.Supporting] == PaneAdaptedValue.Expanded) {

        Log.i("ReadingSupportingPane", "Expanded: ${supportingPaneContent == SupportingPaneContent.Translation}")

        when (supportingPaneContent) {
            SupportingPaneContent.Translation -> {
                if (currentWorkId.isNotEmpty()) {
                    TranslationPane(
                        onClose = onClose,
                        translationContent = uiState.translationContent,
                        translationInfo = uiState.info
                    )
                }
            }

            SupportingPaneContent.TableOfContents -> {
                TableOfContentsPane(
                    toc = uiState.toc?.toList() ?: emptyList(),
                    onTocEntryClick = onTocEntryClick,
                    onClose = onClose
                )
            }

            else -> {}
        }
    }
}