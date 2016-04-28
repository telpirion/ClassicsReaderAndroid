package com.ericmschmidt.greekreader.utilities;

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
        "use strict";

        var _charDictionary = {
        "a": "α",
        "a)": "ἀ",
        "a)/": "ἄ",
        "a/": "ά",
        "a)\\": "ἂ",
        "a\\": "ὰ",
        "a(": "ἁ",
        "a(/": "ἅ",
        "a=": "ᾶ",
        "a)=": "ἆ",
        "a|": "ᾳ",
        "a=|": "ᾷ",
        "*)a": "Ἀ",
        "b": "β",
        "g": "γ",
        "d": "δ",
        "e": "ε",
        "e/": "έ",
        "e\\": "ὲ",
        "e)": "ἐ",
        "e)/": "ἔ",
        "e(": "ἑ",
        "e(/": "ἕ",
        "e(\\": "ἓ",
        "*(e": "Ἑ",
        "*)e": "Ἐ",
        "z": "ζ",
        "h": "η",
        "h\\": "ὴ",
        "h/": "ή",
        "h(/": "ἥ",
        "h)/": "ἤ",
        "h)\\": "ἢ",
        "h)/|": "ᾔ",
        "h)": "ἠ",
        "h(": "ἡ",
        "h(=": "ἧ",
        "h)=": "ἦ",
        "h|": "ῃ",
        "h=": "ῆ",
        "h=|": "ῇ",
        "h/|": "ῄ",
        "q": "θ",
        "i": "ι",
        "i/": "ί",
        "i\\": "ὶ",
        "i)": "ἰ",
        "i(/": "ἵ",
        "i(=": "ἷ",
        "i)=": "ἶ",
        "i)/": "ἴ",
        "i(": "ἱ",
        "i=": "ῖ",
        "k": "κ",
        "l": "λ",
        "m": "μ",
        "n": "ν",
        "c": "ξ",
        "o": "ο",
        "o)": "ὀ",
        "o/": "ό",
        "o\\": "ὸ",
        "o(/": "ὅ",
        "o(": "ὁ",
        "p": "π",
        "r": "ρ",
        "s": "σ",
        "t": "τ",
        "u": "υ",
        "u/": "ύ",
        "u\\": "ὺ",
        "u(/": "ὕ",
        "u(": "ὑ",
        "u)": "ὐ",
        "u)/": "ὔ",
        "u=": "ῦ",
        "u)=": "ὖ",
        "f": "φ",
        "x": "χ",
        "y": "ψ",
        "w": "ω",
        "w/": "ώ",
        "w)=": "ὦ",
        "w(": "ὡ",
        "w(/": "ὥ",
        "w=": "ῶ",
        "w=|": "ῷ",
        "w|": "ῳ",
        "w\\": "ὼ",
        "w)/|": "ᾤ",
        ":": ": ",
        ".": "."
        };

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

        WinJS.Namespace.define("GreekConverter", {
        convert: convert,
        convertWord: convertWord
        });

        })();
 */
public class TextConverter {
}
