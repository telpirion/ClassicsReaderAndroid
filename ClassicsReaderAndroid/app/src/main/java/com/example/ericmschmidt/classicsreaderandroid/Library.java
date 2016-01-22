package com.example.ericmschmidt.classicsreaderandroid;

import java.util.ArrayList;

/**
 * Created by ericmschmidt on 1/6/16.
 */
public class Library {

    private ArrayList<WorkInfo>  _workInfos;

    public Library()
    {
        this._workInfos = Manifest.getCollection();
    }



    public Work getTranslation(String id)
    {
        return new Work();
    }
    public String[] getTranslationLines(String id)
    {
        String[] lines = {};
        return lines;
    }

    public Book getBook(String id)
    {
        return new Book();
    }

    public int getBookCount(Book book)
    {
        return 0;
    }
}
