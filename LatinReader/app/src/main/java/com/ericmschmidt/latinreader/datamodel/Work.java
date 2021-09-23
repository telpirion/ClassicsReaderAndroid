package com.ericmschmidt.latinreader.datamodel;

import java.io.InputStream;

import com.ericmschmidt.latinreader.MyApplication;
import com.ericmschmidt.latinreader.utilities.ResourceHelper;
import com.ericmschmidt.latinreader.utilities.WorkXMLParser;

/** Contains the data for a text ('work') contained in the reader.
 * @author Eric Schmidt
 * @author http://telpirion.com
 * @version 1.5
 * @since 1.0
 */
public class Work {

    private Book[] _books;
    private int _bookCount;
    private int _location;

    /**
     * Creates an instance of the Work class.
     * @param location the resource location of the Work to get.
     */
    public Work(int location) {
        this._location = location;
        initBooks();
    }

    /**
     * Default public constructor.
     */
    public Work() {}

    /**
     * Gets the specific book from the collection.
     * @param id the ID of the book to get.
     * @return Book object
     */
    public Book getBook(int id) {
        int indexToGet = validateBookIndex(id);

        return this._getBook(indexToGet, this._location, this._books);
    }

    /**
     * Get the number of books contained in this work.
     * @return The number of books in this work.
     */
    public int getBookCount()
    {
        return this._bookCount;
    }

    // Initialize the array of books based upon the total number of books in the work.
    private void initBooks() {

        try { // TODO: if we update the min version to 19, we can use try() statement like C# using.

            InputStream stream = ResourceHelper.getResourceStream(this._location);
            this._bookCount = WorkXMLParser.getBookCount(stream);
            this._books = new Book[this._bookCount];

            if (this._bookCount == 0) {
                throw new Exception("Work failed to initialize.");
            }

        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            MyApplication.logError(errorMessage);
        }
    }

    // Retrieve a book from the internal array or get it from the app's resources.
    private Book _getBook(int id, int location, Book[] bookCollection) {

        Book book = bookCollection[id];

        try { // TODO: if we update the min version to 19, we can use try() statement like C# using.

            if (book == null) {
                InputStream stream = ResourceHelper.getResourceStream(location);
                book = WorkXMLParser.getBook(id, stream);

                bookCollection[id] = book;
            }

        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            MyApplication.logError(errorMessage);
        }

        return book;
    }

    // Handle cases where the request is too high or too low
    private int validateBookIndex(int index) {

        int indexToGet = 0;

        if (index >= 0
                && index < this._bookCount) {
            indexToGet = index;
        } else if (index >= this._bookCount) { // If the request is too high, return the last book.
            indexToGet = this._bookCount - 1;
        }

        return indexToGet;
    }
}
