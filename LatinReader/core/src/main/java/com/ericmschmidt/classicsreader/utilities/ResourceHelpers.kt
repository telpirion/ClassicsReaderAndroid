package com.ericmschmidt.classicsreader.utilities

import com.ericmschmidt.classicsreader.MyApplication.Factory.applicationInstance
import com.ericmschmidt.classicsreader.datamodel.Book
import com.ericmschmidt.classicsreader.logError
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory

/** A file of helper functions for managing XML resources.
 *
 * @author Eric Schmidt
 * @author <a href="https://telpirion.com">...</a>
 * @version 2.0
 * @since 1.0
 */

/**
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

val ns: String? = null // Why? Because we strip out the namespace when cleaning up resources.

data object ResourceConstants {
    const val BOOK_TAG: String = "div1"
    const val LINE_TAG: String = "p"
}

data object DictionaryResourceConstants {
    const val LINE_TAG = "entry"
    const val KEY_ATTRIBUTE = "key"
    const val LANG_ATTRIBUTE = "lang"
}

fun getResourceStream(resourceID: Int): InputStream {
    val applicationInstance = applicationInstance()
    val context = applicationInstance.context
    val resources = context.resources

    // TODO: ensure that this is closed.
    val inputStream = resources.openRawResource(resourceID)
    return inputStream
}

// Determine how many books are contained in the work.
@Throws(XmlPullParserException::class, IOException::class)
fun countSections(parser: XmlPullParser, sectionTag: String?): Int {
    var count = 0
    try {
        while (nextSection(parser, sectionTag)) {
            count++
        }
    } catch (ex: Exception) {
        val errorMessage = ex.message
        logError(errorMessage)
    }
    return count
}

@Throws(XmlPullParserException::class, IOException::class)
fun nextSection(parser: XmlPullParser, sectionTag: String?): Boolean {
    while (parser.next() != XmlPullParser.END_DOCUMENT) {
        if (checkTag(sectionTag, parser, XmlPullParser.START_TAG)) {
            return true
        }
    }
    return false
}

// Get the lines of the section out of the text.
@Throws(XmlPullParserException::class, IOException::class)
fun parseLines(
    parser: XmlPullParser,
    id: Int,
    sectionTag: String?,
    lineTag: String?
): Book {
    val book = Book(id)
    parser.require(XmlPullParser.START_TAG, ns, sectionTag)

    while (!checkTag(sectionTag, parser, XmlPullParser.END_TAG)) {
        parser.next()

        if (checkTag(lineTag, parser, XmlPullParser.START_TAG)) {
            val currentLine = getLine(parser, lineTag)
            book.addLines(currentLine)
        }
    }

    return book
}

// Get all of the text out of the current line.
// This assumes that there aren't nested line tags within line tags.
@Throws(XmlPullParserException::class, IOException::class)
fun getLine(parser: XmlPullParser, lineTag: String?): String {
    var line = ""
    parser.require(XmlPullParser.START_TAG, ns, lineTag)

    while (!checkTag(lineTag, parser, XmlPullParser.END_TAG)) {
        parser.next()

        if (parser.eventType == XmlPullParser.TEXT) {
            line += removeExtraneousCharacters(parser.text)
        }
    }

    return line
}

// Check an XmlPullParser event against a tag name.
fun checkTag(tagName: String?, parser: XmlPullParser, xmlParserEvent: Int): Boolean {
    try {
        val currentTag = parser.name
        return currentTag != null && currentTag == tagName && parser.eventType == xmlParserEvent
    } catch (ex: Exception) {
        val errorMessage = ex.message
        logError(message = "checkTag: $errorMessage" )
    }
    return false
}

// Instantiate an XML parser with the given stream.
@Throws(XmlPullParserException::class, IOException::class)
fun initParser(stream: InputStream?): XmlPullParser {
    val parserFactory = XmlPullParserFactory.newInstance()
    val parser = parserFactory.newPullParser()

    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
    parser.setFeature(XmlPullParser.FEATURE_PROCESS_DOCDECL, false)

    parser.setInput(stream, null)

    return parser
}

// Removes extraneous characters from a line.
fun removeExtraneousCharacters(line: String): String {
    val patternString = "\\s\\s+"
    return line.replace(patternString.toRegex(), " ")
}

/**
 * Gets the number of books in this work.
 */
