@file:JvmName("ComposeViewAdapter")

package com.ericmschmidt.classicsreader.ui.interop

import androidx.compose.ui.platform.ComposeView
import com.ericmschmidt.classicsreader.datamodel.Library
import com.ericmschmidt.classicsreader.ui.components.PrettyCardLazyList

fun setContentToLazyList(composeView: ComposeView, library: Library) {
    composeView.setContent {
        PrettyCardLazyList(library = library)
    }
}