package com.ericmschmidt.latinreader.utilities;

import android.text.TextWatcher;
import android.widget.EditText;

public interface ITextConverter {
    public String convertSourceToTargetCharacters(String source);
    public String convertTargetToSourceCharacters(String target);
    public String getLang();
    public TextWatcher getTextWatcher(EditText editText);
}
