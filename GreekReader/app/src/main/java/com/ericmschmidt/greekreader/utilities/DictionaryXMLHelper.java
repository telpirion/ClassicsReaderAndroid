package com.ericmschmidt.greekreader.utilities;

import com.ericmschmidt.greekreader.MyApplication;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.*;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

/**
 * Gets dictionary resources out of the XML.
 */
public class DictionaryXMLHelper extends XmlParserHelper {

    // Overrides constants in super class.
    private static final String sectionTag = "div0";
    private static final String lineTag = "entry";
    private static final String keyAttribute = "key";

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
     * Gets the number of alphabetical sections in the dictionary.
     * @param stream the inputStream of the dictionary.
     * @return the count of the alphabetical sections.
     * @throws XmlPullParserException
     * @throws IOException
     */
    public static int getSectionCount(InputStream stream) throws XmlPullParserException, IOException {
        int count = 0;
        try {
            XmlPullParser parser = initParser(stream);
            count = countSections(parser, sectionTag);

        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            MyApplication.logError(errorMessage);
        } finally {
            stream.close();
        }
        return count;
    }

    /**
     * Gets the keys of all the entries in this dictionary.
     * @param stream the inputStream of the dictionary.
     * @return the ArrayList of dictionary entries.
     * @throws XmlPullParserException
     * @throws IOException
     */
    public static ArrayList<String> getEntryHeaders(InputStream stream) throws XmlPullParserException, IOException {
        ArrayList<String> headers = new ArrayList<String>();

        try {
            XmlPullParser parser = initParser(stream);

            while (nextSection(parser, lineTag)) {
                parser.require(XmlPullParser.START_TAG, XmlParserHelper.ns, lineTag);

                String keyAttributeValue = parser.getAttributeValue(XmlParserHelper.ns, keyAttribute);
                if (keyAttributeValue != null) {
                    headers.add(keyAttributeValue);
                }
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
     * Gets the keys of all the entries in this dictionary from an entries resource.
     * @param stream the inputStream of the dictionary.
     * @return the ArrayList of dictionary entries.
     * @throws Exception
     */
    public static ArrayList<String> getEntryHeaders2(InputStream stream) throws Exception {
        ArrayList<String> headers = new ArrayList<String>();
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
    public static String getEntry(InputStream stream, String searchEntry) throws XmlPullParserException, IOException {
        String definition = null;

        try {
            XmlPullParser parser = initParser(stream);
            while (nextSection(parser, lineTag)) { // TODO: Generalize this loop for this and previous method.
                parser.require(XmlPullParser.START_TAG, XmlParserHelper.ns, lineTag);

                String keyAttributeValue = parser.getAttributeValue(XmlParserHelper.ns, keyAttribute);
                if (keyAttributeValue != null
                        && searchEntry.equals(keyAttributeValue)) {
                    break;
                }
            }

            definition = getLine(parser, lineTag);

        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            MyApplication.logError(errorMessage);
        } finally {
            stream.close();
        }

        return removeExtraneousCharacters(definition);
    }
}
