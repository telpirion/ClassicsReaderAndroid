package com.telpirion.classicsreader.core_data.data

/**
 * Represents a sub-division within a larger text.
 * @author Eric Schmidt
 * @author http://telpirion.com
 * @version 2019-11-17
 * @since 1.0
 */
class Book(val id: Int) {
    private val _lines = mutableListOf<String>()
    val lines: List<String> get() = _lines

    /**
     * Adds a line to the book.
     * @param line the line to add.
     */
    fun addLines(line: String) {
        _lines.add(line)
    }

    /**
     * Gets the ID of this book within the work.
     * @return an int specifying this book's position.
     */
    fun getId(): Int {
        return id
    }

    /**
     * Gets a line from this book.
     * @param position the position of the line from the beginning of the book.
     * @return the line.
     */
    fun getLine(position: Int): String? {
        return when {
            position >= 0 && position < _lines.size -> _lines[position]
            position > _lines.size -> null
            else -> _lines.firstOrNull() // Default is to return the first line of the book.
        }
    }

    /**
     * Get a sequence of lines from the book.
     * @param position the position to start the text from.
     * @param offset the number of lines to get.
     * @return String the text from the position indicated.
     */
    fun getLines(position: Int, offset: Int): String {
        val start = if (position < 0) 0 else position
        if (start >= _lines.size) return ""

        val end = if (start + offset < _lines.size) start + offset else _lines.size
        return _lines.subList(start, end).joinToString("\n") + "\n"
    }

    /**
     * Gets the number of lines in this book.
     * @return an int of the number of lines.
     */
    fun getLineCount(): Int {
        return _lines.size
    }
}
