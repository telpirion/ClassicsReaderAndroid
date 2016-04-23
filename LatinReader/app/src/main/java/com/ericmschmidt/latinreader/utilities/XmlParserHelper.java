package com.ericmschmidt.latinreader.utilities;

import com.ericmschmidt.latinreader.MyApplication;
import com.ericmschmidt.latinreader.datamodel.Book;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * A helper class for working with an XmlPullParser.
 */
public class XmlParserHelper {

    public static final String ns = "";

    // Determine how many books are contained in the work.
    protected static int countSections(XmlPullParser parser, String sectionTag) throws XmlPullParserException, IOException {

        int count = 0;
        try {
            while(nextSection(parser, sectionTag)) {
                count++;
            }

        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            MyApplication.logError(errorMessage);
        }
        return count;
    }

    // Move the parser to the position of the next section tag.
    protected static boolean nextSection(XmlPullParser parser, String sectionTag) throws XmlPullParserException, IOException {
        while(parser.next() != XmlPullParser.END_DOCUMENT) {
            String name = parser.getName();
            if (checkTag(sectionTag, parser, XmlPullParser.START_TAG)) {
                return true;
            }
        }
        return false;
    }

    // Get the lines of the section out of the text.
    protected static Book parseLines(XmlPullParser parser, int id, String sectionTag, String lineTag) throws XmlPullParserException, IOException {
        Book book = new Book(id);
        parser.require(XmlPullParser.START_TAG, ns, sectionTag);

        while (!checkTag(sectionTag, parser, XmlPullParser.END_TAG)) {
            parser.next();

            if (checkTag(lineTag, parser, XmlPullParser.START_TAG)) {
                String currentLine = getLine(parser, lineTag);
                book.addLines(currentLine);
            }
        }

        return book;
    }

    // Get all of the text out of the current line.
    // This assumes that there aren't nested line tags within line tags.
    protected static String getLine(XmlPullParser parser, String lineTag) throws XmlPullParserException, IOException {
        String line = "";
        parser.require(XmlPullParser.START_TAG, ns, lineTag);

        while (!checkTag(lineTag, parser, XmlPullParser.END_TAG)) {
            parser.next();

            if (parser.getEventType() == XmlPullParser.TEXT) {
                line += removeExtraneousCharacters(parser.getText());
            }
        }

        return line;
    }

    // Check an XmlPullParser event against a tag name.
    protected static boolean checkTag(String tagName, XmlPullParser parser, int xmlParserEvent) {
        try {
            String currentTag = parser.getName();
            return currentTag != null && currentTag.equals(tagName) && parser.getEventType() == xmlParserEvent;
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
        }
        return false;
    }

    // Instantiate an XML parser with the given stream.
    protected static XmlPullParser initParser(InputStream stream) throws XmlPullParserException, IOException {
        XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = parserFactory.newPullParser();

        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_DOCDECL, false);

        parser.setInput(stream, null);

        return parser;
    }

    // Removes extraneous characters from a line.
    protected static String removeExtraneousCharacters(String line){
        String patternString = "\\s\\s+";
        return line.replaceAll(patternString, " ");
    }
}
