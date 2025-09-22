package com.ericmschmidt.classicsreader.data;

import androidx.annotation.NonNull;

import com.ericmschmidt.classicsreader.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

/**
 * Contains the data for a work contained in the app.
 * <p>
 * It includes the relevant bibliographical info (author, translator)
 * and the locations of the text in the app.
 * <p>
 * Example:
 * <p>
 * id: "CaesarBG",
 * title: "De Bello Gallico", author: "C. Julius Caesar",
 * engTitle: "The Gallic Wars", engAuthor: "Caesar",
 * location: encodeURI(_dataURI + "caes_bg_lat.xml"),
 * translation: encodeURI(_dataURI + "caes_bg_eng.xml")
 * workType: prose || poem
 * <p>
 * @author Eric Schmidt
 * @author <a href="https://telpirion.com">...</a>
 * @version 1.5
 * @since 1.0
 */
public class WorkInfo {

    // TODO(telpirion): Add translator info
    private String _id;
    private String _author;
    private String _title;
    private String _englishTitle;
    private String _englishAuthor;
    private int _location;
    private int _englishLocation;
    private int _workType;
    private ArrayList<TOCEntry> tocEntries;
    private Integer image;

    // Unless specified otherwise, assume a 1-to-1 relationship
    // between line numbers in the English and source language
    private int _offset = 1;
    private int _englishOffset = 1;

    private static final List<Integer> defaultImages = Arrays.asList(
            R.drawable.work_default_1,
            R.drawable.work_default_2,
            R.drawable.work_default_3
    );

    // Private default constructor.
    private WorkInfo() {}

    /**
     * Gets this work's ID.
     *
     * @return ID as string
     */
    public String getId() {
        return this._id;
    }

    /**
     * Gets this work's author in source language.
     *
     * @return author name as string
     */
    public String getAuthor() {
        return this._author;
    }

    /**
     * Gets this work's title in source language.
     *
     * @return title as string
     */
    public String getTitle() {
        return this._title;
    }

    /**
     * Gets this work's title in English.
     *
     * @return title in English.
     */
    public String getEnglishTitle() {
        return this._englishTitle;
    }

    /**
     * Gets this work's author's name in English.
     *
     * @return author's name as a string.
     */
    public String getEnglishAuthor() {
        return this._englishAuthor;
    }

    /**
     * Gets the location of the file for this work.
     *
     * @return the file location as a string.
     */
    public int getLocation() {
        return this._location;
    }

    /**
     * Gets the location of the file for the English translation.
     *
     * @return the location of the file of the English translation.
     */
    public int getEnglishLocation() {
        return this._englishLocation;
    }

    public int getImage() { return this.image; }

    /**
     * Override the toString method for this class to provide
     * a formatted string
     *
     * @return a formatted string
     */
    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb, Locale.US);
        return formatter.format("%s %s %s %s %s %s",
                this._id,
                this._title,
                this._author,
                this._englishTitle,
                this._englishAuthor,
                this._location).toString();
    }

    /**
     * Gets the type of text that this info represents.
     *
     * @return the WorkType for this work.
     */
    public int getWorkType() {
        return this._workType;
    }

    /**
     * Gets the number of lines offset in the source text
     *
     * @return int lines offset
     */
    public int getOffset() {
        return this._offset;
    }

    /**
     * Gets the number of lines offset in the English translation
     *
     * @return int lines offset
     */
    public int getEnglishOffset() {
        return this._englishOffset;
    }

    /**
     * Gets the table of content entries
     *
     * @return ArrayList
     */
    public TOCEntry[] getTocEntries() {
        TOCEntry[] entries = new TOCEntry[tocEntries.size()];
        return tocEntries.toArray(entries);
    }

    /**
     * Gets the number of table of content entries
     * @return the number of table of content entries
     */
    public int getTOCCount() {
        return this.tocEntries.size();
    }

    /**
     * Specifies the type of work, poem or prose.
     */
    public static class WorkType {
        public static final int PROSE = 1;
        public static final int POEM = 2;
    }

    /**
     * Builder class for generating new WorkInfo objects.
     */
    public static class Builder {

        private final WorkInfo workInfo;

        public Builder(String id) {
            this.workInfo = new WorkInfo();
            this.workInfo._id = id;
            this.workInfo.tocEntries = new ArrayList<>();

            int idHash = Math.abs(id.hashCode());
            this.workInfo.image = defaultImages.get(idHash % defaultImages.size());
        }

        public Builder author(String author) {
            this.workInfo._author = author;
            return this;
        }

        public Builder title(String title) {
            this.workInfo._title = title;
            return this;
        }

        public Builder englishTitle(String englishTitle) {
            this.workInfo._englishTitle = englishTitle;
            return this;
        }

        public Builder englishAuthor(String englishAuthor) {
            this.workInfo._englishAuthor = englishAuthor;
            return this;
        }

        public Builder location(int location) {
            this.workInfo._location = location;
            return this;
        }

        public Builder englishLocation(int englishLocation) {
            this.workInfo._englishLocation = englishLocation;
            return this;
        }

        public Builder workType(int workType) {
            this.workInfo._workType = workType;
            return this;
        }

        public Builder offset(int offset, int englishOffset) {
            this.workInfo._offset = offset;
            this.workInfo._englishOffset = englishOffset;
            return this;
        }

        public Builder TOCEntry(TOCEntry entry) {
            this.workInfo.tocEntries.add(entry);
            return this;
        }

        public Builder image(Integer drawable) {
            this.workInfo.image = drawable;
            return this;
        }

        public WorkInfo build() {
            return this.workInfo;
        }
    }
}
