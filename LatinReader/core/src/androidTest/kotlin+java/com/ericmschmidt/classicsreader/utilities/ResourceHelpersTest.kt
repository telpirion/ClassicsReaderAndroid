package com.ericmschmidt.classicsreader.utilities

import android.support.test.runner.AndroidJUnit4
import com.ericmschmidt.classicsreader.dictionaryXmlString
import com.ericmschmidt.classicsreader.xmlString
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ResourceHelpersTest {

    @Test
    fun getBook() {
        val inputStream = xmlString.byteInputStream()
        val book = getBook(1, inputStream)
        assertNotNull(book)
        assertEquals(1, book!!.getLineCount())
        assertEquals("this is a line in the second book", book.getLine(0))
    }

    @Test
    fun getEntry() {
        val inputStream = dictionaryXmlString.byteInputStream()
        val entry = getEntry(inputStream, "abactus", null)
        assertNotNull(entry)
        assertTrue(entry.contains("driven away, driven off"))
    }


    @Test
    fun getBookCount() {
        val inputStream = xmlString.byteInputStream()
        val bookCount = getBookCount(inputStream)
        assertEquals(3, bookCount)
    }
}