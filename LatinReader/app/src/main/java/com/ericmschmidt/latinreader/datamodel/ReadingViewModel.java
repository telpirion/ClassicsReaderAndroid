package com.ericmschmidt.latinreader.datamodel;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ericmschmidt.classicsreader.R;
import com.ericmschmidt.latinreader.MyApplication;
import com.ericmschmidt.latinreader.utilities.ITextConverter;

import java.util.Locale;

/** A ViewModel that maps reading behaviors to a view.
 * @author Eric Schmidt
 * @author http://telpirion.com
 * @version 1.5
 * @since 1.1
 */
public class ReadingViewModel {

    private String DEFAULT_READING_POSITION="0,0";

    private WorkInfo _currentWorkInfo;
    private Work _currentWork;
    private Book _currentBook;
    private int _currentLineIndex;
    private int _currentBookIndex;
    private int _pageOffset;
    private String _author;
    private String _title;
    private boolean _isTranslation;
    private ITextConverter converter;

    /**
     * Creates an instance of the ReadingViewModel class with a work open.
     * @param work the work to open.
     * @param isTranslation determines whether to return the translation of this work.
     * @param pageOffset
     */
    public ReadingViewModel(WorkInfo work,
                            boolean isTranslation,
                            int pageOffset) {
        this._currentWorkInfo = work;
        this._pageOffset = (pageOffset > -1) ? pageOffset : 1;
        this._isTranslation = isTranslation;
        this.converter = MyApplication.isNonRomanChar() ?
            MyApplication.getTextConverter() : null;

        if (!loadLastReadingPosition()) { // This work hasn't been read yet.
            this._currentLineIndex = 0;
            this._currentBookIndex = 0;
        }

        if (this._isTranslation) {
            this._currentWork = new Work(work.getEnglishLocation());
            this._author = work.getEnglishAuthor();
            this._title = work.getEnglishTitle();
        } else {
            this._currentWork = new Work(work.getLocation());
            this._author = work.getAuthor();
            this._title = work.getTitle();
        }

        this._currentBook = this._currentWork.getBook(this._currentBookIndex);
        updatePage();
    }

