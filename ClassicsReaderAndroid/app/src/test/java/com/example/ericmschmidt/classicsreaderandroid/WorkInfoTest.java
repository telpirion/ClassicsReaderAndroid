package com.example.ericmschmidt.classicsreaderandroid;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

/**
 * Created by ericmschmidt on 1/6/16.
 */
public class WorkInfoTest {

    @Test
    public void shouldCreateWorkInfoObjectsCorrectly() throws Exception
    {
        WorkInfo work = new WorkInfo("The id", "The Latin Title", "The Latin author", "title", "author", "latin file", "english file");
        assertEquals(work.getId(), "The id");
        assertEquals("The Latin Title", work.getTitle());
        assertEquals("The Latin author", work.getAuthor());
        assertEquals("title", work.getEnglishTitle());
        assertEquals("author", work.getEnglishAuthor());
        assertEquals("latin file", work.getLocation());
        assertEquals("english file", work.getEnglishLocation());

    }

    @Test
    public void shouldGetTheManifestOfWorksInTheApp() throws Exception
    {
        ArrayList<WorkInfo> workInfos = Manifest.getCollection();
        String s = "";

        for (WorkInfo t : workInfos) {
            s += t.toString() + "\n";
        }

        assertEquals(s.contains("Caesar"), true);
        assertEquals(s.contains("Sallust"), true);
        assertEquals(s.contains("Plato"), false);
    }
}
