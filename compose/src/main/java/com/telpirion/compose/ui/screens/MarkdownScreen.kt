package com.telpirion.compose.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ericmschmidt.classicsreader.ui.fragments.HelpFragment
import com.ericmschmidt.classicsreader.ui.fragments.InfoFragment
import com.mukesh.MarkDown
import com.telpirion.compose.ui.components.Screen

/**
 * Markdown screen for help and info.
 */
@Composable
fun MarkdownScreen(
    modifier: Modifier = Modifier,
    screen: Screen
) {
    var textString: String
    val context = LocalContext.current
    textString = if (screen == Screen.Help) {
        HelpFragment.buildHelpString(context)
    } else {
        InfoFragment.buildInfoString(context)
    }
    MarkDown(
        modifier = modifier.padding(16.dp),
        text = textString
    )
}