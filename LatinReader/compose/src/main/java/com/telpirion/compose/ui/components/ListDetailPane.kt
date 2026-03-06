package com.telpirion.compose.ui.components

import android.content.Context
import android.os.Parcelable
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.navigation.NavController
import com.ericmschmidt.classicsreader.MyApplication
import com.ericmschmidt.classicsreader.data.Library
import com.ericmschmidt.classicsreader.data.PreferencesDataStore
import com.ericmschmidt.classicsreader.data.PreferencesState
import com.ericmschmidt.classicsreader.data.WorkInfo
import com.ericmschmidt.classicsreader.data.placeholders.PseudoLibrary
import com.ericmschmidt.classicsreader.ui.components.PrettyCardLazyList
import com.ericmschmidt.classicsreader.ui.components.PrettyCardLazyVerticalGrid
import com.telpirion.compose.ui.DevicePreviews
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@DevicePreviews
@Composable
fun NavigableListDetailPaneScaffoldFullPreview(
)  {
    // The preview doesn't have a NavController, so the button won't navigate.
    ListDetailPane(navController = null)
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ListDetailPane(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    screen: Screen = Screen.Library,
    onDismiss: () -> Unit = {},
    library: Library? = null
) {

    // Fix for Preview: The application context is not available in previews.
    // To work around this, we can check if we are in a preview and provide
    // a mock library and context.
    val inPreview = LocalInspectionMode.current
    val context: Context = if (inPreview) {
        LocalContext.current
    } else {
        MyApplication.applicationInstance().context
    }
    val resolvedLibrary: Library = library ?: if (inPreview) {
        PseudoLibrary()
    } else {
        MyApplication.applicationInstance().library
    }

    val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<SelectedItem>()
    val scope = rememberCoroutineScope()
    val works = resolvedLibrary.getWorks()
    val isTranslation = screen == Screen.Translation

    // Get display type from preferences
    val preferencesDataStore = remember(context) { PreferencesDataStore(context) }
    val preferences = preferencesDataStore.preferencesFlow().collectAsState(
        initial = PreferencesState()
    ).value
    val displayTypeValue = preferences.displayType

    val onItemClick : (workInfo: WorkInfo) -> Unit = { workInfo ->
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
    }

    NavigableListDetailPaneScaffold(
        navigator = scaffoldNavigator,
        listPane = {
            AnimatedPane {
                val selectedWork = scaffoldNavigator.currentDestination?.contentKey?.id?.let {
                    if (it >= 0) {
                        works[scaffoldNavigator.currentDestination?.contentKey?.id as Int]
                    } else {
                        null
                    }
                }
                if (displayTypeValue == "Grid") {
                    PrettyCardLazyVerticalGrid(
                        library = resolvedLibrary,
                        modifier = modifier,
                        selectedWork = selectedWork,
                        onCardClick = onItemClick,
                        isTranslation = isTranslation
                    )
                } else {
                    PrettyCardLazyList(
                        library = resolvedLibrary,
                        modifier = modifier,
                        selectedWork = selectedWork,
                        onRowClick = onItemClick,
                        isTranslation = isTranslation
                    )
                }
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
                            when (screen) {
                                Screen.Translation -> navController?.navigate(Screen.Recent.createRoute(workId, true))
                                else -> navController?.navigate(Screen.Recent.createRoute(workId, false))
                            }

                        },
                        onDismiss = {
                            scope.launch {
                                scaffoldNavigator.navigateBack()
                                onDismiss()
                            }
                        }
                    )
                }
            }
        },
    )
}
@Parcelize
class SelectedItem(val id: Int) : Parcelable