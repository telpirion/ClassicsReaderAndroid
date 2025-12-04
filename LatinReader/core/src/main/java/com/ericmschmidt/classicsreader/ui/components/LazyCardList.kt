package com.ericmschmidt.classicsreader.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.ericmschmidt.classicsreader.data.Library
import com.ericmschmidt.classicsreader.data.WorkInfo
import com.ericmschmidt.classicsreader.data.placeholders.PseudoManifest

class LibraryPreviewProvider : PreviewParameterProvider<Library> {
    val pseudoManifest = PseudoManifest()
    override val values = sequenceOf(
        Library(pseudoManifest.collection)
    )
}

@Preview
@Composable
fun PrettyCardLazyListPreview(
    @PreviewParameter(LibraryPreviewProvider::class) library: Library
)  {
    PrettyCardLazyList(library = library)
}


@Composable
fun PrettyCardLazyList(
    library: Library,
    modifier: Modifier = Modifier,
    isTranslation: Boolean = false,
    onRowClick: (WorkInfo) -> Unit = {},
) {
    val works = library.getWorks()
    LazyColumn(
        modifier = modifier.fillMaxWidth()
            .background(Color.White)
    ) {
        items(
            items = works,
            key = { work -> work!!.id },
        ) {
            PrettyRow(workInfo = it!!, onClick = onRowClick, isTranslation = isTranslation)
        }
    }
}