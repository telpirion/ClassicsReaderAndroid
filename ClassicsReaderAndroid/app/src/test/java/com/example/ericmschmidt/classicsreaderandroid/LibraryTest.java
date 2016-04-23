package com.example.ericmschmidt.classicsreaderandroid;

import com.example.ericmschmidt.classicsreaderandroid.datamodel.Library;
import com.example.ericmschmidt.classicsreaderandroid.datamodel.WorkInfo;

import  org.junit.Test;
import static org.junit.Assert.*;

public class LibraryTest {

    @Test
    public void shouldReturnArrayOfWorkTitles() {
        Library lib = new Library();
        String[] works = lib.getWorkList();

        assertNotNull(works);
        assertTrue(works.length > 0);
    }

    @Test
    public void shouldGetArrayofWorkInfoObjects() {
        Library lib = new Library();
        WorkInfo[] works = lib.getWorks();

        assertNotNull(works);
        assertTrue(works.length > 0);
    }

    @Test
    public void shouldGetWorkInfoByID() {
        Library lib = new Library();
        WorkInfo work = lib.getWorkInfoByID("Lucretius");

        assertNotNull(work);
    }

}
