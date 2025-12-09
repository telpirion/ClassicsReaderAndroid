package com.ericmschmidt.classicsreader.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ericmschmidt.classicsreader.R
import com.ericmschmidt.classicsreader.data.WorkInfo

// Use this class for previewing WorkInfo & Card objects
class WorkInfoPreviewProvider : PreviewParameterProvider<WorkInfo> {
    override val values = sequenceOf(
        WorkInfo.Builder("test")
            .author("testAuthor")
            .title("testTitle")
            .englishTitle("testTitle")
            .englishAuthor("testAuthor")
            .location(1)
            .englishLocation(1)
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
            .build()
    )
}

@Composable
fun PrettyCard(
    workInfo: WorkInfo,
    modifier: Modifier = Modifier,
    isTranslation: Boolean = false,
    onCardClick: (WorkInfo) -> Unit = {}
) {
    Card(
        modifier = modifier.size(150.dp, 300.dp)
            .padding(4.dp)
            .clickable(onClick = { onCardClick(workInfo) }),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        ),
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            Box {
                Image(
                    modifier = Modifier.requiredSize(width = 130.dp, height = 130.dp),
                    painter = painterResource(workInfo.image as Int),
                    contentDescription = "test image",
                )
            }
        }
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                fontWeight = FontWeight.Bold,
                text = if (!isTranslation) workInfo.title else workInfo.englishTitle,
                textAlign = TextAlign.Left
            )
            Text(
                text = if (!isTranslation) workInfo.author else workInfo.englishAuthor,
                textAlign = TextAlign.Left
            )
        }
    }
}

@Composable
fun PrettyRow(
    workInfo: WorkInfo,
    modifier: Modifier = Modifier,
    isTranslation: Boolean = false,
    onClick: (WorkInfo) -> Unit = {}
) {
    Card(
        modifier = modifier.fillMaxWidth()
            .padding(4.dp)
            .clickable { onClick(workInfo) },
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        ),
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {

            Box {
                Image(
                    modifier = Modifier.requiredSize(width = 100.dp, height = 100.dp),
                    painter = painterResource(workInfo.image as Int),
                    contentDescription = workInfo.title,
                )
            }
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    text = if (!isTranslation) workInfo.title else workInfo.englishTitle,
                    textAlign = TextAlign.Left
                )
                Text(
                    fontSize = 16.sp,
                    text = if(!isTranslation) workInfo.author else workInfo.englishAuthor,
                    textAlign = TextAlign.Left
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrettyRowPreview(
    @PreviewParameter(WorkInfoPreviewProvider::class) workInfo : WorkInfo
) {
    PrettyRow(workInfo)
}

@Preview(showBackground = true)
@Composable
fun PrettyCardPreview(
    @PreviewParameter(WorkInfoPreviewProvider::class) workInfo : WorkInfo
) {
    PrettyCard(workInfo)
}