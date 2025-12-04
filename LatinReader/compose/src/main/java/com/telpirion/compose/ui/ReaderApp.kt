package com.telpirion.compose.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.telpirion.compose.R
import com.telpirion.compose.ui.components.ReaderAppNavHost
import com.telpirion.compose.ui.components.ReaderTopAppBar
import com.telpirion.compose.ui.components.Screen
import com.telpirion.compose.ui.components.navOptionsBuilder
import kotlinx.coroutines.launch
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ericmschmidt.classicsreader.data.RECENTLY_READ
import com.telpirion.compose.viewmodels.DictionaryViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val bottomNavigationItems = listOf(
    Screen.Library,
    Screen.Recent,
    Screen.Settings
)

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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderApp(
    windowSizeClass: WindowSizeClass
)  {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Determine if the layout is compact. On non-compact layouts, a navigation rail is shown.
    val isCompact = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    // Get the current back stack entry to determine the selected route.
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Instantiate the activity-scoped ViewModel
    val dictionaryViewModel: DictionaryViewModel = viewModel(
        factory = DictionaryViewModel.Factory
        // I think I need to set the viewModelStoreOwner here ... ?
    )
    val dictionaryUiState by dictionaryViewModel.uiState.collectAsStateWithLifecycle()

    // Get recently read from preferences
    val context = LocalContext.current
    val recentlyReadKey = stringPreferencesKey(RECENTLY_READ)
    val recentlyRead: Flow<String> = context.dataStore.data
        .map {
                preferences ->
            preferences[recentlyReadKey] ?: ""
        }
    val currentWorkId: String? = recentlyRead.collectAsState(initial = "").value

    val navigationFunc : (String) -> Unit = { route ->
        when (route){
            Screen.Recent.route -> {
                navController.navigate(
                    route = Screen.Recent.createRoute(currentWorkId, false),
                    builder = navOptionsBuilder(navController))
            }
            else -> navController.navigate(route, navOptionsBuilder(navController))
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = isCompact,
        drawerContent = {
            NavDrawerContent(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    navigationFunc(route)
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                ReaderTopAppBar(
                    onMenuClick = { scope.launch { drawerState.open() } },
                    searchText = dictionaryUiState.searchQuery,
                    onSearchTextChange = {
                        text -> dictionaryViewModel.onQueryChange(text) },
                    onSearch = {
                        dictionaryViewModel.search(dictionaryUiState.searchQuery)
                        navController.navigate(Screen.Dictionary.createRoute()) },
                    onClearSearch = dictionaryViewModel::clearSearch
                )
            },
            bottomBar = {
                // Show bottom navigation bar only on compact screens
                if (isCompact) {
                    ReaderBottomNavigationBar(
                        currentRoute = currentRoute,
                        onNavigate = {route ->
                            if (drawerState.isClosed) {
                                navigationFunc(route)
                            }
                        }
                    )
                }
            }
        ) { innerPadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (!isCompact) {
                    ReaderNavigationRail(
                        currentRoute = currentRoute,
                        onNavigate = { route ->
                            if (drawerState.isClosed) {
                                navigationFunc(route)
                            }
                        }
                    )
                }
                ReaderAppNavHost(
                    navController = navController,
                    modifier = Modifier.weight(1f),
                    dictionaryViewModel = dictionaryViewModel
                )
            }
        }
    }
}

@Composable
private fun NavDrawerContent(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    ModalDrawerSheet(modifier = modifier) {
        NavigationHeader(modifier = Modifier.padding(16.dp))
        Spacer(Modifier.height(12.dp))
        navigationItems.forEach { screen ->
            NavigationDrawerItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(stringResource(screen.label)) },
                selected = currentRoute == screen.route,
                onClick = { onNavigate(screen.route) },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
    }
}

@Composable
private fun NavigationHeader(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        // A Row to neatly arrange the logo and app name side-by-side.
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.mipmap.ic_launcher),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Spacer(Modifier.width(16.dp))
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
private fun ReaderBottomNavigationBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    NavigationBar {
        bottomNavigationItems.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(stringResource(screen.label)) },
                selected = currentRoute?.startsWith(screen.route.substringBefore("/")) ?: false,
                onClick = {
                    // The 'when' expression now correctly handles each navigation case.
                    val route = when (screen) {
                        is Screen.Recent -> {
                            // In a real app, you would get the last-read work ID from a ViewModel.
                            val recentWorkId = "" // Placeholder ID
                            screen.createRoute(recentWorkId, false)
                        }
                        is Screen.Library -> screen.createRoute()
                        is Screen.Settings -> screen.createRoute()
                        // Add any other specific cases from bottomNavigationItems here.
                        else -> Screen.Library.createRoute()
                    }
                    onNavigate(route)
                }
            )
        }
    }
}

@Composable
private fun ReaderNavigationRail(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    NavigationRail {
        navigationItems.forEach { screen ->
            NavigationRailItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(stringResource(screen.label)) },
                selected = currentRoute == screen.route,
                onClick = { onNavigate(screen.route) }
            )
        }
    }
}

