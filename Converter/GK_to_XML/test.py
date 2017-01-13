#!/usr/bin/env python
# -*- coding: utf-8 -*-
import convert
import re

# Test the conversion function
def testConvertCharacters():
    print ("testConvertCharacters")

    print (convert.convertCharacter("b"))
    print (convert.convertCharacter("a\\"))
    print (convert.convertCharacter("*a")) #
    print (convert.convertCharacter("o(\\"))

def testOpenXMLSource():
    print ("testOpenXMLSource:")

    root = convert.openXMLSource('hom_il_gk.xml')

    print (root.tag)

    for child in root:
        print child.tag

        for neighbor in child.iter('body'):
            print neighbor.tag
            print neighbor.text

    # Prints every line in the Iliad XML text
    for c in root[1][0].iter('p'):
        print c.text

def testConvertWord():
    print ("testConvertWord:")

    word = "o(\s"

    print(convert.convertWord(word))

def testIsDiacritical():
    print ("testIsDiacritical:")

    print ("Can find diacritical correctly?")
    print (convert.isDiacritical("+"))
    print (convert.isDiacritical('+'))

    print ("Can reject alpha character correctly?")
    print (convert.isDiacritical("a") == False)
    print (convert.isDiacritical('a') == False)

def testConvertFinalSigma():
    print ("testConvertFinalSigma:")

    print(convert.convertFinalSigma(u"ὃσ"))
    print(convert.convertFinalSigma(u"ὃσ;"))

def testCleanFile():
    print ("testCleanFile")

    f = open("lint_test.txt")
    data = f.read()

    base = "milestone" # results: 7
    exp1 = '<milestone([\W][\S]+)' # results: 7
    exp2 = '<milestone(\W[A-z]\S*)+/>' # results: 5
    exp3 = 'milestone([\W][\S]+)' # results: 7
    exp4 = "<milestone\W\S+\W\S+\W\S+/>" # results: 7 complete

    regex = re.compile(exp4)
    matches = regex.findall(data)

    print (len(matches))

    removedItems = ""
    for match in matches:
        print (match)
        removedItems += match + "\n"

    cleanData = re.sub(exp4, "", data)

    #print (cleanData)
    f.close()

    with open("linted_test.txt", 'w') as cleanFile:
        cleanFile.write(cleanData)

    with open("linted_test_removed.txt", 'w') as removed:
        removed.write(removedItems)

# Set up module for tests.
def main():
    convert.initDictionary()

# Run test
main()
#testConvertCharacters()
#testOpenXMLSource()
#testIsDiacritical()
#testConvertWord()
#testConvertFinalSigma()
testCleanFile()
