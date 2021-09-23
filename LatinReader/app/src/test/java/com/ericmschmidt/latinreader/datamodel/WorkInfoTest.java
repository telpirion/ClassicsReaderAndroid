package com.ericmschmidt.latinreader.datamodel;

import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class WorkInfoTest {

    WorkInfo testWorkInfo;

    @Before
    public void setUp() {

        TOCEntry tocEntry = new TOCEntry("Test Entry",
                1, 1);

        testWorkInfo = new WorkInfo.Builder("testID")
                .workType(WorkInfo.WorkType.POEM)
                .author("Test Author")
                .englishAuthor("Test English Author")
                .englishTitle("Test English Title")
                .title("Test Title")
                .location(2)
                .offset(2, 2)
                .TOCEntry(tocEntry)
                .create();
    }

    @Test
    public void testToStringOverride() {
        String testStringWorkInfo = testWorkInfo.toString();
        assertThat(testStringWorkInfo).contains("Test Author");
    }

    @Test
    public void testTOCEntries() {
        int actualTOCEntriesCount = testWorkInfo.getTOCCount();
        assertThat(actualTOCEntriesCount).isEqualTo(1);

        TOCEntry actualTOCEntry = testWorkInfo.getTocEntries()[0];
        assertThat(actualTOCEntry.getTitle()).contains("Test Entry");
    }
}
