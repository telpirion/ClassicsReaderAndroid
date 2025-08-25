package com.ericmschmidt.classicsreader.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.ericmschmidt.classicsreader.datamodel.Library
import com.ericmschmidt.classicsreader.datamodel.WorkInfo


@Preview(showBackground = true)
@Composable
fun PrettyCardLazyGridPreview(
    @PreviewParameter(LibraryPreviewProvider::class) library: Library
) {
    PrettyCardLazyVerticalGrid(library = library)
}

@Composable
fun PrettyCardLazyVerticalGrid(
    library: Library,
    modifier: Modifier = Modifier,
    isTranslation: Boolean = false,
    onCardClick : (WorkInfo) -> Unit = {}
) {
    val works = library.works

    LazyVerticalGrid(
        modifier = modifier.padding(8.dp),
        columns = GridCells.Adaptive(minSize = 150.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(
            items = works,
            key = { work -> work.id }
        ) {
            PrettyCard(workInfo = it, onCardClick = onCardClick, isTranslation = isTranslation)
        }
    }
}
