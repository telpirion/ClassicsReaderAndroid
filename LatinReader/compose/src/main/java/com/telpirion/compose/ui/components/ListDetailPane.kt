package com.telpirion.compose.ui.components

import android.annotation.SuppressLint
import android.content.Context
import android.os.Parcelable
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import com.ericmschmidt.classicsreader.MyApplication
import com.ericmschmidt.classicsreader.data.Library
import com.ericmschmidt.classicsreader.data.WorkInfo
import com.ericmschmidt.classicsreader.data.placeholders.PseudoLibrary
import com.ericmschmidt.classicsreader.ui.components.PrettyCardLazyList
import com.ericmschmidt.classicsreader.ui.components.PrettyCardLazyVerticalGrid
import com.ericmschmidt.classicsreader.data.DISPLAY_TYPE
import com.ericmschmidt.classicsreader.data.DISPLAY_TYPE_DEFAULT
import com.telpirion.compose.ui.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@Preview
@Composable
fun NavigableListDetailPaneScaffoldFullPreview(
)  {
    // The preview doesn't have a NavController, so the button won't navigate.
    ListDetailPane(navController = null)
}

@SuppressLint("FlowOperatorInvokedInComposition")
@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ListDetailPane(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    screen: Screen = Screen.Library,
    onDismiss: () -> Unit = {}
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
    val library: Library = if (inPreview) {
        PseudoLibrary()
    } else {
        MyApplication.applicationInstance().library
    }

    val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<SelectedItem>()
    val scope = rememberCoroutineScope()
    val works = library.getWorks()
    val isTranslation = screen == Screen.Translation

    // Get display type from preferences
    val displayTypeKey = stringPreferencesKey(DISPLAY_TYPE)
    val displayTypeValue: Flow<String> = context.dataStore.data
        .map { preferences -> preferences[displayTypeKey] ?: DISPLAY_TYPE_DEFAULT }

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
                if (displayTypeValue.collectAsState(
                        initial = DISPLAY_TYPE_DEFAULT).value == "Grid") {
                    PrettyCardLazyVerticalGrid(
                        library = library,
                        modifier = modifier,
                        selectedWork = selectedWork,
                        onCardClick = onItemClick,
                        isTranslation = isTranslation
                    )
                } else {
                    PrettyCardLazyList(
                        library = library,
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