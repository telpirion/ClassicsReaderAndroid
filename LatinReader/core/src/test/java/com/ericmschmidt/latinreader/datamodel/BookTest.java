package com.ericmschmidt.latinreader.datamodel;

import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

import com.ericmschmidt.classicsreader.datamodel.Book;

public class BookTest {

    Book testBook;
    String expectedLine1 = "This is line 1";
    String expectedLine2 = "This is line 2";
    String expectedLine3 = "This is line 3";

    @Before
    public void setUp() {
        testBook = new Book(1234);
        testBook.addLines(expectedLine1);
        testBook.addLines(expectedLine2);
        testBook.addLines(expectedLine3);
    }


    @Test
    public void testGetLine() {

        // Verify that it gets the correct line.
        String actualResult1 = testBook.getLine(0);
        assertThat(actualResult1).contains(expectedLine1);

        // Verify that passing in a negative number returns the first line.
        String actualResult2 = testBook.getLine(-1);
        assertThat(actualResult2).contains(expectedLine1);

        // Verify that passing in a number larger than the number
        // of lines returns null.
        String actualResult3 = testBook.getLine(5);
        assertThat(actualResult3).isNull();
    }

    @Test
    public void testGetLines() {

        // Verify that it gets the correct lines.
        String actualResult1 = testBook.getLines(0, 2);
        assertThat(actualResult1).contains(expectedLine1);
        assertThat(actualResult1).contains(expectedLine2);

        // Verify that passing in a negative number as the position returns
        // lines from the beginning of the book.
        String actualResult2 = testBook.getLines(-2, 2);
        assertThat(actualResult2).contains(expectedLine1);
        assertThat(actualResult2).contains(expectedLine2);

        // Verify that passing in a number greater than the total lines
        // returns the remaining lines in the book.
        String actualResult3 = testBook.getLines(1, 10);
        assertThat(actualResult3).contains(expectedLine2);
        assertThat(actualResult3).contains(expectedLine3);
    }

}