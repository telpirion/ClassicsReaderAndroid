package com.telpirion.classicsreader.core_data.utilities

import android.util.Log
import org.w3c.dom.Document
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory

/**
 * Gets dictionary resources out of the XML.
 *
 * @author Eric Schmidt
 * @author <a href="http://telpirion.com">...</a>
 * @version 1.5
 * @since 1.0
 */
object DictionaryXMLHelper : XmlParserHelper() {

    private const val LINE_TAG = "entry"
    private const val KEY_ATTRIBUTE = "key"
    private const val LANG_ATTRIBUTE = "lang"

    /**
     * Gets the keys of all the entries in this dictionary from an entries resource.
     * @param stream the inputStream of the dictionary.
     * @return the ArrayList of dictionary entries.
     */
    @Throws(Exception::class)
    fun getEntryHeaders(stream: InputStream): ArrayList<String> {
        val headers = ArrayList<String>()
        try {
            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()

            val dom: Document = builder.parse(stream)
            val list = dom.getElementsByTagName("entry")

            for (i in 0 until list.length) {
                headers.add(list.item(i).textContent)
            }
        } catch (ex: Exception) {
            Log.e(this.javaClass.name, ex.message as String)
        } finally {
            stream.close()
        }
        return headers
    }

    /**
     * Get a specific dictionary entry from the XML.
     * @param stream the inputStream of the dictionary.
     * @param searchEntry the dictionary entry to search for.
     * @return the definition for the entry.
     */
    @Throws(XmlPullParserException::class, IOException::class)
    fun getEntry(stream: InputStream, searchEntry: String, converter: ITextConverter?): String? {
        var definition: String? = null

        try {
            val parser = initParser(stream)
            while (nextSection(parser, LINE_TAG)) {
                parser.require(XmlPullParser.START_TAG, ns, LINE_TAG)

                val keyAttributeValue = parser.getAttributeValue(ns, KEY_ATTRIBUTE)
                if (keyAttributeValue != null && searchEntry == keyAttributeValue) {
                    break
                }
            }

            definition = getDictionaryLine(parser, LINE_TAG, converter)

        } catch (ex: Exception) {
            Log.e(this.javaClass.name, ex.message as String)
        } finally {
            stream.close()
        }

        return definition?.let { removeExtraneousCharacters(it) }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun getDictionaryLine(parser: XmlPullParser,
                                  lineTag: String,
                                  converter: ITextConverter?): String {
        var line = ""
        var currentSubLine: String
        var isNonLatin = false
        parser.require(XmlPullParser.START_TAG, ns, lineTag)

        while (!checkTag(lineTag, parser, XmlPullParser.END_TAG)) {
            parser.next()

            if (parser.eventType == XmlPullParser.START_TAG && converter != null && hasAttribute(parser, LANG_ATTRIBUTE, converter.lang)) {
                isNonLatin = true
            }

            if (parser.eventType == XmlPullParser.TEXT) {
                currentSubLine = if (isNonLatin && converter != null) {
                    converter.convertSourceToTargetCharacters(parser.text)
                } else {
                    parser.text
                }
                line += removeExtraneousCharacters(currentSubLine)
                isNonLatin = false
            }
        }

        return line
    }

    private fun hasAttribute(parser: XmlPullParser, attributeName: String, languageName: String): Boolean {
        val attributeValue = parser.getAttributeValue(null, attributeName)
        return attributeValue != null && attributeValue == languageName
    }
}