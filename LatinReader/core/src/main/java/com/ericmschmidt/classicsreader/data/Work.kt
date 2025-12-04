package com.ericmschmidt.classicsreader.datamodel

import com.ericmschmidt.classicsreader.logError
import com.ericmschmidt.classicsreader.utilities.getBook
import com.ericmschmidt.classicsreader.utilities.getBookCount
import com.ericmschmidt.classicsreader.utilities.getResourceStream

/** Contains the data for a text ('work') contained in the reader.
 * @author Eric Schmidt
 * @author <a href="https://telpirion.com">...</a>
 * @version 2.0
 * @since 1.0
 */
class Work(val location: Int) {

    private lateinit var _books: Array<Book?>
    private var _bookCount = 0

    init {
        initBooks()
    }

    /**
     * Gets the specific book from the collection.
     * @param id the ID of the book to get.
     * @return Book object
     */
    fun getBook(id: Int): Book? {
        val indexToGet = validateBookIndex(id)

        return this._getBook(indexToGet, this.location, this._books)
    }

    /**
     * Get the number of books contained in this work.
     * @return The number of books in this work.
     */
    fun getBookCount(): Int {
        return this._bookCount
    }

    // Initialize the array of books based upon the total number of books in the work.
    private fun initBooks() {
        getResourceStream(this.location).use { stream ->
            this._bookCount = getBookCount(stream)
            this._books = arrayOfNulls<Book>(this._bookCount)

            if (this._bookCount == 0) {
                throw Exception("Work failed to initialize.")
            }
        }
    }

    // Retrieve a book from the internal array or get it from the app's resources.
    @Suppress("FunctionName")
    private fun _getBook(id: Int, location: Int, bookCollection: Array<Book?>): Book? {
        var book = bookCollection[id]
        try {
            if (book == null) {
                val stream = getResourceStream(location)
                book = getBook(id, stream)

                bookCollection[id] = book
            }
        } catch (ex: Exception) {
            val errorMessage = ex.message
            logError(errorMessage)
        }

        return book
    }

    // Handle cases where the request is too high or too low
    private fun validateBookIndex(index: Int): Int {
        var indexToGet = 0

        if (index >= 0
            && index < this._bookCount
        ) {
            indexToGet = index
        } else if (index >= this._bookCount) { // If the request is too high, return the last book.
            indexToGet = this._bookCount - 1
        }

        return indexToGet
    }
}