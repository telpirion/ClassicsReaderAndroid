package com.ericmschmidt.classicsreader.utilities

import android.util.Log
import com.ericmschmidt.classicsreader.dictionaryXmlString
import com.ericmschmidt.classicsreader.xmlString
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class ResourceHelpersTest {

    @Test
    fun removeExtraneousCharacters() {
        val startingString = "This  is   a    test."
        val expectedString = "This is a test."
        val actualString = removeExtraneousCharacters(startingString)
        assertEquals(expectedString, actualString)
    }
}