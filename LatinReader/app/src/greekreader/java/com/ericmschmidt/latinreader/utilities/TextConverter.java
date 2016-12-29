package com.ericmschmidt.latinreader.utilities;

import com.ericmschmidt.latinreader.MyApplication;
import com.ericmschmidt.latinreader.R;

import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * A Latin character to Greek polytonic converter.
 * Copyright 2015, Eric Schmidt. All rights reserved.
 * Version 2.0.
 *
 * http://www.unicode.org/charts/PDF/U1F00.pdf
 *
 * Revised: 2016-06-01
 */
public class TextConverter implements ITextConverter {

    private HashMap<String, String> _characterHash;

    public TextConverter() {
        try {
            _characterHash = new HashMap<>();
            initCharacterHash();
        } catch (Exception ex) {
            MyApplication.logError(this.getClass(), ex.getMessage());
        }
    }

    @Override
    public String convertSourceToTargetCharacters(String source) {
        String[] paraArray = source.split(" ");
        String convertedString = "";

        for (int i = 0; i < paraArray.length; i++) {
            convertedString += convertWord(paraArray[i]) + " ";
        }
        return convertedString;
    }

    // Converts the JSON resource into a HashMap.
    private void initCharacterHash() throws IOException {
        InputStream stream = ResourceHelper.getResourceStream(R.raw.latin_greek_text_conversion);
        JsonReader reader = new JsonReader(new InputStreamReader(stream));

        reader.beginObject();

        while (reader.hasNext()){
            String entry = reader.nextName();
            String value = reader.nextString();

            this._characterHash.put(entry, value);
        }
    }

    // Lookup characters in the specified string.
    private String convertCharacter(String character) {
        if (this._characterHash.containsKey(character)) {
            return this._characterHash.get(character);
        }
        return character;
    }

    /**
     * Converts a single word of Latin characters into
     * a Greek polytonic-formatted Greek word (string).
     * @param word The word to convert.
     * @return The word as a converted string
     */
    private String convertWord(String word) {

        String convertedWord = "";
        String holdVowelChar = "";
        String holdCapital = "";

        for (int i = 0; i < word.length(); i++) {
            String currChar = Character.toString(word.charAt(i));
            if (this._characterHash.containsKey(currChar)) {
                if (!holdCapital.isEmpty()) {
                    holdCapital += currChar;
                    convertedWord += resolveDiacriticals(holdVowelChar, holdCapital);
                    holdCapital = "";
                    holdVowelChar = "";
                } else {
                    convertedWord += resolveDiacriticals(holdVowelChar, holdCapital);
                    convertedWord += this._characterHash.get(currChar);
                    holdCapital = "";
                    holdVowelChar = "";
                }
            } else if (String.valueOf(currChar).equals(("*"))) {
                holdCapital += "*";
            } else if (isDiacritical(currChar)) {

                // If this is a diacritical, build the diacritical and vowel.
                // A vowel can have two or three diacriticals (a breathing mark, accent, iota subscript),
                // most will only have one.
                if (!holdCapital.isEmpty()) {
                    holdCapital += currChar;
                } else if (!holdVowelChar.isEmpty()) {
                    holdVowelChar += currChar;
                } else {
                    holdVowelChar += word.charAt(i - 1) + currChar;
                    convertedWord = convertedWord.substring(0, convertedWord.length() - 1);
                }
            } else {
                convertedWord += currChar;
            }
        }

        // Resolve any remaining vowel plus diacriticals.
        convertedWord += resolveDiacriticals(holdVowelChar, holdCapital);

        // Replace any final sigmas with the ending sigma.
        // TODO: Handle case where the sigma is followed by a comma, period, semi-colon or colon.
        //if (String.valueOf(convertedWord.charAt(convertedWord.length() - 1)).equals("σ")) {
        //    convertedWord = convertedWord.substring(0, convertedWord.length() - 1) + "ς";
        //}

        Log.d("T", convertedWord);

        return convertedWord;
    }

    // Determine whether a character is a diacritical.
    private boolean isDiacritical(String character) {
        return ")(\\/=|".indexOf(character) > -1;
    }

    // Resolve any unresolved vowels + diacriticals.
    private String resolveDiacriticals(String holdVowel, String holdCapital) {
        String convertedWord = "";

        if (!holdVowel.isEmpty() && (this._characterHash.containsKey(holdVowel))) {
            convertedWord += this._characterHash.get(holdVowel);
        } else if (!holdVowel.isEmpty()) {
            convertedWord += holdVowel;
        }

        if (!holdCapital.isEmpty() && (this._characterHash.containsKey(holdCapital))) {
            convertedWord += this._characterHash.get(holdCapital);
        } else if (!holdCapital.isEmpty()) {
            convertedWord += holdCapital;
        }

        return convertedWord;
    }
}
