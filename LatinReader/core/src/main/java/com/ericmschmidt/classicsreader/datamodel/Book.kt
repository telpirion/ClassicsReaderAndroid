package com.ericmschmidt.classicsreader.datamodel

/**
 * Represents a sub-division within a larger text.
 * @author Eric Schmidt
 * @author <a href="https://telpirion.com">...</a>
 * @version 2019-11-17
 * @since 1.0
 */
class Book(val id: Int) {

    private val _lines = mutableListOf<String>()

    /**
     * Adds a line to the book.
     * @param line the line to add.
     */
    fun addLines(line: String) {
        _lines.add(line)
    }

    /**
     * Gets a line from this book.
     * @param position the position of the line from the beginning of the book.
     * @return the line.
     */
    fun getLine(position: Int): String? {
        return if (position >= 0 && position < _lines.size) {
            _lines[position]
        } else if (position >= _lines.size) {
            null
        } else {
            _lines.firstOrNull() // Default is to return the first line of the book.
        }
    }

    /**
     * Get a sequence of lines from the book.
     * @param position the position to start the text from.
     * @param offset the number of lines to get.
     * @return String the text from the position indicated.
     */
    fun getLines(position: Int, offset: Int): String {
        if (position < 0) {
            return getLines(0, offset)
        }

        val toIndex = (position + offset).coerceAtMost(_lines.size)
        
        if (position > toIndex) {
            return ""
        }

        val subList = _lines.subList(position, toIndex)
        return if (subList.isEmpty()) {
            ""
        } else {
            subList.joinToString("\n", postfix = "\n")
        }
    }

    /**
     * Gets the number of lines in this book.
     * @return an int of the number of lines.
     */
    fun getLineCount(): Int {
        return _lines.size
    }
}