@Throws(XmlPullParserException::class, IOException::class)
fun getBookCount(stream: InputStream): Int {
    var bookCount = 0

    try {
        val parser = initParser(stream)
        bookCount = countSections(parser, ResourceConstants.BOOK_TAG)
    } catch (ex: Exception) {
        val errorMessage = ex.message
        logError(errorMessage)
    } finally {
        stream.close()
    }
    return bookCount
}

/**
 * Gets the specified book from the XML resource.
 */
@Throws(XmlPullParserException::class, IOException::class)
fun getBook(bookIndex: Int, stream: InputStream): Book? {
    var bookToGet: Book? = null
    var count = 0

    try {
        val parser = initParser(stream)

        while (nextSection(parser, ResourceConstants.BOOK_TAG)) {
            if (count == bookIndex) {
                break
            } else {
                count++
            }
        }

        bookToGet = parseLines(
            parser,
            bookIndex,
            ResourceConstants.BOOK_TAG,
            ResourceConstants.LINE_TAG
        )
    } catch (ex: java.lang.Exception) {
        val errorMessage = ex.message
        logError(errorMessage)
    } finally {
        stream.close()
    }
    return bookToGet
}

/**
 * Get a specific dictionary entry from the XML.
 */
fun getEntryHeaders(stream: InputStream): ArrayList<String> {
    val headers = ArrayList<String>()
    try {
        stream.use {
            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            val dom = builder.parse(it)
            val list = dom.getElementsByTagName("entry")

            for (i in 0 until list.length) {
                headers.add(list.item(i).textContent)
            }
        }
    } catch (ex: Exception) {
        logError(ex.message)
    }
    return headers
}

fun getEntry(stream: InputStream, searchEntry: String, converter: ITextConverter_): String {
    var definition: String? = null
    try {
        stream.use {
            val parser = initParser(it)
            while (nextSection(parser, DictionaryResourceConstants.LINE_TAG)) {
                parser.require(XmlPullParser.START_TAG, ns, DictionaryResourceConstants.LINE_TAG)
                val keyAttributeValue = parser.getAttributeValue(ns, DictionaryResourceConstants.KEY_ATTRIBUTE)
                if (keyAttributeValue != null && searchEntry == keyAttributeValue) {
                    break
                }
            }
            definition = getDictionaryLine(parser, DictionaryResourceConstants.LINE_TAG, converter)
        }
    } catch (ex: Exception) {
        logError(ex.message)
    }
    return removeExtraneousCharacters(definition as String)
}

@Throws(XmlPullParserException::class, IOException::class)
fun getDictionaryLine(
    parser: XmlPullParser,
    lineTag: String,
    converter: ITextConverter_
): String {
    var line = ""
    var currentSubLine: String
    var isNonLatin = false
    parser.require(XmlPullParser.START_TAG, ns, lineTag)
    while (!checkTag(lineTag, parser, XmlPullParser.END_TAG)) {
        parser.next()
        if (parser.eventType == XmlPullParser.START_TAG &&
            hasAttribute(parser)
        ) {
            isNonLatin = true
        }
        if (parser.eventType == XmlPullParser.TEXT) {

            // Check the entry for non-Latin characters and
            // convert to the other orthography, if necessary.
            currentSubLine = if (isNonLatin) converter.convertSourceToTargetCharacters(
                parser.text
            ) else parser.text
            line += removeExtraneousCharacters(currentSubLine)
            isNonLatin = false
        }
    }
    return line
}

private fun hasAttribute(
    parser: XmlPullParser,
): Boolean {
    val attributeValue = parser.getAttributeValue(
        ns, DictionaryResourceConstants.LANG_ATTRIBUTE)
    return attributeValue != null && attributeValue == DictionaryResourceConstants.LANG_ATTRIBUTE
}