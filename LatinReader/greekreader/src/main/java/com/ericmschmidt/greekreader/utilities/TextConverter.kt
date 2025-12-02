package com.ericmschmidt.greekreader.utilities

import android.text.Editable
import android.text.TextWatcher
import android.util.JsonReader
import android.widget.EditText
import com.ericmschmidt.classicsreader.logError
import com.ericmschmidt.classicsreader.utilities.ITextConverter
import com.ericmschmidt.classicsreader.utilities.getResourceStream
import com.ericmschmidt.greekreader.R
import java.io.IOException
import java.io.InputStreamReader

/**
 * A Latin character to Greek polytonic converter.
 * Copyright 2015, Eric Schmidt. All rights reserved.
 * <br/>
 * <a href="http://www.unicode.org/charts/PDF/U1F00.pdf">...</a>
 * <a href="http://www.fileformat.info/search/google.htm">...</a>
 * <br/>
 * Last updated: 2025-12-02
 * <br/>
 * @author Eric Schmidt
 * @author <a href="https://telpirion.com">...</a>
 * @version 3.0
 * @since 0.1
 */
class TextConverter: ITextConverter {

    private var _characterHash: HashMap<String?, String?>? = null
    private var _reverseCharacterHash: HashMap<String?, String?>? = null

    val _lang = "greek"

    val DIACRITICALS: String = ")(\\/=|+"
    val PUNCTUATION: String = ":;'.\n"

    init {
        try {
            _characterHash = HashMap<String?, String?>()
            _reverseCharacterHash = HashMap<String?, String?>()
            initCharacterHash()
        } catch (ex: Exception) {
            logError(this.javaClass, ex.message)
        }
    }

    /**
     * Get a string indicating the target language of the text converter,
     * in this case Greek polyonic.
     * @return
     */
    override fun getLang(): String {
        return this._lang
    }

    /**
     * Converts Latin characters to Greek polytonic characters.
     * @param source a string of Latin characters to convert.
     * @return String Greek polytonic characters, UTF-8
     */
    override fun convertSourceToTargetCharacters(source: String?): String {
        val paraArray = source?.split("\n".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()
        var convertedString = ""

        if (paraArray != null) {
            for (para in paraArray) {
                val wordArray = para.split(" ".toRegex())
                    .dropLastWhile { it.isEmpty() }.toTypedArray()

                for (word in wordArray) {
                    convertedString += convertWord(word) + " "
                }
                convertedString += "\n"
            }
        }
        return convertedString
    }

    /**
     * Converts Greek polytonic characters to Latin characters.
     * @param target a string of UTF-8 Greek characters to convert.
     * @return String Latin characters.
     */
    override fun convertTargetToSourceCharacters(target: String?): String {
        val wordArray = target?.split(" ".toRegex())
            ?.dropLastWhile { it.isEmpty() }
            ?.toTypedArray()
        var convertedString = ""

        if (wordArray != null) {
            for (word in wordArray) convertedString += revertWord(word)
        }

        return convertedString
    }

    override fun getTextWatcher(editText: EditText?): TextWatcher {
        return object : TextWatcher {
            private var isCanceled = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!isCanceled) {
                    isCanceled = true
                    convertText(editText)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                isCanceled = false
            }
        }
    }

    // Converts the JSON resource into a HashMap.
    @Throws(IOException::class)
    private fun initCharacterHash() {
        val stream = getResourceStream(R.raw.latin_greek_text_conversion)
        val reader = JsonReader(InputStreamReader(stream))

        reader.beginObject()

        while (reader.hasNext()) {
            val entry = reader.nextName()
            val value = reader.nextString()

            this._characterHash!!.put(entry, value)
            this._reverseCharacterHash!!.put(value, entry)
        }

        // Add final sigma character to reverseCharacterHash
        this._reverseCharacterHash!!.put("ς", "s")
    }

