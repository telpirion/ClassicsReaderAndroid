package com.example.ericmschmidt.classicsreaderandroid;

import java.util.Formatter;
import java.util.Locale;

/**
 * Created by ericmschmidt on 1/6/16.
 */
public class WorkInfo {

    /*

            Example:

            id: "CaesarBG",
            title: "De Bello Gallico", author: "C. Julius Caesar",
            engTitle: "The Gallic Wars", engAuthor: "Caesar",
            location: encodeURI(_dataURI + "caes.bg_lat.xml"),
            translation: encodeURI(_dataURI + "caes.bg_eng.xml")

     */

    private String _id;
    private String _author;
    private String _title;
    private String _englishTitle;
    private String _englishAuthor;
    private String _location;
    private String _englishLocation;

    /**
     * Creates a new instance of the Work class.
     * @param id The internal ID of the work.
     * @param title The title of the work in the original language.
     * @param author The author of the work (original language).
     * @param englishTitle The title of the work in English.
     * @param englishAuthor The name of the author in English.
     * @param location The location of the resource in the assembly.
     * @param englishLocation The location of the English translation in the assembly.
     */
    public WorkInfo(String id,
                String title,
                String author,
                String englishTitle,
                String englishAuthor,
                String location,
                String englishLocation)
    {
        this._id = id;
        this._title = title;
        this._author = author;
        this._englishTitle = englishTitle;
        this._englishAuthor = englishAuthor;
        this._location = location;
        this._englishLocation = englishLocation;
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
    public String getLocation()
    {
        return this._location;
    }

    /**
     * Gets the location of the file for the English translation.
     * @return the location of the file of the English translation.
     */
    public String getEnglishLocation()
    {
        return this._englishLocation;
    }

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
}
