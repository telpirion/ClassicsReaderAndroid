package com.ericmschmidt.classicsreader.datamodel

import android.support.test.runner.AndroidJUnit4
import com.ericmschmidt.classicsreader.MyApplication
import com.ericmschmidt.classicsreader.utilities.getResourceStream
import com.ericmschmidt.classicsreader.xmlString
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockkObject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WorkTest {

    @RelaxedMockK
    lateinit var myApplicationFactory: MyApplication.Factory

    @RelaxedMockK
    lateinit var myApplication: MyApplication

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun getBook() {
        mockkObject(MyApplication.Factory)
        every { getResourceStream(0) } returns xmlString.byteInputStream()

        val work = Work(0)
    }

    @Test
    fun getBookCount() {

    }
}