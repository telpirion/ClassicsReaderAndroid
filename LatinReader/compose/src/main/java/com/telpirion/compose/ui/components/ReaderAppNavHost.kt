package com.telpirion.compose.ui.components

import ReadingScreen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ericmschmidt.classicsreader.R as CoreResources

/**
 * Defines the primary navigation destinations in the app.
 */
open class Screen(val route: String, val label: Int, val icon: ImageVector) {
    object Library : Screen(
        "library",
        CoreResources.string.nav_drawer_library,
        Icons.Default.Book
    ) {
        fun createRoute() = "library/"
    }
    object Recent : Screen(
        "recent/{workId}",
        CoreResources.string.nav_drawer_recent,
        Icons.Default.Bookmark
    ) {
        fun createRoute(workId: String) = "recent/$workId"
    }
    object Translation : Screen(
        "translation/",
        CoreResources.string.nav_drawer_translations,
        Icons.Default.Description
    ) {
        fun createRoute() = "translation/"
    }
    object Vocab : Screen(
        "vocab",
        CoreResources.string.nav_drawer_vocab,
        Icons.Default.School
    ) {
        fun createRoute(source: String) = "vocab/$source"
    }
    object Settings : Screen(
        "settings",
        CoreResources.string.action_settings,
        Icons.Default.Settings
    ) {
        fun createRoute(source: String) = "settings/$source"
    }
    object Help : Screen(
        "help",
        CoreResources.string.nav_drawer_help,
        Icons.AutoMirrored.Filled.Help
    ) {
        fun createRoute(source: String) = "help/$source"
    }
    object Info : Screen(
        "info",
        CoreResources.string.nav_drawer_info,
        Icons.Default.Info
    ) {
        fun createRoute(source: String) = "info/$source"
    }
}

@Composable
fun ReaderAppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Library.route,
        modifier = modifier
    ) {
        composable(
            Screen.Library.route,
        ) { backStackEntry ->
            NavigableListDetailPaneScaffoldFull()
        }

        composable(
            Screen.Translation.route,
        ) { backStackEntry ->
            NavigableListDetailPaneScaffoldFull()
        }

        composable(
            route = Screen.Recent.route,
            arguments = listOf(navArgument("workId") { type = NavType.StringType })
        ) { backStackEntry ->
            val workId = backStackEntry.arguments?.getString("workId")
            if (workId != null) {
                ReadingScreen(workId = workId)
            } else {
                Text("Error: Work ID not found.")
            }
        }

        composable (
            Screen.Vocab.route
        ) { backStackEntry ->
            val workId = "vocab"
            ReadingScreen(workId = workId)
        }

        composable (
            Screen.Help.route
        ) { backStackEntry ->
            val workId = "help"
            ReadingScreen(workId = workId)
        }

        composable (
            Screen.Info.route
        ) { backStackEntry ->
            val workId = "about"
            ReadingScreen(workId = workId)
        }

        composable(
            Screen.Settings.route,
        ) { backStackEntry ->
            SettingsScreen()
        }
    }
}