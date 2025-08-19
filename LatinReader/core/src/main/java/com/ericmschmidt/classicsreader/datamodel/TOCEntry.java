package com.ericmschmidt.classicsreader.datamodel;

import java.util.Formatter;
import java.util.Locale;

/** An entry within a table of contents.
 * @author Eric Schmidt
 * @author http://telpirion.com
 * @version 1.5
 * @since 1.4
 */
public class TOCEntry {
    private String title;
    private int book;
    private int line;
    public TOCEntry (String title, int book, int line) {
        this.title = title;
        this.book = book;
        this.line = line;
    }

    public String getTitle() {
        return title;
    }

    public int getBook() {
        return book;
    }

    public int getLine() {
        return line;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb, Locale.US);
        return formatter.format("Book %d.%d%s",
                (this.book + 1),
                this.line,
                (this.title.isEmpty() ? "" : ": " + this.title)).toString();
    }
}
