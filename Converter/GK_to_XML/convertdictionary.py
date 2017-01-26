#!/usr/bin/env python
# -*- coding: utf-8 -*-

import sys
import xml.etree.ElementTree as ET
import json
import os
import re
import convert

def convertDictionary(e):
    convert.initDictionary()
    newEntry = convert.convertWord(e)
    return newEntry

def main():
    try:

        if len(sys.argv) > 1:
            filePath = sys.argv[1]

            # Open source file and get root XML element
            f = open(filePath, 'r')
            dictionaryRoot = convert.openXMLSource(f)
            entries = dictionaryRoot.iter("entry")

            # Create new dictionary entry XML file
            fileName = os.path.basename(f.name)
            newRoot = ET.Element("entries")

            for entry in entries:
                newEntry = convertDictionary(entry.text)
                el = ET.SubElement(newRoot, "entry")
                el.text = newEntry
                el.attrib["latin"] = entry.text
                print (el.text)

            f.close()

            # Write the resulting XML to file
            newTree = ET.ElementTree(newRoot)
            newFileName = "output/gk_" + fileName
            newTree.write(newFileName, encoding="UTF-8", xml_declaration=True)

            convert.reformatXMLFile(newFileName, ["<entry"])

            print ("Conversion complete")

    except RuntimeError:
        print ("There was an error opening the source file")

main()