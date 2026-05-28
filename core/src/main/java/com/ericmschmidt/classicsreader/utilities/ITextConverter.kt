package com.ericmschmidt.classicsreader.utilities

import android.text.TextWatcher
import android.widget.EditText

/** Interface for converter that changes one orthography for another.
 *
 * @author Eric Schmidt
 * @author <a href="https://telpirion.com">...</a>
 * @version 2.0
 * @since 1.1
 */
interface ITextConverter {

    fun convertSourceToTargetCharacters(source: String?): String?
    fun convertTargetToSourceCharacters(target: String?): String?
    fun getLang(): String?
    fun getTextWatcher(editText: EditText?): TextWatcher?
}