package com.example.ericmschmidt.classicsreaderandroid;

import java.util.ArrayList;

/**
 * Created by ericmschmidt on 1/6/16.
 */
public class Work {

    private WorkInfo _info;

    public Work(WorkInfo info)
    {
        _info = info;
        // TODO: Open the XML file from the resources.
    }

    public String getId()
    {
        return _info.getId();
    }

    public ArrayList<Book> getBooks()
    {
        return new ArrayList<Book>();
    }
}
