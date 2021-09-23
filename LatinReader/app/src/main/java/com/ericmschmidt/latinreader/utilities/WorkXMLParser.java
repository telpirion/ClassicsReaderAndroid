package com.ericmschmidt.latinreader.utilities;

import com.ericmschmidt.latinreader.MyApplication;
import com.ericmschmidt.latinreader.datamodel.Book;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/** Parses an XML work containing a text.
 *
 * @author Eric Schmidt
 * @author http://telpirion.com
 * @version 1.5
 * @since 1.0
 */
public class WorkXMLParser extends XmlParserHelper {

    private static final String ns = null;
    private static final String bookTag = "div1";
    private static final String lineTag = "p";

    /*
    The XML works are generally structured like this:

    <work> <!-- This corresponds to the Work data structure -->
      <header>
        <!-- Stuff here ... -->
      <header/>
      <text>
        <body> <!-- This is like a collection of Book objects -->
          <div1 type="Book" n="1"> <!-- This corresponds to the Book data structure -->
            <p>
              <milestone n="1" unit="chapter"/>
              <milestone n="1" unit="section"/>This is the text we want.<milestone n="2" unit="section"/> This text too.
            </p>
          </div>
        </body>
      </text>
    </work>
    */

    /**
     * Gets the number of books in this work.
     * @return the integer of Book objects in this Work.
     */
    public static int getBookCount(InputStream stream) throws XmlPullParserException, IOException {
        int bookCount = 0;

        try {
            XmlPullParser parser = initParser(stream);
            bookCount = countSections(parser, bookTag);

        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            MyApplication.logError(errorMessage);
        } finally {
            stream.close();
        }
        return bookCount;
    }

    /**
     * Gets the specified book from the XML resource.
     * @param bookIndex the ID of the book to get.
     * @return a parsed Book object.
     */
    public static Book getBook(int bookIndex, InputStream stream) throws XmlPullParserException, IOException {

        Book bookToGet = null;
        int count = 0;

        try {
            XmlPullParser parser = initParser(stream);

            while (nextSection(parser, bookTag)) {

                if (count == bookIndex) {
                    break;
                } else {
                    count++;
                }
            }

            bookToGet = parseLines(parser, bookIndex, bookTag, lineTag);

        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            MyApplication.logError(errorMessage);
        } finally {
            stream.close();
        }
        return bookToGet;
    }
}
