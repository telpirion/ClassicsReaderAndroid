package com.ericmschmidt.latinreader.datamodel;

import java.util.ArrayList;
import java.util.List;

/** Represents a sub-division within a larger text.
 * @author Eric Schmidt
 * @author http://telpirion.com
 * @version 2019-11-17
 * @since 1.0
 */
public class Book {

    private ArrayList<String> _lines;
    private int _id;

    /**
     * Creates an instance of the Book class
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
            return null;
        } else {
            line = this._lines.get(0); // Default is to return the first line of the book.
        }
        return line;
    }

    /**
     * Get a sequence of lines from the book.
     * @param position the position to start the text from.
     * @param offset the number of lines to get.
     * @return String the text from the position indicated.
     */
    public String getLines(int position, int offset) {
        String text = "";
        List<String> lines;

        if (position < 0)
            return getLines(0, offset);

        if (position + offset < this._lines.size()) {
            lines = this._lines.subList(position, position + offset);
        } else {
            lines = this._lines.subList(position, this._lines.size());
        }

        for (String l : lines) {
            text += l + "\n";
        }

        return text;
    }

    /**
     * Gets the number of lines in this book.
     * @return an int of the number of lines.
     */
    public int getLineCount() {
        return this._lines.size();
    }
}
