package com.ericmschmidt.classicsreader.data

import com.ericmschmidt.classicsreader.MyApplication
import com.ericmschmidt.classicsreader.utilities.getEntry
import com.ericmschmidt.classicsreader.utilities.getResourceStream
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream

class DictionaryTest {

    private lateinit var libraryMock: Library
    private lateinit var workInfoMock: WorkInfo
    private lateinit var appInstanceMock: MyApplication.ApplicationInstance

    private val dictionaryEntriesXmlString = """
        <?xml version="1.0" encoding="UTF-8"?>
        <entries>
            <entry>A1</entry>
            <entry>a2</entry>
            <entry>abactus</entry>
            <entry>abacus</entry>
            <entry>abalienatio</entry>
            <entry>abalieno</entry>
        </entries>
    """.trimIndent()

    @Before
    fun setUp() {
        // Mock static objects/functions
        mockkObject(MyApplication.Factory)
        mockkStatic("com.ericmschmidt.classicsreader.utilities.ResourceHelpersKt")

        libraryMock = mockk()
        workInfoMock = WorkInfo(id = "dict", location = 123)

        every { libraryMock.getDictionaryInfo() } returns workInfoMock
        every { libraryMock.getDictionaryEntryResource() } returns 456

        appInstanceMock = MyApplication.ApplicationInstance(
            context = mockk(),
            library = libraryMock,
            isNonRomanChar = false,
            textConverter = null
        )

        every { MyApplication.applicationInstance() } returns appInstanceMock

        // Mock getResourceStream for entry headers resource (456)
        every { getResourceStream(456) } answers {
            ByteArrayInputStream(dictionaryEntriesXmlString.toByteArray(Charsets.UTF_8))
        }
        // Mock getResourceStream for dictionary raw content resource (123)
        every { getResourceStream(123) } answers {
            ByteArrayInputStream("".toByteArray(Charsets.UTF_8))
        }

        // Mock the top-level getEntry function to bypass Android's XmlPullParser on JVM unit tests
        every { getEntry(any(), any(), any()) } returns "driven away, driven off"
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testDictionary_init() {
        val dictionary = Dictionary()
        assertNotNull(dictionary)
        assertEquals(6, dictionary.entryCount())
    }

    @Test
    fun testDictionary_isInDictionary() {
        val dictionary = Dictionary()
        assertTrue(dictionary.isInDictionary("abactus"))
        assertTrue(dictionary.isInDictionary("abacus"))
        assertFalse(dictionary.isInDictionary("nonexistent"))
    }

    @Test
    fun testDictionary_getEntry() {
        val dictionary = Dictionary()
        val entry = dictionary.getEntry("abactus")
        assertNotNull(entry)
        assertTrue(entry!!.contains("driven away, driven off"))
    }

    @Test
    fun testDictionary_getEntry_nonexistent_returnsEmptyString() {
        val dictionary = Dictionary()
        val entry = dictionary.getEntry("nonexistent")
        assertEquals("", entry)
    }

    @Test
    fun testDictionary_getRandomEntry() {
        val dictionary = Dictionary()
        val randomEntry = dictionary.getRandomEntry()
        assertNotNull(randomEntry)
        assertTrue(randomEntry!!.isNotEmpty())
    }

    @Test
    fun testDictionary_searchPartial() {
        val dictionary = Dictionary()
        val entries = dictionary.searchForPartialMatch("abac")

        assertTrue(/* condition = */ entries.size == 2)

        val entry = entries.toString()

        assertThat(entry, containsString("abacus"))
        assertThat(entry, containsString("abactus"))
    }
}
