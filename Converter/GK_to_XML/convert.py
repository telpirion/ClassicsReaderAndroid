import sys
import xml.etree.ElementTree as ET
import json

# Python XML: https://docs.python.org/2/library/xml.etree.elementtree.html

__characterHash = {}

# Convert a character using the JSON key.
def convertCharacter(c):
    return __characterHash[c]

# Open an XML file and return the root element.
def openXMLSource(filename):
    tree = ET.parse(filename)
    root = tree.getroot()
    return root

# Open the JSON conversion file and add key/value
# pairs to the character dictionary.
def initDictionary():
    with open('latin_greek_text_conversion.json') as json_data:

        d = json.load(json_data)

        for k, v in d.items():
            __characterHash.update({k:v})

# Create a new DOM tree for the updated text.
# Returns ElementTree
def initNewXMLFile():
    root = ET.Element("work")
    body = ET.SubElement(root, "body")
    ET.SubElement(body, "text")
    tree = ET.ElementTree(root)
    return tree

# Transfers the metadata from the old file
# to the new one.
def updateNewXMLHeader(source, newFile):
    sourceHeader = source.find("header")
    newFile.insert(0, sourceHeader)

# Updates a XML tree with a new entry
def updateNewXMLFile(element, newElement, value):
    el = ET.SubElement(element, newElement)
    if value != None:
        el.text = value

    return el

# Iterate over each book in the source.
def iterateBooks(source, newText):
    books = source[1][0].iter("div1")

    for book in books:

        # Create a new div element in the newText.
        newDiv = updateNewXMLFile(newText, "div1", None)
        newDiv.attrib["n"] = book.attrib["n"]

        # Convert the book and add a new div to the
        # output XML.
        convertBook(book, newDiv)

# Translate the line in one book to Greek Polytonic.
def convertBook(div, newDiv):
    lines = div.findall("p")
    counter = 0

    for line in lines:
        if line != None:

            words = line.text.split()
            para = iterateWords(words)

            newParaEl = updateNewXMLFile(newDiv, "p", para)
            counter +=1

    newDiv.attrib["lines"] = str(counter)

# Iterate over each word in an array of words,
# converting the characters from Latin chars to Greek.
def iterateWords(words):
    para = ""

    for word in words:
        rewrittenWord = convertWord(word)
        para += rewrittenWord + " "

    return para

# Convert a word from Latin chars to Greek polytonic
# characters.
def convertWord(word):
    convertedWord = ""
    holdVowelChar = ""
    holdCapital = ""

    for i in range(len(word)):
        c = word[i]

        if c in __characterHash:
            # Convert the character
            #convertedWord += convertCharacter(c)

            # Resolving a capital letter.
            if holdCapital != "":
                holdCapital += c
                convertedWord += resolve(holdVowelChar)
                convertedWord += resolve(holdCapital)

            else:
                convertedWord += resolve(holdVowelChar)
                convertedWord += resolve(holdCapital)
                convertedWord += convertCharacter(c)

            holdCapital = ""
            holdVowelChar = ""

        elif c == '*':
            # Build the holdCapital value
            holdCapital += "*"

        elif isDiacritical(c):
            # Build the holdVowelChar value
            if holdCapital != "":
                holdCapital += c

            elif holdVowelChar != "":
                holdVowelChar += c;

            # Started a new vowel with diacriticals.
            # Adjust the holdVowelChar to get the previous
            # alpha numeric and adjust the converted word.
            else:
                holdVowelChar += word[i - 1] + c
                convertedWord = convertedWord[0:(len(convertedWord) - 1)]

        else:
            # Don't know what to do with this
            # character; output to the terminal
            convertedWord += c
            print (c)

    return convertedWord

# Determine whether the current character is a diacritical
# (rough breathing mark, accent, iota subscript, umlaut/dieresis)
def isDiacritical(character):
    return ")(\\/=|+".find(character) > -1

# Resolves a hold capital or hold vowel
def resolve(s):

    newChar = ""

    if (s != "") and (s in __characterHash):
        newChar = convertCharacter(s)
    elif (s != ""):
        newChar = s

    if newChar == None:
        return ""

    return newChar

def main():

    # Determine that we're actually converting,
    # not just testing a method
    if len(sys.argv) > 1:
        source = openXMLSource('hom_il_gk.xml')

        # Populate the dictionary with entries from JSON
        initDictionary()

        # Create new XML file
        newFile = initNewXMLFile()
        newRoot = newFile.getroot()
        newTextElement = newRoot.find("body").find("text")
        updateNewXMLHeader(source, newRoot)

        # Start conversion process
        iterateBooks(source, newTextElement)

        # Write the resulting XML to file
        newFile.write("output.xml", encoding="UTF-8", xml_declaration=True)

        print ("Conversion complete")

#############################

# Run the program
main()