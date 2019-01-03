package com.ericmschmidt.latinreader.datamodel;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.Locale;

/**
 * A class that contains the data for a work contained in the app.
 * It includes the relevant bibliographical info (author, translator)
 * and the locations of the text in the app.
 */
public class WorkInfo {

    /*

            Example:

            id: "CaesarBG",
            title: "De Bello Gallico", author: "C. Julius Caesar",
            engTitle: "The Gallic Wars", engAuthor: "Caesar",
            location: encodeURI(_dataURI + "caes_bg_lat.xml"),
            translation: encodeURI(_dataURI + "caes_bg_eng.xml")
            workType: prose || poem

     */

    // TODO: Add translater info
    private String _id;
    private String _author;
    private String _title;
    private String _englishTitle;
    private String _englishAuthor;
    private int _location;
    private int _englishLocation;
    private int _workType;
    private ArrayList<TOCEntry> tocEntries;

    // Unless specified otherwise, assume a 1-to-1 relationship
    // between line numbers in the English and source language
    private int _offset = 1;
    private int _englishOffset = 1;

    /**
     * Creates a new instance of the Work class.
     * @param id The internal ID of the work.
     * @param title The title of the work in the original language.
     * @param author The author of the work (original language).
     * @param englishTitle The title of the work in English.
     * @param englishAuthor The name of the author in English.
     * @param location The location of the resource in the assembly.
     * @param englishLocation The location of the English translation in the assembly.
     * @param workType The type of text, poem or prose.
     */
    public WorkInfo(String id,
                String title,
                String author,
                String englishTitle,
                String englishAuthor,
                int location,
                int englishLocation,
                int workType)
    {
        this._id = id;
        this._title = title;
        this._author = author;
        this._englishTitle = englishTitle;
        this._englishAuthor = englishAuthor;
        this._location = location;
        this._englishLocation = englishLocation;
        this._workType = workType;
    }

    /**
     * Creates a new instance of the Work class.
     * @param id The internal ID of the work.
     * @param title The title of the work in the original language.
     * @param author The author of the work (original language).
     * @param englishTitle The title of the work in English.
     * @param englishAuthor The name of the author in English.
     * @param location The location of the resource in the assembly.
     * @param englishLocation The location of the English translation in the assembly.
     * @param workType The type of text, poem or prose.
     * @param offset The offset of the source text by line
     * @param englishOffset The offset of the translation text by line
     */
    public WorkInfo(String id,
                    String title,
                    String author,
                    String englishTitle,
                    String englishAuthor,
                    int location,
                    int englishLocation,
                    int workType,
                    int offset,
                    int englishOffset)
    {
        this(id, title,
                author,
                englishTitle,
                englishAuthor,
                location,
                englishLocation,
                workType);
        this._offset = offset;
        this._englishOffset = englishOffset;
        this.tocEntries = new ArrayList<>();
    }

    /**
     * Gets this work's ID.
     * @return ID as string
     */
    public String getId()
    {
        return this._id;
    }

    /**
     * Gets this work's author in source language.
     * @return author name as string
     */
    public String getAuthor()
    {
        return this._author;
    }

    /**
     * Gets this work's title in source language.
     * @return
     */
    public String getTitle()
    {
        return this._title;
    }

    /**
     * Gets this work's title in English.
     * @return title in English.
     */
    public String getEnglishTitle()
    {
        return this._englishTitle;
    }

    /**
     * Gets this work's author's name in English.
     * @return author's name as a string.
     */
    public String getEnglishAuthor()
    {
        return this._englishAuthor;
    }

    /**
     * Gets the location of the file for this work.
     * @return the file location as a string.
     */
    public int getLocation()
    {
        return this._location;
    }

    /**
     * Gets the location of the file for the English translation.
     * @return the location of the file of the English translation.
     */
    public int getEnglishLocation()
    {
        return this._englishLocation;
    }

    /**
     * Override the toString method for this class to provide
     * a formatted string
     * @return
     */
    public String toString()
    {
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
     * @return the WorkType for this work.
     */
    public int getWorkType() {
        return this._workType;
    }

    /**
     * Gets the number of lines offset in the source text
     * @return int lines offset
     */
    public int getOffset() {
        return this._offset;
    }

    /**
     * Gets the number of lines offset in the English translation
     * @return int lines offset
     */
    public int getEnglishOffset(){
        return this._englishOffset;
    }

    /**
     * Gets the table of content entries
     * @return ArrayList
     */
    public ArrayList<TOCEntry> getTocEntries() {
        return tocEntries;
    }

    public int getTOCCount() {
        return this.tocEntries.size();
    }

    public void addTOCEntry(TOCEntry entry) {
        this.tocEntries.add(entry);
    }

    /**
     * Specifies the type of work, poem or prose.
     */
    public class WorkType {
        public static final int PROSE = 1;
        public static final int POEM = 2;
    }

    /**
     * Builder class for generating new WorkInfo objects.
     */
    public static class Builder {
        private String _id;
        private String _author;
        private String _title;
        private String _englishTitle;
        private String _englishAuthor;
        private int _location;
        private int _englishLocation;
        private int _workType;
        private int _offset = 1;
        private int _englishOffset = 1;
        private ArrayList<TOCEntry> tocEntries;

        public Builder(String id){
            this._id = id;
            this.tocEntries = new ArrayList<>();
        }
        public Builder author(String author) {
            this._author = author;
            return this;
        }
        public Builder title(String title) {
            this._title = title;
            return this;
        }
        public Builder englishTitle(String englishTitle) {
            this._englishTitle = englishTitle;
            return this;
        }
        public Builder englishAuthor(String englishAuthor) {
            this._englishAuthor = englishAuthor;
            return this;
        }
        public Builder location(int location) {
            this._location = location;
            return this;
        }
        public Builder englishLocation(int englishLocation) {
            this._englishLocation = englishLocation;
            return this;
        }
        public Builder workType(int workType) {
            this._workType = workType;
            return this;
        }
        public Builder offset(int offset, int englishOffset) {
            this._offset = offset;
            this._englishOffset = englishOffset;
            return this;
        }
        public Builder TOCEntry(TOCEntry entry) {
            this.tocEntries.add(entry);
            return this;
        }
        public WorkInfo create() {
            WorkInfo info = new WorkInfo(this._id,
                    this._title,
                    this._author,
                    this._englishTitle,
                    this._englishAuthor,
                    this._location,
                    this._englishLocation,
                    this._workType,
                    this._offset,
                    this._englishOffset);

            info.tocEntries = this.tocEntries;

            return info;
        }
    }
}