    /**
     * Gets the text for the reader's current position in the work.
     * @return String the text to read.
     */
    public String getCurrentPage() {
        int projectedIndex;

        if (_isTranslation){
            projectedIndex = (int)Math.floor(this._pageOffset / this._currentWorkInfo.getEnglishOffset());
        } else {
            projectedIndex = this._pageOffset;
        }
        return this._currentBook.getLines(this._currentLineIndex, projectedIndex);
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
    public void goToPage(int numberOfPages) {

        if (numberOfPages > 0){
            advancePages(numberOfPages);
        } else {
            decreasePages(numberOfPages);
        }

        updatePage();
    }

    /**
     * Flips the page forwards or backwards one page.
     * @param isForward whether the user is flipping forwards or backwards.
     */
    public void goToPage(boolean isForward) {
        if (isForward) {
            goToPage(this._pageOffset);
        } else {
            goToPage(-1 * this._pageOffset);
        }
    }

    /**
     * Gets a formatted string that specifies the current work and reader's position.
     * @return String
     */
    public String getReadingInfo() {

         return String.format(Locale.US, "%s, %s",
                 this._author,
                 this._title);
    }

    /**
     * Gets the book number, current page, and total pages in the book as a formatted
     * string.
     * @return String
     */
    public String getReadingPositionString() {

        if (this._currentWork.getBookCount() == 1) {
            return MyApplication.getContext()
                    .getResources()
                    .getString(R.string.reading_page_of_pages,
                            this._currentLineIndex + 1,
                            this._currentBook.getLineCount());
        }
        return MyApplication.getContext()
                        .getResources()
                        .getString(R.string.reading_book_page_of_pages,
                                this._currentBookIndex + 1,
                                this._currentLineIndex + 1,
                                this._currentBook.getLineCount());
    }

    /**
     * Gets the table of contents for the work.
     * @return ArrayList
     */
    public TOCEntry[] getTOC() {
        return this._currentWorkInfo.getTocEntries();
    }

    /**
     * Sets the current book for reading.
     * @param currentBook the book to set as current
     */
    public void setCurrentBook(int currentBook) {
        this._currentBookIndex = currentBook;
        this._currentBook = this._currentWork.getBook(currentBook);
        updatePage();
    }

    /**
     * Sets the current line for reading
     * @param currentLine the current line to set as current
     */
    public void setCurrentLine(int currentLine) {
        this._currentLineIndex = currentLine;
        updatePage();
    }

    /**
     * Access the currently viewed book.
     * @return int
     */
    public int getCurrentBookIndex() {
        return this._currentBookIndex;
    }

    /**
     * Access the currently viewed line.
     * @return int
     */
    public int getCurrentLineIndex() {
        return this._currentLineIndex;
    }

    // Determines the line to get given the page offset
    // of the work and its translation
    private int resolvePageIndex() {
        return 0;
    }

    // Increase the reading position.
    private void advancePages(int offset) {
        int count = 0;
        int bookLineCount = this._currentBook.getLineCount() - 1;
        int bookCount = this._currentWork.getBookCount() - 1;

        while (count < offset) {
            if (this._currentLineIndex < bookLineCount) {
                this._currentLineIndex++;
            } else if (this._currentBookIndex == bookCount){
                this._currentLineIndex = this._currentBook.getLineCount() - 1;
            }else {
                this._currentBookIndex++;
                this._currentBook = this._currentWork.getBook(this._currentBookIndex);
                this._currentLineIndex = 0;
                break;
            }
            count++;
        }
    }

    // Decrease the reading position.
    private void decreasePages(int offset) {
        int count = 0;

        while (count > offset) {

            // The page is still within this book, keep going.
            if (this._currentLineIndex > 0){
                this._currentLineIndex--;

             // The user is on the first book; keep the book here.
            } else if (this._currentBookIndex == 0) {
                this._currentLineIndex = 0;

            // The user flips to the previous book
            } else {
                this._currentBookIndex--;
                this._currentBook = this._currentWork.getBook(this._currentBookIndex);
                this._currentLineIndex = this._currentBook.getLineCount() - 1;
            }

            count--;
        }
    }

    // Gets the specified work from the Library.

    // Updates the current reading page.
    private void updatePage() {

        // Store the current reading position in SharedPreferences.
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String currentPosition = String.format(Locale.US, "%d,%d", this._currentBookIndex, this._currentLineIndex);

        editor.putString(_currentWorkInfo.getId(), currentPosition);
        editor.apply();
    }

    // Open up the next book.
    private void advanceBook(int deltaPages) {
        this._currentBookIndex++;

        // This feels like code smell ...
        if (this._currentBookIndex < this._currentWork.getBookCount()) {
            this._currentBook = this._currentWork.getBook(this._currentBookIndex);
            this._currentLineIndex = 0;

            goToPage(deltaPages);
        } else {
            this._currentBookIndex = this._currentWork.getBookCount() - 1;
            this._currentLineIndex = this._currentBook.getLineCount() - 1;
        }
    }

    // Go to the previous book.
    private void decreaseBook(int deltaPages) {
        this._currentBookIndex--;

        if (this._currentBookIndex >= 0) {
            this._currentBook = this._currentWork.getBook(this._currentBookIndex);
            this._currentLineIndex = this._currentBook.getLineCount();

            goToPage(deltaPages);
        } else {
            this._currentBookIndex = 0;
            this._currentLineIndex = 0;
        }
    }

    // Gets the user's last reading position from device storage or cloud storage.
    private boolean loadLastReadingPosition() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        String prefs = sharedPreferences.getString(_currentWorkInfo.getId(), DEFAULT_READING_POSITION);

        if (!prefs.contains(DEFAULT_READING_POSITION)) {
            String[] readingPosition = prefs.split(",");

            // Need to store reading position as bookIndex,lineIndex
            this._currentBookIndex = Integer.parseInt(readingPosition[0]);
            this._currentLineIndex = Integer.parseInt(readingPosition[1]);

            return true;
        }

        return false;
    }
}
