#!/usr/bin/env python
# -*- coding: utf-8 -*-

import sys
import xml.etree.ElementTree as ET
import json
import os
import re

# Python XML: https://docs.python.org/2/library/xml.etree.elementtree.html

__characterHash = {}

# Convert a character using the JSON key.
def convertCharacter(c):
    return __characterHash[c]

# Open an XML file and return the root element.
def openXMLSource(file):
    try:
        tree = ET.parse(file)
        root = tree.getroot()
        return root
    except RuntimeError:
        print ("There was an error opening the source file")

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

            words = line.text
            if words != None:
                para = iterateWords(words.split())

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

        # Convert the character if it's known.
        if c in __characterHash:

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

        # The character is the beginning of a capital letter.
        elif c == '*':
            # Build the holdCapital value
            holdCapital += "*"

        # The character is a diacritical mark.
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

        # Don't know what to do with this
        # character; convert as-is and output to the terminal.
        else:
            convertedWord += c
            print (c)

    convertedWord += resolve(holdVowelChar)
    convertedWord += resolve(holdCapital)

    if convertedWord.find(u"σ") > -1:
        convertedWord = convertFinalSigma(convertedWord)

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

# Replace any final sigma characters with the correct version
# of the character.
def convertFinalSigma(convertedWord):
    trimmedWord = convertedWord.replace(" ", "")
    trimmedWordLen = len(trimmedWord)
    lastChar = trimmedWord[trimmedWordLen - 1]
    secondToLast = trimmedWord[trimmedWordLen - 2]
    cleanedWord = ""

    if lastChar == u"σ":
        cleanedWord = trimmedWord[:trimmedWordLen - 1] + u"ς"

    if (secondToLast == u"σ") and (".:;,".find(lastChar) > -1):
        cleanedWord = trimmedWord[:trimmedWordLen - 2] + u"ς" +lastChar

    return cleanedWord

# Remove all of the garbage <milestone> tags from the input file.
def cleanFile(f):

    data = f.read()

    exp = "<milestone\W\S+\W\S+\W\S+/>"
    regex = re.compile(exp)
    matches = regex.findall(data)

    removedItems = ""
    for match in matches:
        removedItems += match + "\n"

    cleanData = re.sub(exp, "", data)

    cleanFileName = "output/linted_" + os.path.basename(f.name)
    removedFileName = "output/removed_" + os.path.basename(f.name)

    f.close()

    with open(cleanFileName, 'w') as cleanFile:
        cleanFile.write(cleanData)

    # Save all of the removed tags to a file to ensure
    # that no necessary data was deleted.
    with open(removedFileName, 'w') as removed:
        removed.write(removedItems)

    return cleanFileName

def main():

    try:
        # Determine that we're actually converting,
        # not just testing a method
        if len(sys.argv) > 1:
            filePath = sys.argv[1]

            f = open(filePath, 'r')
            fileName = os.path.basename(f.name)
            g = cleanFile(f)

            source = openXMLSource(open(g, 'r'))

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
            newFileName = "output/gk_" + fileName
            newFile.write(newFileName, encoding="UTF-8", xml_declaration=True)

            f.close()
            print ("Conversion complete")

    except RuntimeError:
        print ("There was an error opening the source file")

#############################

# Run the program
main()