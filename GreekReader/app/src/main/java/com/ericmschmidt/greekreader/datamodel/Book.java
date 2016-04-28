package com.ericmschmidt.greekreader.datamodel;

import java.util.ArrayList;

/**
 * Represents a sub-division within a larger text.
 */
public class Book {

    private ArrayList<String> _lines;
    private int _id;

    /**
     * Creates an instance of the Bok class
     */
    public Book(int id) {
        this._lines = new ArrayList<String>();
        this._id = id;
    }

    /**
     * Adds a line to the book.
     * @param line the line to add.
     */
    public void addLines(String line) {
        this._lines.add(line);
    }

    /**
     * Gets the ID of this book within the work.
     * @return an int specifying this book's position.
     */
    public int getId() {
        return this._id;
    }

    /**
     * Gets a line from this book.
     * @param position the position of the line from the beginning of the book.
     * @return the line.
     */
    public String getLine(int position) {

        String line = null;
        if (position >= 0
                && position < this._lines.size()) {
            line = this._lines.get(position);
        } else if (position > this._lines.size()) {

        } else {
            line = this._lines.get(0); // Default is to return the first line of the book.
        }
        return line;
    }

    /**
     * Gets the number of lines in this book.
     * @return an int of the number of lines.
     */
    public int getLineCount() {
        return this._lines.size();
    }
}
