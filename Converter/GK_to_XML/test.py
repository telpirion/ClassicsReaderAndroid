import json
import convert

# Test the conversion function
def testConvertCharacters():
    print ("testConvertCharacters")

    convert.initDictionary()

    with open('latin_greek_text_conversion.json') as json_data:

        d = json.load(json_data)

        print (d["a"])
        print (convert.convertCharacter("b"))
        print (convert.convertCharacter("a\\"))
        print (convert.convertCharacter("*a"))

def testOpenXMLSource():
    print ("testOpenXMLSource")

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

def testIsDiacritical():
    convert.initDictionary()

    print ("Can find diacritical correctly?")
    print (convert.isDiacritical("+"))
    print (convert.isDiacritical('+'))

    print ("Can reject alpha character correctly?")
    print (convert.isDiacritical("a") == False)
    print (convert.isDiacritical('a') == False)

# Run test
testConvertCharacters()
testOpenXMLSource()
testIsDiacritical()