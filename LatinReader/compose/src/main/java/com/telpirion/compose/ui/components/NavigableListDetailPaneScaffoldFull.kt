package com.telpirion.compose.ui.components

import android.os.Parcelable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ericmschmidt.classicsreader.data.Library
import com.ericmschmidt.classicsreader.data.WorkInfo
import com.ericmschmidt.classicsreader.data.placeholders.PseudoManifest
import com.ericmschmidt.classicsreader.ui.components.LibraryPreviewProvider
import com.ericmschmidt.classicsreader.ui.components.PrettyCardLazyList
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@Preview
@Composable
fun NavigableListDetailPaneScaffoldFullPreview(
    @PreviewParameter(LibraryPreviewProvider::class) library: Library
)  {
    // The preview doesn't have a NavController, so the button won't navigate.
    NavigableListDetailPaneScaffoldFull(navController = null)
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun NavigableListDetailPaneScaffoldFull(
    modifier: Modifier = Modifier,
    navController: NavController? = null
) {
    // TODO(telpirion): replace PseudoManifest with ViewModel
    val pseudoManifest = PseudoManifest()
    val library = Library(pseudoManifest.collection)
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
                        // with SelectedItem(id: Int).
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
                // Show the detail pane content if an item is selected
                scaffoldNavigator.currentDestination?.contentKey?.let { selectedItem ->
                    DetailsPane(
                        item = selectedItem,
                        works = works,
                        onReadClick = { workId ->
                            // Use the NavController to navigate to the ReadingScreen
                            navController?.navigate(Screen.Recent.createRoute(workId))
                        }
                    )
                }
            }
        },
    )
}

@Parcelize
class SelectedItem(val id: Int) : Parcelable