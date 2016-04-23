package com.example.ericmschmidt.classicsreaderandroid;

import com.example.ericmschmidt.classicsreaderandroid.datamodel.Manifest;
import com.example.ericmschmidt.classicsreaderandroid.datamodel.WorkInfo;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class WorkInfoTest {

    @Test
    public void shouldCreateWorkInfoObjectsCorrectly() throws Exception {
        WorkInfo work = new WorkInfo("The id", "The Latin Title", "The Latin author", "title", "author", 1, 2, WorkInfo.WorkType.prose);
        assertEquals(work.getId(), "The id");
        assertEquals("The Latin Title", work.getTitle());
        assertEquals("The Latin author", work.getAuthor());
        assertEquals("title", work.getEnglishTitle());
        assertEquals("author", work.getEnglishAuthor());
        assertEquals(1, work.getLocation());
        assertEquals(2, work.getEnglishLocation());

    }

    @Test
    public void shouldGetTheManifestOfWorksInTheApp() throws Exception {
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
