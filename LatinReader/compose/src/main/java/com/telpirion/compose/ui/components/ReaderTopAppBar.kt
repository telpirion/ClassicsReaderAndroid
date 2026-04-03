package com.telpirion.compose.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.telpirion.compose.R

data class TopBarAction(val text: String, val onClick: () -> Unit)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderTopAppBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onSearch: () -> Unit,
    onClearSearch: () -> Unit,
    modifier: Modifier = Modifier,
    onMenuClick: (() -> Unit)? = null,
    actions: List<TopBarAction> = emptyList()
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    // A single TopAppBar that always shows the search bar.
    TopAppBar(
        title = {
            Column (
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = searchText,
                    onValueChange = onSearchTextChange,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        onSearch()
                        keyboardController?.hide() // Hide keyboard on search
                    }),
                    placeholder = { Text(stringResource(R.string.search_label)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.width(IntrinsicSize.Max)
                        .align(Alignment.CenterHorizontally)
                        .testTag("SearchField"),
                    singleLine = true,
                    shape = RoundedCornerShape(32.dp), // Makes the TextField pill-shaped
                    colors = TextFieldDefaults.colors(
                        // Set a distinct background color for the TextField
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        // Hide the indicator line for a cleaner look
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                    )
                )
            }
        },
        navigationIcon = {
            if (onMenuClick != null) {
                IconButton(
                    onClick = onMenuClick,
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = stringResource(R.string.cd_open_navigation_drawer)
                    )
                }
            }
        },
        actions = {
            if (searchText.isNotEmpty()) {
                IconButton(onClick = onClearSearch) { // Use the new callback
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.cd_clear_search)
                    )
                }
            }
            if (actions.isNotEmpty()) {
                var showMenu by remember { mutableStateOf(false) }
                Box {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More options"
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        actions.forEach { action ->
                            DropdownMenuItem(
                                text = { Text(action.text) },
                                onClick = {
                                    action.onClick()
                                    showMenu = false
                                }
                            )
                        }
                    }
                }
            }
        },
        // Set the background color for the entire TopAppBar.
        // This will contrast with the TextField's 'surfaceVariant' color.
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = modifier
    )
}