package com.telpirion.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ericmschmidt.classicsreader.datamodel.Library
import com.ericmschmidt.classicsreader.datamodel.placeholders.PseudoManifest
import com.telpirion.compose.ui.components.NavigableListDetailPaneScaffoldFull
import com.telpirion.compose.ui.theme.LatinReaderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val manifest = PseudoManifest()
        val library = Library(manifest.collection)
        setContent {
            LatinReaderTheme {
                NavigableListDetailPaneScaffoldFull(library = library)
            }
        }
    }
}
