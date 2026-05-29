package com.ericmschmidt.latin

import com.ericmschmidt.classicsreader.MyApplication
import com.ericmschmidt.latin.theme.LatinReaderThemeObj

class LatinReaderApplication: MyApplication(
    libraryName = "com.ericmschmidt.latin.data.LatinReaderLibrary",
    themeColors = LatinReaderThemeObj.lightScheme
) {
}