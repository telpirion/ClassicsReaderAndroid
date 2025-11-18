package com.ericmschmidt.latinreader.datamodel;

import com.ericmschmidt.classicsreader.datamodel.Book;
import com.ericmschmidt.classicsreader.datamodel.Work;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.ericmschmidt.classicsreader.utilities.ResourceHelpersKt.getBook;
import static com.ericmschmidt.classicsreader.utilities.ResourceHelpersKt.getResourceStream;
import static com.ericmschmidt.classicsreader.utilities.ResourceHelpersKt.getBookCount;
import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class WorkTest {

    int testResourceID = 12345;

    @Before
    public void setUp() {

        try {
            String sampleString =
                    "<work>" +
                            "<header>" +
                            "</header>" +
                            "<text>" +
                            "<body>" +
                            "<div1 type=\"Book\" n=\"1\">" +
                            "<head>SOME HEADING</head>" +
                            "<p>This is some text</p>" +
                            "</div1>" +
                            "</body>" +
                            "</text>" +
                            "</work>";

            Book testBook = new Book(0);
            testBook.addLines("This is line #1");
            testBook.addLines("This is line #2");
            testBook.addLines("This is line #3");

            InputStream inputStream = new ByteArrayInputStream(sampleString.getBytes());

            when(getResourceStream(testResourceID)).thenReturn(inputStream);
            when(getBookCount(inputStream)).thenReturn(1);
            when(getBook(0, inputStream)).thenReturn(testBook);

        } catch (XmlPullParserException | IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void testGetBook() {
        Work testWork = new Work(testResourceID);
        Book book = testWork.getBook(0);
        assertThat(book).isNotNull();
    }

    @Test
    public void testGetBookCount() {
        Work testWork = new Work(testResourceID);
        int actualNumberOfBook = testWork.getBookCount();
        assertThat(actualNumberOfBook).isEqualTo(1);
    }
}