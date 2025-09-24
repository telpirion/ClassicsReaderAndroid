package com.telpirion.classicsreader.core_data.utilities

import android.util.Log
import com.telpirion.classicsreader.core_data.data.Book
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream

/**
 * A helper class for working with an XmlPullParser.
 *
 * @author Eric Schmidt
 * @author http://telpirion.com
 * @version 1.5
 * @since 1.0
 */
open class XmlParserHelper {

    companion object {
        const val ns = ""
    }

    // Determine how many books are contained in the work.
    @Throws(XmlPullParserException::class, IOException::class)
    protected open fun countSections(parser: XmlPullParser, sectionTag: String): Int {
        var count = 0
        try {
            while (nextSection(parser, sectionTag)) {
                count++
            }
        } catch (ex: Exception) {
            Log.e(this.javaClass.name, ex.message as String)
        }
        return count
    }

    // Move the parser to the position of the next section tag.
    @Throws(XmlPullParserException::class, IOException::class)
    protected open fun nextSection(parser: XmlPullParser, sectionTag: String): Boolean {
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (checkTag(sectionTag, parser, XmlPullParser.START_TAG)) {
                return true
            }
        }
        return false
    }

    // Get the lines of the section out of the text.
    @Throws(XmlPullParserException::class, IOException::class)
    protected open fun parseLines(parser: XmlPullParser, id: Int, sectionTag: String, lineTag: String): Book {
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
    @Throws(XmlPullParserException::class, IOException::class)
    protected open fun getLine(parser: XmlPullParser, lineTag: String): String {
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
    protected open fun checkTag(tagName: String, parser: XmlPullParser, xmlParserEvent: Int): Boolean {
        return try {
            val currentTag = parser.name
            currentTag != null && currentTag == tagName && parser.eventType == xmlParserEvent
        } catch (ex: Exception) {
            Log.e(this.javaClass.name, ex.message as String)
            false
        }
    }

    // Instantiate an XML parser with the given stream.
    @Throws(XmlPullParserException::class, IOException::class)
    protected open fun initParser(stream: InputStream): XmlPullParser {
        val parserFactory = XmlPullParserFactory.newInstance()
        val parser = parserFactory.newPullParser()

        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_DOCDECL, false)

        parser.setInput(stream, null)

        return parser
    }

    // Removes extraneous characters from a line.
    protected open fun removeExtraneousCharacters(line: String): String {
        val patternString = "\\s\\s+"
        return line.replace(patternString.toRegex(), " ")
    }
}
