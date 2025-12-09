package com.ericmschmidt.classicsreader.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class BookTest {

    private lateinit var book: Book

    @Before
    fun setUp() {
        book = Book(1)
        book.addLines("line 1")
        book.addLines("line 2")
        book.addLines("line 3")
    }

    @Test
    fun addLines() {
        val initialLineCount = book.getLineCount()
        book.addLines("line 4")
        assertEquals(initialLineCount + 1, book.getLineCount())
    }

    @Test
    fun getLine() {
        assertEquals("line 1", book.getLine(0))
        assertEquals("line 2", book.getLine(1))
        assertEquals("line 3", book.getLine(2))
    }

    @Test
    fun getLine_invalidPosition_returnsNull() {
        assertNull(book.getLine(3))
        assertNull(book.getLine(10))
    }

    @Test
    fun getLine_negativePosition_returnsFirstLine() {
        assertEquals("line 1", book.getLine(-1))
    }

    @Test
    fun getLines() {
        assertEquals("line 1\nline 2\n", book.getLines(0, 2))
        assertEquals("line 2\nline 3\n", book.getLines(1, 2))
    }

    @Test
    fun getLines_offsetExceedsSize_returnsUpToEnd() {
        assertEquals("line 1\nline 2\nline 3\n", book.getLines(0, 10))
    }

    @Test
    fun getLines_negativePosition_startsFromBeginning() {
        assertEquals("line 1\nline 2\n", book.getLines(-1, 2))
    }

    @Test
    fun getLines_empty() {
        val emptyBook = Book(2)
        assertEquals("", emptyBook.getLines(0, 1))
    }

    @Test
    fun getLineCount() {
        assertEquals(3, book.getLineCount())
    }
}