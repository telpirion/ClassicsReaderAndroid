package com.ericmschmidt.latinreader.datamodel;

import android.util.Log;

import com.ericmschmidt.latinreader.utilities.DictionaryXMLHelper;
import com.ericmschmidt.latinreader.MyApplication;
import com.ericmschmidt.latinreader.utilities.ITextConverter;
import com.ericmschmidt.latinreader.utilities.ResourceHelper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Contains the data and methods for getting dictionary entries.
 */
public class Dictionary  {

    private ArrayList<String>  _entryHeaders;
    private WorkInfo dictionaryInfo;
    private ITextConverter _converter;

    /**
     * Creates a new instance of the dictionary class.
     */
    public Dictionary() {
        this.dictionaryInfo = MyApplication.getManifest().getDictionaryInfo();
        initEntries();
    }

    /**
     * Creates a new instance of the Dictionary class
     * and instantiates a text converter for non-Latin orthographies.
     * @param converter the ITextConverter to use
     */
    public Dictionary(ITextConverter converter) {
        this();
        this._converter = converter;
    }

    /**
     * Gets the number of entries in this dictionary.
     * @return integer
     */
    public int getEntryCount() {
        return this._entryHeaders.size();
    }

    /**
     * Checks whether a specific string is an entry in the dictionary.
     * @param searchEntry the entry to check for.
     * @return boolean
     */
    public boolean isInDictionary(String searchEntry) {
        return this._entryHeaders.contains(searchEntry);
    }

    /**
     * Gets a definition from the dictionary.
     * @param searchEntry the entry to search for.
     * @return String
     */
    public String getEntry(String searchEntry) {
        String definition = null;
        try {
            if (isInDictionary(searchEntry)) {
                InputStream stream = ResourceHelper.getResourceStream(this.dictionaryInfo.getLocation());
                definition = DictionaryXMLHelper.getEntry(stream, searchEntry, _converter);
            }
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            MyApplication.logError(errorMessage);
        }
        return definition;
    }

    /**
     * Gets a randomly selected from the dictionary
     * @return String randomly selected dictionary entry.
     */
    public String getRandomEntry() {
        Random random = new Random();
        int numberOfEntries = this.getEntryCount();

        int randomNumber = random.nextInt(numberOfEntries + 1);
        String randomEntryKey = this._entryHeaders.get(randomNumber);
        return getEntry(randomEntryKey);
    }

    // Gets the number of alphabet chapters in dictionary.
    private void initEntries() {
        try {
            Manifest manifest = MyApplication.getManifest();
            InputStream stream = ResourceHelper.getResourceStream(manifest.getDictionaryEntryResource());
            this._entryHeaders = DictionaryXMLHelper.getEntryHeaders(stream);

        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            MyApplication.logError(errorMessage);
        }
    }
}
