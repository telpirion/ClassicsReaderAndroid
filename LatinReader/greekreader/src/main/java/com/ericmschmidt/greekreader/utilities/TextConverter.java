package com.ericmschmidt.greekreader.utilities;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.JsonReader;
import android.widget.EditText;

import com.ericmschmidt.greekreader.R;
import com.ericmschmidt.latinreader.MyApplication;
import com.ericmschmidt.latinreader.utilities.ITextConverter;
import com.ericmschmidt.latinreader.utilities.ResourceHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * A Latin character to Greek polytonic converter.
 * Copyright 2015, Eric Schmidt. All rights reserved.
 *
 * http://www.unicode.org/charts/PDF/U1F00.pdf
 * http://www.fileformat.info/search/google.htm
 *
 * Last updated: 2017-02-03
 *
 * @author Eric SChmidt
 * @author http://telpirion.com
 * @version 2.0
 */
public class TextConverter implements ITextConverter {

    private HashMap<String, String> _characterHash;
    private HashMap<String, String> _reverseCharacterHash;

    private final String _lang = "greek";

    private static final String DIACRITICALS = ")(\\/=|+";
    private static final String PUNCTUATION = ":;'.\n";

    public TextConverter() {
        try {
            _characterHash = new HashMap<>();
            _reverseCharacterHash = new HashMap<>();
            initCharacterHash();
        } catch (Exception ex) {
            MyApplication.logError(this.getClass(), ex.getMessage());
        }
    }

    /**
     * Get a string indicating the target language of the text converter,
     * in this case Greek polyonic.
     * @return
     */
    @Override
    public String getLang() {
        return this._lang;
    }

    /**
     * Converts Latin characters to Greek polytonic characters.
     * @param source a string of Latin characters to convert.
     * @return String Greek polytonic characters, UTF-8
     */
    @Override
    public String convertSourceToTargetCharacters(String source) {
        String[] paraArray = source.split("\n");
        String convertedString = "";

        for (String para : paraArray) {

            String[] wordArray = para.split(" ");

            for (String word : wordArray) {
                convertedString += convertWord(word) + " ";
            }
            convertedString += "\n";
        }
        return convertedString;
    }

    /**
     * Converts Greek polytonic characters to Latin characters.
     * @param target a string of UTF-8 Greek characters to convert.
     * @return String Latin characters.
     */
    @Override
    public String convertTargetToSourceCharacters(String target) {
        String [] wordArray = target.split(" ");
        String convertedString = "";

        for (String word : wordArray)
            convertedString += revertWord(word);

        return convertedString;
    }

    @Override
    public TextWatcher getTextWatcher(final EditText editText){
        return new TextWatcher() {

            private boolean isCanceled;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isCanceled) {
                    isCanceled = true;
                    convertText(editText);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                isCanceled = false;
            }
        };
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
            this._reverseCharacterHash.put(value, entry);
        }

        // Add final sigma character to reverseCharacterHash
        this._reverseCharacterHash.put("ς", "s");
    }

    // Converts the characters typed into the EditText box to another orthography.
    private void convertText(EditText editText){
        String searchString = editText.getText().toString();

        String formattedString = convertTargetToSourceCharacters(searchString);
        formattedString = convertSourceToTargetCharacters(formattedString);

        formattedString = formattedString.replace("\n", "").replace(" ", "");
        editText.setText(formattedString);
        editText.setSelection(formattedString.length());
    }

    // Converts a single word of Latin characters into
    //a Greek polytonic-formatted Greek word (string).
    private String convertWord(String word) {

        String convertedWord = "";
        String holdVowelChar = "";
        String holdCapital = "";

        for (int i = 0; i < word.length(); i++) {
            String currChar = Character.toString(word.charAt(i));
            if (this._characterHash.containsKey(currChar)) {
                if (!holdCapital.isEmpty()) {
                    holdCapital += currChar;
                    convertedWord += resolveDiacriticals(holdVowelChar);
                    convertedWord += resolveDiacriticals(holdCapital);
                } else {
                    convertedWord += resolveDiacriticals(holdVowelChar);
                    convertedWord += resolveDiacriticals(holdCapital);
                    convertedWord += this._characterHash.get(currChar);
                }
                holdCapital = "";
                holdVowelChar = "";
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
        convertedWord += resolveDiacriticals(holdVowelChar);
        convertedWord += resolveDiacriticals(holdCapital);

        // Replace any final sigmas with the ending sigma.
        if (convertedWord.contains("σ")) {
            convertedWord = convertFinalSigma(convertedWord);
        }

        return convertedWord;
    }

    // Convert a word in polytonic-formatted Greek characters
    // to Latin characters with diacritical marks.
    private String revertWord(String word){
        String revertedWord = "";
        for (int i = 0; i < word.length(); i++) {
            String currChar = Character.toString(word.charAt(i));
            if (this._reverseCharacterHash.containsKey(currChar)) {
                revertedWord += this._reverseCharacterHash.get(currChar);
            } else {
                revertedWord += currChar;
            }
        }
        return revertedWord;
    }

    // Convert final sigma.
    private String convertFinalSigma(String convertedWord){
        String trimmedWord = convertedWord.replace(" ", "");
        StringBuilder cleaner = new StringBuilder(trimmedWord);

        Character last = trimmedWord.charAt(trimmedWord.length() - 1);
        Character secondToLast = trimmedWord.charAt(trimmedWord.length() - 2);

        if (last.equals('σ')) {
            cleaner.setCharAt(trimmedWord.length() - 1, 'ς');
            return cleaner.toString();
        }

        else if ((TextConverter.PUNCTUATION.indexOf(last) > -1)
            && (secondToLast.equals('σ'))){
            cleaner.setCharAt(trimmedWord.length() - 2, 'ς');
            return cleaner.toString();
        }

        return convertedWord;
    }

    // Determine whether a character is a diacritical.
    private boolean isDiacritical(String character) {
        return DIACRITICALS.contains(character);
    }

    // Resolve any unresolved vowels + diacriticals.
    private String resolveDiacriticals(String s) {
        String convertedWord = "";

        if (!s.isEmpty() && this._characterHash.containsKey(s)) {
            convertedWord += this._characterHash.get(s);
        } else if (!s.isEmpty()) {
            convertedWord += s;
        }

        return convertedWord;
    }
}
