package com.telpirion.compose.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ericmschmidt.classicsreader.R as CoreResources

@Composable
fun PageControls(
    onPrev: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = onPrev, modifier = Modifier.weight(1f)) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(CoreResources.string.reading_btn_prev)

            )
        }
        IconButton(onClick = onNext, modifier = Modifier.weight(1f)) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = stringResource(CoreResources.string.reading_btn_next)
            )
        }
    }
}