package com.telpirion.classicsreader.core_data.datamodel

import java.util.Formatter
import java.util.Locale

data class TOCEntry(val title: String, val book: Int, val line: Int) {

    override fun toString(): String {
        val sb = StringBuilder()
        val formatter = Formatter(sb, Locale.US)
        return formatter.format(
            "Book %d.%d%s",
            (this.book + 1),
            this.line,
            (if (this.title.isEmpty()) "" else ": " + this.title)
        ).toString()
    }
}