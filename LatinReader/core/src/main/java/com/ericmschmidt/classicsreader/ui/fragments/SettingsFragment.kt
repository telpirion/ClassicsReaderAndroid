package com.ericmschmidt.classicsreader.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.CheckBoxPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.ericmschmidt.classicsreader.R

/**
 * Displays the settings for this app.
 *
 * Source files:
 * - res/xml/settings.xml
 *
 * @author Eric Schmidt
 * @author <a href="https://telpirion.com">...</a>
 * @version 2.0
 * @since 1.2
 */
class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.settings)

        val sharedPrefs = preferenceManager.sharedPreferences
        sharedPrefs?.registerOnSharedPreferenceChangeListener(this)

        sharedPrefs?.let {
            onSharedPreferenceChanged(it, TEXT_SIZE)
            onSharedPreferenceChanged(it, POEM_LINES)
            onSharedPreferenceChanged(it, SHOW_PAGE_CONTROLS)
            onSharedPreferenceChanged(it, DISPLAY_TYPE)
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == null || sharedPreferences == null) {
            return
        }

        val pref = findPreference<Preference>(key)

        // TODO: Make this more elegant ...
        if ((key == TEXT_SIZE) && (pref is ListPreference)) {
            val textSize = sharedPreferences.getString(TEXT_SIZE, TEXT_SIZE_DEFAULT)
            pref.value = textSize
        } else if ((key == POEM_LINES) && (pref is ListPreference)) {
            val poemLines = sharedPreferences.getString(POEM_LINES, POEM_LINES_DEFAULT)
            pref.value = poemLines
        } else if ((key == SHOW_PAGE_CONTROLS) && (pref is CheckBoxPreference)) {
            val showPageControls = sharedPreferences
                .getBoolean(SHOW_PAGE_CONTROLS, SHOW_PAGE_CONTROLS_DEFAULT)
            pref.isChecked = showPageControls
        } else if ((key == DISPLAY_TYPE) && (pref is ListPreference)) {
            val displayType = sharedPreferences.getString(DISPLAY_TYPE, DISPLAY_TYPE_DEFAULT)
            pref.value = displayType
        }
    }

    companion object {
        const val TEXT_SIZE = "textSize"
        const val TEXT_SIZE_DEFAULT = "20"
        const val POEM_LINES = "poemLines"
        const val POEM_LINES_DEFAULT = "5"
        const val SHOW_PAGE_CONTROLS = "showPageControls"
        const val SHOW_PAGE_CONTROLS_DEFAULT = true
        const val DISPLAY_TYPE = "displayType"
        const val DISPLAY_TYPE_DEFAULT = "List"
    }
}
