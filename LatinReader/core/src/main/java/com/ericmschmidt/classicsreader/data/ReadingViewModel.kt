package com.ericmschmidt.classicsreader.data

import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.ericmschmidt.classicsreader.MyApplication
import com.ericmschmidt.classicsreader.R
import com.ericmschmidt.classicsreader.utilities.ITextConverter
import java.util.Locale
import kotlin.math.floor

/**
 * A ViewModel that maps reading behaviors to a view.
 * @author Eric Schmidt
 * @author <a href="https://telpirion.com">...</a>
 * @version 2.0
 * @since 1.1
 */
class ReadingViewModel(
    private val workInfo: WorkInfo,
    private val isTranslation: Boolean,
    private val pageOffset: Int
) {

    private val defaultReadingPosition = "0,0"
    private val currentWork: Work
    private var currentBook: Book?
    var currentLineIndex: Int = 0
        private set
    var currentBookIndex: Int = 0
        private set

    val author: String
    val title: String
    val toc: Array<TOCEntry>
        get() = workInfo.tocEntries.toTypedArray()

    private var converter: ITextConverter? = null

    init {
        val applicationInstance = MyApplication.Factory.applicationInstance()
        converter = if (applicationInstance.isNonRomanChar) {
            applicationInstance.textConverter
        } else {
            null
        }

        if (!loadLastReadingPosition()) { // This work hasn't been read yet.
            currentLineIndex = 0
            currentBookIndex = 0
        }

        if (isTranslation) {
            currentWork = Work(workInfo.englishLocation)
            author = workInfo.englishAuthor as String
            title = workInfo.englishTitle as String
        } else {
            currentWork = Work(workInfo.location)
            author = workInfo.author as String
            title = workInfo.title as String
        }

        currentBook = currentWork.getBook(currentBookIndex)
        updatePage()
    }

    /**
     * Gets the text for the reader's current position in the work.
     * @return String the text to read.
     */
    fun getCurrentPage(): String {
        val projectedIndex = if (isTranslation) {
            floor(pageOffset.toDouble() / workInfo.englishOffset).toInt()
        } else {
            pageOffset
        }
        return currentBook?.getLines(currentLineIndex, projectedIndex) ?: ""
    }

    /**
     * Scans the position in the book forwards or backwards.
     *
     * If the value goes beyond the end of the current book, it goes to the next book.
     * If the value goes beyond the beginning of the current book, it goes to the previous book.
     * If the value goes beyond the end of the work, it goes to the end of the work and stays there.
     * If the value goes beyond the beginning of the work, it goes to the first page.
     * @param numberOfPages the number of pages to update the reading position by
     */
    fun goToPage(numberOfPages: Int) {
        if (numberOfPages > 0) {
            advancePages(numberOfPages)
        } else {
            decreasePages(numberOfPages)
        }
        updatePage()
    }

    /**
     * Flips the page forwards or backwards one page.
     * @param isForward whether the user is flipping forwards or backwards.
     */
    fun goToPage(isForward: Boolean) {
        if (isForward) {
            goToPage(pageOffset)
        } else {
            goToPage(-1 * pageOffset)
        }
    }

    /**
     * Gets a formatted string that specifies the current work and reader's position.
     * @return String
     */
    fun getReadingInfo(): String {
        return String.format(Locale.US, "%s, %s", author, title)
    }

    /**
     * Gets the book number, current page, and total pages in the book as a formatted
     * string.
     * @return String
     */
    fun getReadingPositionString(): String {
        val applicationInstance = MyApplication.Factory.applicationInstance()
        val lineCount = currentBook?.getLineCount() ?: 0
        return if (currentWork.getBookCount() == 1) {
            applicationInstance.context
                .resources
                .getString(
                    R.string.reading_page_of_pages,
                    currentLineIndex + 1,
                    lineCount
                )
        } else applicationInstance.context
            .resources
            .getString(
                R.string.reading_book_page_of_pages,
                currentBookIndex + 1,
                currentLineIndex + 1,
                lineCount
            )
    }

    /**
     * Sets the current book for reading.
     * @param currentBook the book to set as current
     */
    fun setCurrentBook(currentBook: Int) {
        currentBookIndex = currentBook
        this.currentBook = this.currentWork.getBook(currentBook)
        updatePage()
    }

    /**
     * Sets the current line for reading
     * @param currentLine the current line to set as current
     */
    fun setCurrentLine(currentLine: Int) {
        currentLineIndex = currentLine
        updatePage()
    }

    // Increase the reading position.
    private fun advancePages(offset: Int) {
        var count = 0
        val bookLineCount = currentBook?.getLineCount()?.minus(1) ?: 0
        val bookCount = currentWork.getBookCount() - 1

        while (count < offset) {
            if (currentLineIndex < bookLineCount) {
                currentLineIndex++
            } else if (currentBookIndex == bookCount) {
                currentLineIndex = currentBook?.getLineCount()?.minus(1) ?: 0
            } else {
                currentBookIndex++
                currentBook = currentWork.getBook(currentBookIndex)
                currentLineIndex = 0
                break
            }
            count++
        }
    }

    // Decrease the reading position.
    private fun decreasePages(offset: Int) {
        var count = 0

        while (count > offset) {

            // The page is still within this book, keep going.
            if (currentLineIndex > 0) {
                currentLineIndex--

                // The user is on the first book; keep the book here.
            } else if (currentBookIndex == 0) {
                currentLineIndex = 0

                // The user flips to the previous book
            } else {
                currentBookIndex--
                currentBook = currentWork.getBook(currentBookIndex)
                currentLineIndex = currentBook?.getLineCount()?.minus(1) ?: 0
            }

            count--
        }
    }

    // Updates the current reading page.
    private fun updatePage() {
        val applicationInstance = MyApplication.Factory.applicationInstance()
        // Store the current reading position in SharedPreferences.
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationInstance.context)
        sharedPreferences.edit {
            val currentPosition = String.format(Locale.US, "%d,%d", currentBookIndex, currentLineIndex)
            putString(workInfo.id, currentPosition)
        }
    }

    // Gets the user's last reading position from device storage or cloud storage.
    private fun loadLastReadingPosition(): Boolean {
        val applicationInstance = MyApplication.Factory.applicationInstance()
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationInstance.context)
        val prefs = sharedPreferences.getString(workInfo.id, defaultReadingPosition)

        if (prefs != null && !prefs.contains(defaultReadingPosition)) {
            val readingPosition = prefs.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            // Need to store reading position as bookIndex,lineIndex
            currentBookIndex = readingPosition[0].toInt()
            currentLineIndex = readingPosition[1].toInt()

            return true
        }

        return false
    }
}
