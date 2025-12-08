package com.ericmschmidt.classicsreader.datamodel

import java.util.Formatter
import java.util.Locale

/** An entry within a table of contents.
 * @author Eric Schmidt
 * @author <a href="https://telpirion.com">...</a>
 * @version 2.0
 * @since 1.4
 */
class TOCEntry(val title: String, val book: Int, val line: Int)  {
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