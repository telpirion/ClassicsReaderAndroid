package com.ericmschmidt.latinreader.utilities;

import android.util.JsonReader;

import com.ericmschmidt.latinreader.MyApplication;
import com.ericmschmidt.latinreader.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A Latin character to Greek polytonic converter.
 * Copyright 2015, Eric Schmidt. All rights reserved.
 * Version 1.0.
 *
 * https://msdn.microsoft.com/library/dn890630(v=vs.94).aspx
 * http://www.unicode.org/charts/PDF/U1F00.pdf
 *
 * Revised: 2015-03-14
 */
/*(function () {
        // Determines whether a character is a diacritical or not.
        function isDiacrit(char) {
        return [")", "(", "\\", "/", "=", "|"].some(function (i) {
        return i == this;
        }, char);
        }
    /*
     * Converts a complex string (multiple paragraphs) into a
     * polytonic-formatted Greek string.
     * @param {string} str: The string to format.
     * @return {string} The string formatted in Greek polytonic characters.
        function convert(str) {
        var paraArray = str.split(" ");
        var convertedString = "";
        for (var i = 0; i < paraArray.length; i++) {
        convertedString += convertWord(paraArray[i]) + " ";
        }
        return convertedString;
        }
    /*
     * Converts a single word of Latin characters into
     * a Greek polytonic-formatted Greek word (string).
     * @param {string} word: The word to convert.
     * @return {string} The word as a converted string
        function convertWord(word) {
        var convertedWord = "";
        var holdVowelChar = "";
        var holdCapital = ""
        // Resolve any unresolved vowels + diacriticals.
        function resolveDiacriticals() {
        if (holdVowelChar && (_charDictionary[holdVowelChar] != undefined)) {
        convertedWord += _charDictionary[holdVowelChar];
        } else if (holdVowelChar) {
        convertedWord += holdVowelChar;
        }
        holdVowelChar = "";
        if (holdCapital && (_charDictionary[holdCapital] != undefined)) {
        convertedWord += _charDictionary[holdCapital];
        } else if (holdCapital) {
        convertedWord += holdCapital;
        }
        holdCapital = "";
        }
        for (var i = 0; i < word.length; i++) {
        var currChar = word.charAt(i);
        if (_charDictionary[currChar] !== undefined) {
        if (holdCapital) {
        holdCapital += currChar;
        resolveDiacriticals();
        } else {
        resolveDiacriticals();
        convertedWord += _charDictionary[currChar];
        }
        } else if (currChar == "*") {
        holdCapital += "*";
        } else if (isDiacrit(currChar)) {
        // If this is a diacritical, build the diacritical and vowel.
        // A vowel can have two or three diacriticals (a breathing mark, accent, iota subscript),
        // most will only have one.
        if (holdCapital) {
        holdCapital += currChar;
        } else if (holdVowelChar) {
        holdVowelChar += currChar;
        } else {
        holdVowelChar += word.charAt(i - 1) + currChar;
        convertedWord = convertedWord.substr(0, convertedWord.length - 1);
        }
        } else {
        convertedWord += currChar;
        }
        }
        // Resolve any remaining vowel plus diacriticals.
        resolveDiacriticals();
        // Replace any final sigmas with the ending sigma.
        // TODO: Handle case where the sigma is followed by a comma, period, semi-colon or colon.
        if (convertedWord.charAt(convertedWord.length - 1) == "σ") {
        convertedWord = convertedWord.substr(0, convertedWord.length - 1) + "ς";
        }
        return convertedWord;
        }
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

        return null;
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
}
