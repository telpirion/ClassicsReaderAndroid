@file:Suppress("unused", "UnusedVariable")

package com.telpirion.compose.ui.components

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.telpirion.compose.MainActivity
import com.telpirion.compose.ui.screens.LibraryScreen
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
        "recent/{workId}/{isTranslation}",
        CoreResources.string.nav_drawer_recent,
        Icons.Default.Bookmark
    ) {
        fun createRoute(workId: String?, isTranslation: Boolean?) = "recent/$workId/$isTranslation"
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
        fun createRoute() = "vocab/"
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

// NavOptions builder function
fun navOptionsBuilder(navController: NavHostController):  NavOptionsBuilder.() -> Unit {
    return {
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
fun ReaderAppNavHost(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    dictionaryViewModel: DictionaryViewModel = viewModel(
        viewModelStoreOwner = (context as MainActivity)
    ),
    navController: NavHostController,
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Library.route,
        modifier = modifier
    ) {
        composable(
            Screen.Library.route,
        ) { backStackEntry ->
            // Pass the NavController here
            LibraryScreen(modifier = modifier, screen = Screen.Library, navController = navController)
        }

        composable(
            Screen.Translation.route,
        ) { backStackEntry ->
            LibraryScreen(modifier = modifier, navController = navController, screen = Screen.Translation)
        }

        composable(
            route = Screen.Recent.route,
            arguments = listOf(
                navArgument("workId") { type = NavType.StringType },
                navArgument("isTranslation") { type = NavType.BoolType })
        ) { backStackEntry ->

            // TODO: Pull from PreferencesDataStore?
            val workId = backStackEntry.arguments?.getString("workId")
            val isTranslation = backStackEntry.arguments?.getBoolean("isTranslation")
            ReadingScreen(
                workId = workId,
                isTranslation = isTranslation ?: false,
                navController = navController,
            )
        }

        composable (
            Screen.Vocab.route
        ) { backStackEntry ->
            LaunchedEffect(Unit) {
                dictionaryViewModel.getVocab()
            }
            ReadingScreen(
                workId = "",
                isTranslation = false,
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