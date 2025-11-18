package com.ericmschmidt.classicsreader.utilities

import android.text.TextWatcher
import android.widget.EditText

interface ITextConverter {

    fun convertSourceToTargetCharacters(source: String?): String?
    fun convertTargetToSourceCharacters(target: String?): String?
    fun getLang(): String?
    fun getTextWatcher(editText: EditText?): TextWatcher?
}