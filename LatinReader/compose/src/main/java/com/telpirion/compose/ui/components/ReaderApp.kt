package com.telpirion.compose.ui.components

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.telpirion.compose.R

import kotlinx.coroutines.launch

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderApp(
    windowSizeClass: WindowSizeClass
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Determine if the layout is compact. On non-compact layouts, a navigation rail is shown.
    val isCompact = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    // Get the current back stack entry to determine the selected route.
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = isCompact,
        drawerContent = {
            NavDrawerContent(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                ReaderTopAppBar(
                    onMenuClick = { scope.launch { drawerState.open() } }
                )
            },
            bottomBar = {
                // Show bottom navigation bar only on compact screens
                if (isCompact) {
                    ReaderBottomNavigationBar(
                        currentRoute = currentRoute,
                        onNavigate = { route ->
                            navController.navigate(route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
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
                            navController.navigate(route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
                ReaderAppNavHost(
                    navController = navController,
                    modifier = Modifier.weight(1f)
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
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
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
                    var route = "library/someid"
                    when (screen) {
                        Screen.Recent -> {
                            val recentWorkId = "recent_bottom_navigation_bar"
                            route = (screen as Screen.Recent).createRoute(recentWorkId)
                        }
                        Screen.Settings -> {
                            val settingsSource = "settings_bottom_navigation_bar"
                            route = (screen as Screen.Settings).createRoute(settingsSource)
                        }
                        else -> {
                            route = screen.route
                        }
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

