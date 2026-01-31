package com.telpirion.compose.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.telpirion.compose.ui.components.ListDetailPane
import com.telpirion.compose.ui.components.Screen

@Composable
fun LibraryScreen(
    modifier: Modifier = Modifier,
    screen: Screen,
    navController: NavController,
) {
    ListDetailPane(
        modifier = modifier,
        navController = navController,
        screen = screen,
    )
}