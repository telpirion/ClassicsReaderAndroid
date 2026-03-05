@file:Suppress("unused")

package com.telpirion.compose.ui


import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldValue
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.material3.adaptive.navigationsuite.rememberNavigationSuiteScaffoldState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ericmschmidt.classicsreader.data.PreferencesDataStore
import com.ericmschmidt.classicsreader.data.PreferencesState
import com.telpirion.compose.ui.components.ReaderAppNavHost
import com.telpirion.compose.ui.components.ReaderTopAppBar
import com.telpirion.compose.ui.components.Screen
import com.telpirion.compose.ui.components.navOptionsBuilder
import com.telpirion.compose.viewmodels.DictionaryUiState
import com.telpirion.compose.viewmodels.DictionaryViewModel
import kotlinx.coroutines.launch


private val navigationItems = listOf(
    Screen.Library,
    Screen.Recent,
    Screen.Translation,
    Screen.Vocab,
    Screen.Help,
    Screen.Info,
    Screen.Settings,
)

// Global declaration for user settings preferences
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@SuppressLint("FlowOperatorInvokedInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderApp(
    windowSizeClass: WindowSizeClass
) {
    val navController = rememberNavController()

    // Get the current back stack entry to determine the selected route.
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Instantiate the activity-scoped ViewModel
    val dictionaryViewModel: DictionaryViewModel = viewModel(
        factory = DictionaryViewModel.Factory
    )
    val dictionaryUiState by dictionaryViewModel.uiState.collectAsStateWithLifecycle()

    // Get recently read from preferences
    val context = LocalContext.current
    val preferencesDataStore = remember(context) { PreferencesDataStore(context) }

    val preferences = preferencesDataStore.preferencesFlow().collectAsState(
        initial = PreferencesState()
    ).value
    val currentWorkId = preferences.recentlyRead

    val navigationFunc: (String) -> Unit = { route ->
        when (route) {
            Screen.Recent.route -> {
                navController.navigate(
                    route = Screen.Recent.createRoute(currentWorkId, false),
                    builder = navOptionsBuilder(navController)
                )
            }

            else -> navController.navigate(route, navOptionsBuilder(navController))
        }
    }

    ReaderAppContent(
        dictionaryUiState = dictionaryUiState,
        onQueryChange = { text -> dictionaryViewModel.onQueryChange(text) },
        onSearch = {
            dictionaryViewModel.search(dictionaryUiState.searchQuery)
            navController.navigate(Screen.Dictionary.createRoute())
        },
        onClearSearch = dictionaryViewModel::clearSearch,
        currentRoute = currentRoute,
        onNavigate = navigationFunc,
        windowSizeClass = windowSizeClass,
        content = { modifier ->
            ReaderAppNavHost(
                navController = navController,
                modifier = modifier,
                dictionaryViewModel = dictionaryViewModel
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderAppContent(
    dictionaryUiState: DictionaryUiState,
    windowSizeClass: WindowSizeClass,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onClearSearch: () -> Unit,
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    content: @Composable (Modifier) -> Unit
) {
    val navSuiteType = when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Expanded -> NavigationSuiteType.WideNavigationRailExpanded
        else -> NavigationSuiteType.WideNavigationRailCollapsed
    }

    val navigationScaffoldState = rememberNavigationSuiteScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val onHamburgerClick: () -> Unit = {
        coroutineScope.launch{
            if (navigationScaffoldState.currentValue == NavigationSuiteScaffoldValue.Visible) {
                navigationScaffoldState.hide()
            } else {
                navigationScaffoldState.show()
            }
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            ReaderTopAppBar(
                searchText = dictionaryUiState.searchQuery,
                onSearchTextChange = onQueryChange,
                onSearch = onSearch,
                onClearSearch = onClearSearch,
                onMenuClick = onHamburgerClick
            )
        },
    ) {
        NavigationSuiteScaffold(
            modifier = Modifier.padding(top=25.dp),
            navigationSuiteItems = {
                navigationItems.forEach { screen ->
                    item(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(stringResource(screen.label)) },
                        selected = currentRoute == screen.route,
                        onClick = { onNavigate(screen.route) }
                    )
                }
            },
            layoutType = navSuiteType,
            state = navigationScaffoldState
        ) {
            Box(Modifier.padding(it)) {
                content(Modifier)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class,
    ExperimentalMaterial3WindowSizeClassApi::class
)
@DevicePreviews
@Composable
fun ReaderAppExpandedPreview() {
    ReaderAppContent(
        dictionaryUiState = DictionaryUiState(),
        onQueryChange = {},
        onSearch = {},
        onClearSearch = {},
        currentRoute = Screen.Library.route,
        onNavigate = {},
        windowSizeClass = WindowSizeClass.calculateFromSize(DpSize(400.dp, 900.dp)),
        content = {
            Box(Modifier.fillMaxSize()) {
                Text("NavHost Content")
            }
        }
    )
}
