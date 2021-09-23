package com.ericmschmidt.latinreader.utilities;

import android.text.TextWatcher;
import android.widget.EditText;

/** Interface for converter that changes one orthography for another.
 *
 * @author Eric Schmidt
 * @author http://telpirion.com
 * @version 1.5
 * @since 1.1
 */
public interface ITextConverter {
    public String convertSourceToTargetCharacters(String source);
    public String convertTargetToSourceCharacters(String target);
    public String getLang();
    public TextWatcher getTextWatcher(EditText editText);
}
