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
import com.ericmschmidt.classicsreader.datamodel.Library
import com.ericmschmidt.classicsreader.datamodel.WorkInfo
import com.ericmschmidt.classicsreader.R

class LibraryPreviewProvider : PreviewParameterProvider<Library> {
    override val values = sequenceOf(
        Library(
            arrayListOf(
                WorkInfo.Builder("test")
                    .author("testAuthor")
                    .title("testTitle")
                    .englishTitle("testTitle")
                    .englishAuthor("testAuthor")
                    .location(1)
                    .image(R.drawable.work_default_1)
                    .build(),
                WorkInfo.Builder("test2")
                    .author("Very very super long never-ending author name")
                    .title("Very very long title that could go on forever")
                    .englishTitle("testTitle")
                    .englishAuthor("testAuthor")
                    .image(R.drawable.work_default_2)
                    .location(1)
                    .build(),
                WorkInfo.Builder("test3")
                    .author("Ξενοφῶν")
                    .title("Ἀνάβασις")
                    .englishTitle("Anabasis")
                    .englishAuthor("Xenophon")
                    .image(R.drawable.work_default_3)
                    .location(1)
                    .build(),
            )
        )
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
            key = { work -> work.id },
        ) {
            PrettyRow(workInfo = it, onClick = onRowClick, isTranslation = isTranslation)
        }
    }
}