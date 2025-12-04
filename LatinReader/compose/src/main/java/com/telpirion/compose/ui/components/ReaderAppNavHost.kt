@file:Suppress("unused", "UnusedVariable")

package com.telpirion.compose.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.telpirion.compose.ui.screens.MarkdownScreen
import com.telpirion.compose.ui.screens.ReadingScreen
import com.telpirion.compose.ui.screens.SettingsScreen
import com.telpirion.compose.viewmodels.DictionaryViewModel
import com.ericmschmidt.classicsreader.R as CoreResources

/**
 * Defines the primary navigation destinations in the app.
 */
open class Screen(val route: String, val label: Int, val icon: ImageVector) {
    object Library : Screen(
        "library/",
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
        fun createRoute(workId: String?) = "recent/$workId"
    }
    object Translation : Screen(
        "translation/",
        CoreResources.string.nav_drawer_translations,
        Icons.Default.Description
    ) {
        fun createRoute() = "translation/"
    }
    object Vocab : Screen(
        "vocab/",
        CoreResources.string.nav_drawer_vocab,
        Icons.Default.School
    ) {
        fun createRoute(source: String) = "vocab/$source"
    }

    object Dictionary : Screen(
        "dictionary/",
        CoreResources.string.nav_drawer_dictionary,
        Icons.Default.Description
    ) {
        fun createRoute() = "dictionary/"
    }

    object Settings : Screen(
        "settings/",
        CoreResources.string.action_settings,
        Icons.Default.Settings
    ) {
        fun createRoute() = "settings/"
    }
    object Help : Screen(
        "help/",
        CoreResources.string.nav_drawer_help,
        Icons.AutoMirrored.Filled.Help
    ) {
        fun createRoute() = "help/"
    }
    object Info : Screen(
        "info/",
        CoreResources.string.nav_drawer_info,
        Icons.Default.Info
    ) {
        fun createRoute() = "info/"
    }
}

@Composable
fun ReaderAppNavHost(
    dictionaryViewModel: DictionaryViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val textSize by rememberSaveable { mutableFloatStateOf(20f) }
    val poemLines by rememberSaveable { mutableIntStateOf(5) }
    val showPageControls by rememberSaveable { mutableStateOf(true) }

    NavHost(
        navController = navController,
        startDestination = Screen.Library.route,
        modifier = modifier
    ) {
        composable(
            Screen.Library.route,
        ) { backStackEntry ->
            // Pass the NavController here
            NavigableListDetailPaneScaffoldFull(navController = navController)
        }

        composable(
            Screen.Translation.route,
        ) { backStackEntry ->
            // And here as well
            NavigableListDetailPaneScaffoldFull(navController = navController)
        }

        composable(
            route = Screen.Recent.route,
            arguments = listOf(navArgument("workId") { type = NavType.StringType })
        ) { backStackEntry ->
            val workId = backStackEntry.arguments?.getString("workId")

            ReadingScreen(
                workId = workId,
                isTranslation = false,
                dictionaryViewModel,
                navController = navController,
            )
        }

        composable (
            Screen.Vocab.route
        ) { backStackEntry ->
            val workId = "vocab"
            ReadingScreen(
                workId = workId,
                isTranslation = false,
                dictionaryViewModel = dictionaryViewModel,
                navController = navController,
                screen = Screen.Vocab
            )
        }

        composable (
            Screen.Dictionary.route
        ) { backStackEntry ->
            ReadingScreen(
                workId = "test",
                isTranslation = false,
                dictionaryViewModel,
                navController = navController,
                screen = Screen.Dictionary
            )

        }

        composable (
            Screen.Help.route
        ) { backStackEntry ->
            MarkdownScreen(screen = Screen.Help)
        }

        composable (
            Screen.Info.route
        ) { backStackEntry ->
            MarkdownScreen(screen = Screen.Info)
        }

        composable(
            Screen.Settings.route,
        ) { backStackEntry ->
            SettingsScreen()
        }
    }
}