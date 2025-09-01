@file:JvmName("ComposeViewAdapter")

package com.ericmschmidt.classicsreader.ui.interop

import androidx.compose.ui.platform.ComposeView

import com.ericmschmidt.classicsreader.activities.MainActivity
import com.ericmschmidt.classicsreader.data.Library
import com.ericmschmidt.classicsreader.ui.components.PrettyCardLazyList
import com.ericmschmidt.classicsreader.ui.components.PrettyCardLazyVerticalGrid
import com.ericmschmidt.classicsreader.ui.fragments.LibraryFragmentDirections

/**
 * Provides an adapter between ComposeView View and PrettyCardLazyList.
 */
fun setContentToLazyList(composeView: ComposeView, library: Library, isTranslation: Boolean, activity: MainActivity) {
    composeView.setContent {
        PrettyCardLazyList(library = library, isTranslation = isTranslation, onRowClick = { selectedWork ->
            navigateToReadingFragment(activity, selectedWork.id, isTranslation)
        })
    }
}

/**
 * Provides an adapter between ComposeView View and PrettyCardLazyVerticalGrid.
 */
fun setContentToLazyGrid(composeView: ComposeView, library: Library, isTranslation: Boolean, activity: MainActivity) {
    composeView.setContent {
        PrettyCardLazyVerticalGrid(library = library, isTranslation = isTranslation, onCardClick = { selectedWork ->
            navigateToReadingFragment(activity, selectedWork.id, isTranslation)
        })
    }
}

fun navigateToReadingFragment(activity: MainActivity, workId: String, isTranslation: Boolean) {
    val navController = activity.navController
    val action: LibraryFragmentDirections.ActionLibraryFragmentToReadingDest =
        LibraryFragmentDirections.actionLibraryFragmentToReadingDest(workId)
    action.setIsTranslation(isTranslation)
    navController.navigate(action)
}