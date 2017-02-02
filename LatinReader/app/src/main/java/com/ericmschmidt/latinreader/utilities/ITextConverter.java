package com.ericmschmidt.latinreader.utilities;

public interface ITextConverter {
    public String convertSourceToTargetCharacters(String source);
    public String convertTargetToSourceCharacters(String target);
    public String getLang();
}
