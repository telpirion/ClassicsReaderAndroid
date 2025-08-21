@file:JvmName("ComposeViewAdapter")

package com.ericmschmidt.classicsreader.ui.interop

import android.app.Activity
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.NavHostFragment
import com.ericmschmidt.classicsreader.MyApplication
import com.ericmschmidt.classicsreader.R
import com.ericmschmidt.classicsreader.activities.MainActivity
import com.ericmschmidt.classicsreader.datamodel.Library
import com.ericmschmidt.classicsreader.ui.components.PrettyCardLazyList
import com.ericmschmidt.classicsreader.ui.fragments.LibraryFragmentDirections

fun setContentToLazyList(composeView: ComposeView, library: Library, isTranslation: Boolean, activity: MainActivity) {
    composeView.setContent {
        PrettyCardLazyList(library = library, onRowClick = { selectedWork ->
            val supportManager = activity.supportFragmentManager
            val navHostFragment = supportManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController

            val action: LibraryFragmentDirections.ActionLibraryFragmentToReadingDest =
                LibraryFragmentDirections.actionLibraryFragmentToReadingDest(selectedWork.id)
            action.setIsTranslation(isTranslation)
            navController.navigate(action)
        })
    }
}

fun setContentToLazyGrid(composeView: ComposeView, library: Library, isTranslation: Boolean, activity: MainActivity) {
    composeView.setContent {

    }
}