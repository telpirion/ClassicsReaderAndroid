package com.ericmschmidt.greekreader

import com.ericmschmidt.classicsreader.MyApplication
import com.ericmschmidt.greekreader.theme.GreekReaderThemeObj

class GreekReaderApplication: MyApplication(
    libraryName = "com.ericmschmidt.greekreader.data.GreekReaderLibrary",
    themeColors = GreekReaderThemeObj.lightScheme
) {
}