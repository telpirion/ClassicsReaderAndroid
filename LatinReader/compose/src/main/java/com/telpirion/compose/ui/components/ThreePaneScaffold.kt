package com.telpirion.compose.ui.components

import android.os.Parcelable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ericmschmidt.classicsreader.datamodel.Library
import com.ericmschmidt.classicsreader.datamodel.placeholders.PseudoManifest
import com.ericmschmidt.classicsreader.ui.components.LibraryPreviewProvider
import com.ericmschmidt.classicsreader.ui.components.PrettyCardLazyList
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@Preview
@Composable
fun NavigableListDetailPaneScaffoldFullPreview(
    @PreviewParameter(LibraryPreviewProvider::class) library: Library
)  {
    NavigableListDetailPaneScaffoldFull(library = library)
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun NavigableListDetailPaneScaffoldFull(
    library: Library,
    modifier: Modifier = Modifier,
) {
    val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<SelectedItem>()
    val scope = rememberCoroutineScope()
    val works = library.getWorks()

    NavigableListDetailPaneScaffold(
        navigator = scaffoldNavigator,
        listPane = {
            AnimatedPane {
                PrettyCardLazyList(
                    library = library,
                    modifier = modifier,
                    onRowClick = { workInfo ->
                        // Find the index of the clicked work to maintain compatibility
                        // with MyItem(id: Int) and MyDetails.
                        val index = works.indexOf(workInfo)
                        if (index != -1) {
                            // Navigate to the detail pane with the passed item
                            scope.launch {
                                scaffoldNavigator.navigateTo(
                                    ListDetailPaneScaffoldRole.Detail,
                                    SelectedItem(index)
                                )
                            }
                        }
                    },
                )
            }
        },
        detailPane = {
            AnimatedPane {
                // Show the detail pane content if selected item is available
                scaffoldNavigator.currentDestination?.contentKey?.let {
                    DetailsPane(item = it)
                }
            }
        },
    )
}

@Composable
fun DetailsPane(item: SelectedItem) {
    val pseudoManifest = PseudoManifest()
    val works = pseudoManifest.collection
    val workInfo = works?.get(item.id)
    Card {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Details page for ${workInfo?.title}",
                fontSize = 24.sp,
            )
            Spacer(Modifier.size(16.dp))
            Text(
                text = "TODO: Add great details here"
            )
        }
    }
}

@Parcelize
class SelectedItem(val id: Int) : Parcelable