package com.telpirion.compose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.ericmschmidt.classicsreader.data.Library
import com.ericmschmidt.classicsreader.data.WorkInfo
import com.ericmschmidt.classicsreader.ui.components.LibraryPreviewProvider
import com.telpirion.compose.ui.DevicePreviews
import com.telpirion.compose.ui.theme.TelpirionGray

@DevicePreviews
@Composable
fun DetailsPanePreview(
    @PreviewParameter(LibraryPreviewProvider::class) library: Library
) {
    val works = library.getWorks()
    val selectedItem = SelectedItem(0)
    DetailsPane(item = selectedItem, works = works, onReadClick = {}, onDismiss = {})
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
    onReadClick: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val workInfo = works.getOrNull(item.id)

    Box(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 32.dp),
            colors = CardColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                disabledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.12f),
                disabledContentColor =  MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
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
                            painter = painterResource(id = workInfo.image),
                            contentDescription = "${workInfo.title} cover art",
                            modifier = Modifier.size(120.dp)
                        )
                        Column(
                            modifier = Modifier.padding(start = 16.dp)
                        ) {
                            // 2. Title
                            Text(
                                text = workInfo.title,
                                style = MaterialTheme.typography.headlineSmall
                            )

                            Spacer(Modifier.height(8.dp))

                            // 3. Author
                            Text(
                                text = workInfo.author,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                    }

                    Spacer(Modifier.height(24.dp))
                    Button(
                        onClick = { onReadClick(workInfo.id) },
                        modifier = Modifier
                            .width(120.dp),
                        colors = buttonColors(
                            containerColor = TelpirionGray,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Read")
                    }
                    Spacer(Modifier.height(24.dp))
                    HorizontalDivider(
                        thickness = 2.dp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(Modifier.height(24.dp))
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        BoldedText("Translator",
                            workInfo.translator
                        )
                        BoldedText("Editor",
                            workInfo.editor
                        )
                        BoldedText("Description",
                            workInfo.description
                        )
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
        IconButton(
            onClick = onDismiss,
            modifier = Modifier.align(Alignment.TopEnd).padding(16.dp)
        ) {
            Icon(Icons.Default.Close, contentDescription = "Close")
        }
    }
}