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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.telpirion.compose.MainActivity
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

// NavOptions builder function
val navOptionsBuilder:  NavOptionsBuilder.() -> Unit = {
    /* This is empty, but previously contained the following AI-generated code:

    popUpTo(navController.graph.findStartDestination().id) {
        saveState = true
    }
    launchSingleTop = true
    restoreState = true
    */
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
            NavigableListDetailPaneScaffoldFull(navController = navController, screen = Screen.Translation)
        }

        composable(
            route = Screen.Recent.route,
            arguments = listOf(
                navArgument("workId") { type = NavType.StringType },
                navArgument("isTranslation") { type = NavType.BoolType })
        ) { backStackEntry ->
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
            val workId = "vocab"
            ReadingScreen(
                workId = workId,
                isTranslation = false,
                navController = navController,
            )
        }

        composable (
            Screen.Dictionary.route
        ) { backStackEntry ->
            ReadingScreen(
                workId = "test",
                isTranslation = false,
                navController = navController,
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