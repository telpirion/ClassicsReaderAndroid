package com.ericmschmidt.latinreader.fragments;

import android.os.Bundle;
import android.content.SharedPreferences;
import androidx.preference.CheckBoxPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.ericmschmidt.classicsreader.R;

/** Displays the settings for this app.
 *
 *  Source files:
 *  - res/xml/settings.xml
 *
 * @author Eric Schmidt
 * @author http://telpirion.com
 * @version 1.5
 * @since 1.2
 */
public class SettingsFragment extends PreferenceFragmentCompat
    implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String TEXT_SIZE = "textSize";
    public static final String TEXT_SIZE_DEFAULT = "20";
    public static final String POEM_LINES = "poemLines";
    public static final String POEM_LINES_DEFAULT = "5";
    public static final String SHOW_PAGE_CONTROLS = "showPageControls";
    public static final boolean SHOW_PAGE_CONTROLS_DEFAULT = true;

    public void onCreatePreferences(Bundle bundle, String settings) {
        addPreferencesFromResource(R.xml.settings);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sharedPrefs.registerOnSharedPreferenceChangeListener(this);

        onSharedPreferenceChanged(sharedPrefs, TEXT_SIZE);
        onSharedPreferenceChanged(sharedPrefs, POEM_LINES);
        onSharedPreferenceChanged(sharedPrefs, SHOW_PAGE_CONTROLS);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference pref = findPreference(key);

        // TODO: Make this more elegant ...
        if ((key.equals(TEXT_SIZE)) && (pref instanceof ListPreference)) {
            String textSize = sharedPreferences.getString(TEXT_SIZE, TEXT_SIZE_DEFAULT);
            ListPreference listPref = (ListPreference)pref;
            listPref.setValue(textSize);
        } else if ((key.equals(POEM_LINES)) && (pref instanceof ListPreference)) {
            String poemLines = sharedPreferences.getString(POEM_LINES, POEM_LINES_DEFAULT);
            ListPreference listPref = (ListPreference)pref;
            listPref.setValue(poemLines);
        } else if ((key.equals(SHOW_PAGE_CONTROLS)) && (pref instanceof CheckBoxPreference)) {
            boolean showPageControls = sharedPreferences
                .getBoolean(SHOW_PAGE_CONTROLS, SHOW_PAGE_CONTROLS_DEFAULT);
            CheckBoxPreference chxPref = (CheckBoxPreference) pref;
            chxPref.setChecked(showPageControls);
        }
    }
}
