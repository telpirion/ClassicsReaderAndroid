package com.telpirion.compose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.ericmschmidt.classicsreader.data.Library
import com.ericmschmidt.classicsreader.data.WorkInfo
import com.ericmschmidt.classicsreader.ui.components.LibraryPreviewProvider
import com.telpirion.compose.ui.theme.Purple40
import com.telpirion.compose.ui.theme.PurpleGrey80


@Preview(showBackground = true)
@Composable
fun DetailsPanePreview(
    @PreviewParameter(LibraryPreviewProvider::class) library: Library
) {
    val works = library.works
    val selectedItem = SelectedItem(0)
    DetailsPane(item = selectedItem, works = works, onReadClick = {})
}

@Composable
fun BoldedText(boldText: String, normalText: String) {
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(boldText)
            }
            append(": ")
            append(normalText)
        }
    )
}

@Composable
fun DetailsPane(
    item: SelectedItem,
    works: Array<WorkInfo>,
    onReadClick: (String) -> Unit
) {
    val workInfo = works.getOrNull(item.id)

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardColors(
            containerColor = PurpleGrey80,
            contentColor = lightColorScheme().onSurface,
            disabledContainerColor = lightColorScheme().onSurface.copy(alpha = 0.12f),
            disabledContentColor = lightColorScheme().onSurface.copy(alpha = 0.38f)
        )
    ) {
        if (workInfo != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp), // Inner padding for the content
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        painter = painterResource(id = workInfo.image as Int),
                        contentDescription = "${workInfo.title} cover art",
                        modifier = Modifier.size(120.dp)
                    )
                    Column(
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        // 2. Title
                        Text(
                            text = workInfo.title as String,
                            style = MaterialTheme.typography.headlineSmall
                        )

                        Spacer(Modifier.height(8.dp))

                        // 3. Author
                        Text(
                            text = workInfo.author as String,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                }

                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = { onReadClick(workInfo.id as String) },
                    modifier = Modifier
                        .width(120.dp),
                    colors = buttonColors(
                        containerColor = Purple40,
                        contentColor = Color.White
                    )
                ) {
                    Text("Read")
                }
                Spacer(Modifier.height(24.dp))
                HorizontalDivider(
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.height(24.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    BoldedText("Translator",
                        workInfo.translator as String)
                    BoldedText("Editor",
                        workInfo.editor as String)
                    BoldedText("Description",
                        workInfo.description as String)
                }
            }
        } else {
            // A placeholder view for when no item is selected.
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Select a work from the list.")
            }
        }
    }
}