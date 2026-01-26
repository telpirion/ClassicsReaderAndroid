package com.telpirion.compose.ui.components

import android.util.Log
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.runtime.Composable
import com.ericmschmidt.classicsreader.data.TOCEntry
import com.telpirion.compose.ui.screens.SupportingPaneContent

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ReadingSupportingPane(
    scaffoldNavigator: ThreePaneScaffoldNavigator<Any>,
    supportingPaneContent: SupportingPaneContent,
    currentWorkId: String,
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
                    )
                }
            }

            SupportingPaneContent.TableOfContents -> {
                TableOfContentsPane(
                    onTocEntryClick = onTocEntryClick,
                    onClose = onClose
                )
            }

            else -> {}
        }
    }
}