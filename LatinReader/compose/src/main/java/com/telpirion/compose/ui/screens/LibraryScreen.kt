package com.telpirion.compose.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ericmschmidt.classicsreader.data.Library
import com.ericmschmidt.classicsreader.data.placeholders.PseudoLibrary
import com.telpirion.compose.ui.DevicePreviews
import com.telpirion.compose.ui.components.ListDetailPane
import com.telpirion.compose.ui.components.Screen
import com.telpirion.compose.ui.theme.ReaderTheme

@Composable
fun LibraryScreen(
    modifier: Modifier = Modifier,
    screen: Screen,
    navController: NavController,
    library: Library? = null
) {
    ListDetailPane(
        modifier = modifier,
        navController = navController,
        screen = screen,
        library = library
    )
}

@DevicePreviews
@Composable
private fun LibraryScreenPreview() {
    ReaderTheme {
        LibraryScreen(
            screen = Screen.Library,
            navController = rememberNavController(),
            library = PseudoLibrary()
        )
    }
}
