package com.ericmschmidt.classicsreader.utilities;

import com.ericmschmidt.classicsreader.MyApplication;

import java.io.*;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/** Gets dictionary resources out of the XML.
 *
 * @author Eric Schmidt
 * @author http://telpirion.com
 * @version 1.5
 * @since 1.0
 */
public class DictionaryXMLHelper extends XmlParserHelper {

    // Overrides constants in super class.
    private static final String LINE_TAG = "entry";
    private static final String KEY_ATTRIBUTE = "key";
    private static final String LANG_ATTRIBUTE = "lang";

    /*
    The XML dictionary is structured like this:

    <work> <!-- This corresponds to the Work data structure -->
      <header>
        <!-- Stuff here ... -->
      <header/>
      <text>
        <body> <!-- This is like a collection of Book objects -->
          <div0 type="alphabetic letter" n="A"> <!-- This is a alphabetic section in the dictionary.
          <head></head>
            <superEntry>
              <entry></entry> <!-- This is usually the entry for the letter itself. -->
              <entry></entry>
            </superEntry>
            <entry id="n2" type="main" key="abactus"> <!-- This is the actual dictionary entry for a word. -->
              <form><orth extent="full" lang="la">abctus</orth></form>
              <sense id="n2.0" level="0" n="0"><etym lang="la">P. of abigo</etym>,
                <trans><tr>driven away, driven off</tr></trans>:
                <foreign lang="la">nox abacta</foreign>,
                <trans><tr>driven back</tr></trans> (from the pole), i. e.
                <trans><tr>already turned towards dawn</tr></trans>,
                <usg>V.</usg>:
                <foreign lang="la">abacta null conscienti</foreign>,
                <trans><tr>restrained by</tr></trans>,
                <usg>H.</usg>
              </sense>
            </entry>
          </div0>
        </body>
      </text>
    </work>
    */

    /**
     * Gets the keys of all the entries in this dictionary from an entries resource.
     * @param stream the inputStream of the dictionary.
     * @return the ArrayList of dictionary entries.
     * @throws Exception
     */
    public static ArrayList<String> getEntryHeaders(InputStream stream) throws Exception {
        ArrayList<String> headers = new ArrayList<>();
        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document dom = builder.parse(stream);
            NodeList list = dom.getElementsByTagName("entry");

            for (int i = 0; i < list.getLength(); i++) {
                headers.add(list.item(i).getTextContent());
            }

        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            MyApplication.logError(errorMessage);
        } finally {
            stream.close();
        }
        return headers;
    }

    /**
     * Get a specific dictionary entry from the XML.
     * @param stream the inputStream of the dictionary.
     * @param searchEntry the dictionary entry to search for.
     * @return the definition for the entry.
     * @throws XmlPullParserException
     * @throws IOException
     */
    public static String getEntry(InputStream stream, String searchEntry, ITextConverter converter) throws XmlPullParserException, IOException {
        String definition = null;

        try {
            XmlPullParser parser = initParser(stream);
            while (nextSection(parser, LINE_TAG)) {
                parser.require(XmlPullParser.START_TAG, XmlParserHelper.ns, LINE_TAG);

                String keyAttributeValue = parser.getAttributeValue(XmlParserHelper.ns, KEY_ATTRIBUTE);
                if (keyAttributeValue != null
                        && searchEntry.equals(keyAttributeValue)) {
                    break;
                }
            }

            definition = getDictionaryLine(parser, LINE_TAG, converter);

        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            MyApplication.logError(errorMessage);
        } finally {
            stream.close();
        }

        return removeExtraneousCharacters(definition);
    }

    private static String getDictionaryLine(XmlPullParser parser, String lineTag, ITextConverter converter) throws XmlPullParserException, IOException {
        String line = "";
        String currentSubLine;
        boolean isNonLatin = false;
        parser.require(XmlPullParser.START_TAG, ns, lineTag);

        while (!checkTag(lineTag, parser, XmlPullParser.END_TAG)) {
            parser.next();

            if ((parser.getEventType() == XmlPullParser.START_TAG) &&
                    converter != null &&
                    hasAttribute(parser, LANG_ATTRIBUTE, converter.getLang())) {
                isNonLatin = true;
            }

            if (parser.getEventType() == XmlPullParser.TEXT) {

                // Check the entry for non-Latin characters and
                // convert to the other orthography, if necessary.
                currentSubLine = isNonLatin ?
                        converter.convertSourceToTargetCharacters(parser.getText()):
                        parser.getText();
                line += removeExtraneousCharacters(currentSubLine);
                isNonLatin = false;
            }
        }

        return line;
    }

    private static boolean hasAttribute(XmlPullParser parser, String attributeName, String languageName){
        String attributeValue = parser.getAttributeValue(null, attributeName);
        return ((attributeValue!= null) && (attributeValue.equals(languageName)));
    }
}
