package com.telpirion.classicsreader.core_data.utilities

import android.util.Log
import com.telpirion.classicsreader.core_data.data.Book
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream

/**
 * Parses an XML work containing a text.
 *
 * @author Eric Schmidt
 * @author <a href="http://telpirion.com">...</a>
 * @version 1.5
 * @since 1.0
 */
object WorkXMLParser : XmlParserHelper() {

    private const val BOOK_TAG = "div1"
    private const val LINE_TAG = "p"

    /**
     * Gets the number of books in this work.
     * @return the integer of Book objects in this Work.
     */
    @Throws(XmlPullParserException::class, IOException::class)
    fun getBookCount(stream: InputStream): Int {
        var bookCount = 0

        try {
            val parser = initParser(stream)
            bookCount = countSections(parser, BOOK_TAG)
        } catch (ex: Exception) {
            Log.e(this.javaClass.name, ex.message as String)
        } finally {
            stream.close()
        }
        return bookCount
    }

    /**
     * Gets the specified book from the XML resource.
     * @param bookIndex the ID of the book to get.
     * @return a parsed Book object.
     */
    @Throws(XmlPullParserException::class, IOException::class)
    fun getBook(bookIndex: Int, stream: InputStream): Book? {
        var bookToGet: Book? = null
        var count = 0

        try {
            val parser = initParser(stream)

            while (nextSection(parser, BOOK_TAG)) {
                if (count == bookIndex) {
                    break
                } else {
                    count++
                }
            }

            bookToGet = parseLines(parser, bookIndex, BOOK_TAG, LINE_TAG)
        } catch (ex: Exception) {
            Log.e(this.javaClass.name, ex.message as String)
        } finally {
            stream.close()
        }
        return bookToGet
    }
}