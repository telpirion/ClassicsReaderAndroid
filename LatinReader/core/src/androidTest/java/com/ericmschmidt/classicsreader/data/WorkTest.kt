package com.ericmschmidt.classicsreader.data

import android.support.test.runner.AndroidJUnit4
import com.ericmschmidt.classicsreader.MyApplication
import com.ericmschmidt.classicsreader.utilities.getResourceStream
import com.ericmschmidt.classicsreader.xmlString
import io.mockk.every
import io.mockk.mockkObject
import org.junit.Assert.assertNotNull
import java.io.ByteArrayInputStream
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class WorkTest {

    // This method attempts to create a new ByteArrayInputStream from the resource string
    // for each invocation of this method.
    fun produceInputStream(inputString: String): ByteArrayInputStream {
        val copiedString : String = inputString
        val inputStream = ByteArrayInputStream(copiedString.toByteArray(Charsets.UTF_8))
        return inputStream
    }

    @Test
    fun getBook() {
        mockkObject(MyApplication.Factory)
        every { getResourceStream(0) } returns produceInputStream(xmlString)

        val work = Work(0)
        assertNotNull(work)

        // It seems that you have to reload the input stream before you can get a book :/
        every { getResourceStream(0) } returns produceInputStream(xmlString)
        val book = work.getBook(0)
        assertNotNull(book)
    }

    @Test
    fun getBookCount() {

    }
}