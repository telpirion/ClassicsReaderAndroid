package com.telpirion.compose.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ericmschmidt.classicsreader.ui.fragments.SettingsFragment.POEM_LINES
import com.ericmschmidt.classicsreader.ui.fragments.SettingsFragment.POEM_LINES_DEFAULT
import com.ericmschmidt.classicsreader.ui.fragments.SettingsFragment.SHOW_PAGE_CONTROLS
import com.ericmschmidt.classicsreader.ui.fragments.SettingsFragment.SHOW_PAGE_CONTROLS_DEFAULT
import com.ericmschmidt.classicsreader.ui.fragments.SettingsFragment.TEXT_SIZE
import com.ericmschmidt.classicsreader.ui.fragments.SettingsFragment.TEXT_SIZE_DEFAULT
import com.ericmschmidt.classicsreader.ui.fragments.SettingsFragment.DISPLAY_TYPE
import com.ericmschmidt.classicsreader.ui.fragments.SettingsFragment.DISPLAY_TYPE_DEFAULT
import com.telpirion.compose.ui.dataStore
import com.telpirion.compose.utils.writeBoolSetting
import com.telpirion.compose.utils.writeIntSetting
import com.telpirion.compose.utils.writeStringSetting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import com.ericmschmidt.classicsreader.R as CoreResources

/**
 * The main composable for the settings screen, which displays a list of preferences.
 *
 * In a production app, the state for these settings would typically be hoisted to a
 * ViewModel and persisted using DataStore. For this example, we use `rememberSavable`
 * to maintain state across configuration changes.
 */
@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    // Placeholder data for ListPreferences, as these are defined in XML arrays.
    val textSizeOptions = stringArrayResource(CoreResources.array.pref_array)
    val poemLinesOptions = stringArrayResource(CoreResources.array.poem_lines_array)
    val displayTypeOptions = listOf(
        stringResource(CoreResources.string.action_display_row),
        "Grid"
    )

    // Get poem lines from preferences
    val poemLinesKey = intPreferencesKey(POEM_LINES)
    val poemLines: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[poemLinesKey] ?: (POEM_LINES_DEFAULT).toInt()
        }

    // Get text size from preferences
    val textSizeKey = intPreferencesKey(TEXT_SIZE)
    val textSize: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[textSizeKey] ?: TEXT_SIZE_DEFAULT.toInt()
        }

    // Get show page controls from preferences
    val showPageControlsKey = booleanPreferencesKey(SHOW_PAGE_CONTROLS)
    val showPageControls: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[showPageControlsKey] ?: true }

    // Get display type from preferences
    val displayTypeKey = stringPreferencesKey(DISPLAY_TYPE)
    val displayTypeValue: Flow<String> = context.dataStore.data
        .map { preferences -> preferences[displayTypeKey] ?: DISPLAY_TYPE_DEFAULT }


    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = stringResource(CoreResources.string.action_settings),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        item {
            SettingsListPreference(
                title = stringResource(CoreResources.string.pref_text_size),
                summary = stringResource(CoreResources.string.pref_text_size_summary),
                currentValue = textSize.collectAsState(initial = TEXT_SIZE_DEFAULT.toInt()).value.toString(),
                options = textSizeOptions.toList(),
                onValueChange = {
                    runBlocking {
                        launch { writeIntSetting(context, textSizeKey, newValue = it.toInt()) }
                    }
                }
            )
        }

        item {
            SettingsListPreference(
                title = stringResource(CoreResources.string.pref_poem_lines),
                summary = stringResource(CoreResources.string.pref_poem_lines_summary),
                currentValue = poemLines.collectAsState(initial = CoreResources.string.pref_poem_lines_default).value.toString(),
                options = poemLinesOptions.toList(),
                onValueChange = {
                    runBlocking {
                        launch { writeIntSetting(context, poemLinesKey, newValue = it.toInt()) }
                    }
                }
            )
        }

        item {
            @Suppress("KotlinConstantConditions")
            SettingsSwitchPreference(
                title = stringResource(CoreResources.string.pref_show_page_controls),
                summary = stringResource(CoreResources.string.pref_show_page_controls_summary),
                isChecked = showPageControls.collectAsState(initial = SHOW_PAGE_CONTROLS_DEFAULT).value,
                onCheckedChange = {
                    runBlocking {
                        launch { writeBoolSetting(context, showPageControlsKey, newValue = it) }
                    }
                }
            )
        }

        item {
            SettingsListPreference(
                title = stringResource(CoreResources.string.action_display),
                summary = stringResource(CoreResources.string.action_display_description),
                currentValue = displayTypeValue.collectAsState(initial = DISPLAY_TYPE_DEFAULT).value,
                options = displayTypeOptions,
                onValueChange = {
                    runBlocking {
                        launch { writeStringSetting(context, displayTypeKey, newValue = it) }
                    }
                }
            )
        }
    }
}

/**
 * A composable that replicates the behavior of a ListPreference.
 *
 * @param title The title of the preference.
 * @param summary A short description of the preference.
 * @param currentValue The currently selected value.
 * @param options The list of available options to choose from.
 * @param onValueChange A callback invoked when a new value is selected.
 */
@Composable
private fun SettingsListPreference(
    title: String,
    summary: String,
    currentValue: String,
    options: List<String>,
    onValueChange: (String) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    PreferenceItem(
        title = title,
        summary = summary,
        onClick = { showDialog = true }
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(title) },
            text = {
                Column {
                    options.forEach { option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onValueChange(option)
                                    showDialog = false
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (option == currentValue),
                                onClick = {
                                    onValueChange(option)
                                    showDialog = false
                                }
                            )
                            Text(
                                text = option,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

/**
 * A composable that replicates the behavior of a CheckBoxPreference or SwitchPreference.
 *
 * @param title The title of the preference.
 * @param summary A short description of the preference.
 * @param isChecked The current checked state.
 * @param onCheckedChange A callback invoked when the state is changed.
 */
@Composable
private fun SettingsSwitchPreference(
    title: String,
    summary: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!isChecked) }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
            Text(text = summary, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
private fun PreferenceItem(
    title: String,
    summary: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.bodyLarge)
        Text(text = summary, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    MaterialTheme {
        SettingsScreen()
    }
}