package com.ericmschmidt.classicsreader.utilities

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