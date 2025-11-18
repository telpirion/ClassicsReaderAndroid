package com.ericmschmidt.classicsreader.utilities;

import android.text.TextWatcher;
import android.widget.EditText;

/** Interface for converter that changes one orthography for another.
 *
 * @author Eric Schmidt
 * @author <a href="https://telpirion.com">...</a>
 * @version 2.0
 * @since 1.1
 */
public interface ITextConverter_ {
    public String convertSourceToTargetCharacters(String source);
    public String convertTargetToSourceCharacters(String target);
    public String getLang();
    public TextWatcher getTextWatcher(EditText editText);
}