    // Converts the characters typed into the EditText box to another orthography.
    private fun convertText(editText: EditText?) {
        val searchString = editText?.getText().toString()

        var formattedString: String? = convertTargetToSourceCharacters(searchString)
        formattedString = convertSourceToTargetCharacters(formattedString)

        formattedString = formattedString.replace("\n", "").replace(" ", "")
        editText?.setText(formattedString)
        editText?.setSelection(formattedString.length)
    }

    // Converts a single word of Latin characters into
    //a Greek polytonic-formatted Greek word (string).
    private fun convertWord(word: String): String {
        var convertedWord = ""
        var holdVowelChar = ""
        var holdCapital = ""

        for (i in 0..<word.length) {
            val currChar = word.get(i).toString()
            if (this._characterHash!!.containsKey(currChar)) {
                if (!holdCapital.isEmpty()) {
                    holdCapital += currChar
                    convertedWord += resolveDiacriticals(holdVowelChar)
                    convertedWord += resolveDiacriticals(holdCapital)
                } else {
                    convertedWord += resolveDiacriticals(holdVowelChar)
                    convertedWord += resolveDiacriticals(holdCapital)
                    convertedWord += this._characterHash!!.get(currChar)
                }
                holdCapital = ""
                holdVowelChar = ""
            } else if (currChar.toString() == ("*")) {
                holdCapital += "*"
            } else if (isDiacritical(currChar)) {
                // If this is a diacritical, build the diacritical and vowel.
                // A vowel can have two or three diacriticals (a breathing mark, accent, iota subscript),
                // most will only have one.

                if (!holdCapital.isEmpty()) {
                    holdCapital += currChar
                } else if (!holdVowelChar.isEmpty()) {
                    holdVowelChar += currChar
                } else {
                    holdVowelChar += word.get(i - 1).toString() + currChar
                    convertedWord = convertedWord.substring(0, convertedWord.length - 1)
                }
            } else {
                convertedWord += currChar
            }
        }

        // Resolve any remaining vowel plus diacriticals.
        convertedWord += resolveDiacriticals(holdVowelChar)
        convertedWord += resolveDiacriticals(holdCapital)

        // Replace any final sigmas with the ending sigma.
        if (convertedWord.contains("σ")) {
            convertedWord = convertFinalSigma(convertedWord)
        }

        return convertedWord
    }

    // Convert a word in polytonic-formatted Greek characters
    // to Latin characters with diacritical marks.
    private fun revertWord(word: String): String {
        var revertedWord: String? = ""
        for (i in 0..<word.length) {
            val currChar = word.get(i).toString()
            if (this._reverseCharacterHash!!.containsKey(currChar)) {
                revertedWord += this._reverseCharacterHash!!.get(currChar)
            } else {
                revertedWord += currChar
            }
        }
        return revertedWord!!
    }

    // Convert final sigma.
    private fun convertFinalSigma(convertedWord: String): String {
        val trimmedWord = convertedWord.replace(" ", "")
        val cleaner = StringBuilder(trimmedWord)

        val last = trimmedWord.get(trimmedWord.length - 1)
        val secondToLast = trimmedWord.get(trimmedWord.length - 2)

        if (last == 'σ') {
            cleaner.setCharAt(trimmedWord.length - 1, 'ς')
            return cleaner.toString()
        } else if ((this.PUNCTUATION.indexOf(last) > -1)
            && (secondToLast == 'σ')
        ) {
            cleaner.setCharAt(trimmedWord.length - 2, 'ς')
            return cleaner.toString()
        }

        return convertedWord
    }

    // Determine whether a character is a diacritical.
    private fun isDiacritical(character: String): Boolean {
        return DIACRITICALS.contains(character)
    }

    // Resolve any unresolved vowels + diacriticals.
    private fun resolveDiacriticals(s: String): String {
        var convertedWord: String? = ""

        if (!s.isEmpty() && this._characterHash!!.containsKey(s)) {
            convertedWord += this._characterHash!!.get(s)
        } else if (!s.isEmpty()) {
            convertedWord += s
        }

        return convertedWord!!
    }
}