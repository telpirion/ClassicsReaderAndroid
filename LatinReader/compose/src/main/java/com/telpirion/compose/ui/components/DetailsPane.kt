package com.telpirion.compose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ericmschmidt.classicsreader.data.WorkInfo

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
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        if (workInfo != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp), // Inner padding for the content
                horizontalAlignment = Alignment.Start
            ) {
                // 1. Work Icon
                Image(
                    painter = painterResource(id = workInfo.image),
                    contentDescription = "${workInfo.title} cover art",
                    modifier = Modifier.size(120.dp)
                )

                Spacer(Modifier.height(24.dp))

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

                // Pushes the button to the bottom
                Spacer(Modifier.weight(1f))

                // 4. Read Button
                Button(
                    onClick = { onReadClick(workInfo.id) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Read")
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