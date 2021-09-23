package com.ericmschmidt.latinreader.fragments;

import android.os.Bundle;
import android.content.SharedPreferences;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.ericmschmidt.classicsreader.R;

public class SettingsFragment extends PreferenceFragmentCompat
implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String TEXT_SIZE = "textSize";
    public static final String TEXT_SIZE_DEFAULT = "20";
    public static final String POEM_LINES = "poemLines";
    public static final String POEM_LINES_DEFAULT = "5";


    public void onCreatePreferences(Bundle bundle, String settings) {
        addPreferencesFromResource(R.xml.settings);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sharedPrefs.registerOnSharedPreferenceChangeListener(this);

        onSharedPreferenceChanged(sharedPrefs, TEXT_SIZE);
        onSharedPreferenceChanged(sharedPrefs, POEM_LINES);
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
        }
    }
}
