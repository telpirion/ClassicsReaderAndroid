/*
    Learn Latin for Windows Runtime
    Version 1.1
    Eric Schmidt
    2014-07-10

    Rev: 2014-11-15
*/
(function () {
    "use strict";

    var _dictionary,
        _keys = [],
        _dictionaryPromise,
        MINIMUM_STEM_LENGTH = 3;

    // Provide a set of flags for detecting
    var searchingFlags = {
        high: 1,
        low: -1,
        match: 0
    };

    // Add entries into the dictionary.
    function load(data) {

        // If stored data is passed in to the method,
        // don't open the local file.
        if (data) {
            _dictionary = data;
            _dictionaryPromise = WinJS.Promise.as(true);
            return;
        }

        // Get the entries from the data file in the assembly.
        var uri = new Windows.Foundation.Uri('ms-appx:///data/lewis.xml');
        _dictionaryPromise = Windows.Storage.StorageFile.getFileFromApplicationUriAsync(uri).
            then(function (file) {
                return Windows.Storage.FileIO.readTextAsync(file);
            }).
            then(function (text) {

                var parsed = false;
                var position = 0;
                _dictionary = {};

                while (!parsed) {
                    var entryStart = text.indexOf('\<entry', position);
                    if (entryStart < 0) {
                        parsed = true;
                    }
                    else {
                        var entryEnd = text.indexOf('\<\/entry', entryStart) + 8;
                        var entryText = text.slice(entryStart, entryEnd);

                        var newEntry = createEntry(entryText);
                        
                        _keys.push(newEntry.id);
                        _dictionary[newEntry.id] = newEntry.entry;
                        position = entryEnd;
                    }
                }
                return WinJS.Promise.as(true);
            });
    }

    // Create an entry in the dictionary object.
    function createEntry(entryText) {
        var keyStart = entryText.indexOf('key=\"') + 5;
        var entryKey = entryText.slice(keyStart, entryText.indexOf('\"', keyStart));

        return {
            id: entryKey,
            entry: entryText
        };
    }

    function parseEntryToHTML(result) {
        var parser = new DOMParser();
        var doc = parser.parseFromString(result, 'text/html');

        return toStaticHTML(doc.documentElement.innerHTML);
    }

    // Retrieve an entry from the dictionary.
    function getEntry(query) {
        var result;

        // If the user passed in a stem, look for
        // all matches that match the stem.
        if (query && (query.indexOf('-') > -1)) {

            // Check that the user has typed in a stem larger
            // than three characters.
            if (query.length < MINIMUM_STEM_LENGTH)
                return "You need to provide at least three characters for a word stem search.";
                
            result = getPartialEntry(query.replace("-", ""));
            return result.join("<br/><br/>");
        }

        // Check the dictionary for the query.
        if (_dictionary.hasOwnProperty(query)) {
            result = _dictionary[query];

            return parseEntryToHTML(result);
        }
        else if (_dictionary.hasOwnProperty(query + '1')) {
            var dictionaryString = "",
                foundAllEntries = false,
                count = 1;

            while (!foundAllEntries) {
                if (_dictionary.hasOwnProperty(query + count.toString())) {
                    dictionaryString += "<i>(" + count + ")</i>&nbsp";
                    var result = _dictionary[query + count.toString()];
                    dictionaryString += parseEntryToHTML(result);
                    dictionaryString += "<br/><br/>"
                    count++;
                }
                else {
                    foundAllEntries = true;
                }
            }
            return dictionaryString;
        }

        return null;
    }

    // Given a stem of a word, look for all matches
    // that might match the word.
    function getPartialEntry(query) {
     
        var rangeStart = 0,
        rangeEnd = _keys.length,
        checkPosition = Math.floor(rangeEnd / 2),
        numberOfChecks = 1;

        var checkChar;
        var queryChar = query.charAt(0);
        var found = false;
        var results = [];

        // A simple character comparison function
        function comparer(query, checkKey) {
            var isFound = ((checkKey.indexOf(query, 0)) > -1);

            if (isFound) {
                return searchingFlags.match;
            }
            else if (query > checkKey) {
                return searchingFlags.high;
            }
            else {
                return searchingFlags.low;
            }
        }
        
        // Use divide and conquer algorithm to find at least
        // one match for the stem.
        while ((!found) && (Math.pow(2, numberOfChecks) < _keys.length)) {
            
            checkChar = _keys[checkPosition];
            var checkResult = comparer(query, checkChar);

            if (checkResult == searchingFlags.high) {
                rangeStart = checkPosition;
                checkPosition = checkPosition + Math.floor((rangeEnd - checkPosition) / 2);
            }
            else if (checkResult == searchingFlags.low) {
                rangeEnd = checkPosition;
                checkPosition = checkPosition - Math.floor((checkPosition - rangeStart) / 2);
            }
            else { // We found the right section of the dictionary. 
                found = true;
            }

            numberOfChecks++;
        }

        // Now we need to narrow down the results to only the results
        // that match. Use divide & conquer again to find the lower bounds.
        var frontCheckArray = _keys.slice(rangeStart, checkPosition);
        var backCheckArray = _keys.slice(checkPosition, rangeEnd);

        // Find the front end.
        numberOfChecks = 1;
        rangeStart = 0;
        rangeEnd = frontCheckArray.length;
        checkPosition = Math.floor(frontCheckArray.length / 2);
      
        while (Math.pow(2, numberOfChecks) < frontCheckArray.length) {
            checkChar = frontCheckArray[checkPosition];
            var checkResult = comparer(query, checkChar);

            if (checkResult == searchingFlags.match) {
                var previousKey = frontCheckArray[checkPosition - 1];
                if (previousKey.indexOf(query, 0) == -1) {
                    results = results.concat(frontCheckArray.slice(checkPosition, frontCheckArray.length - 1));
                    break;
                }
                else {
                    rangeEnd = checkPosition;
                    checkPosition = checkPosition - Math.floor((checkPosition - rangeStart) / 2);
                }
            }
            else {
                rangeStart = checkPosition;
                checkPosition = checkPosition + Math.floor((rangeEnd - checkPosition) / 2);
            }
            numberOfChecks++;
        }

        // Find the back end.
        numberOfChecks = 1;
        rangeStart = 0;
        rangeEnd = backCheckArray.length;
        checkPosition = Math.floor(backCheckArray.length / 2);

        while (Math.pow(2, numberOfChecks) < backCheckArray.length) {
            checkChar = backCheckArray[checkPosition];
            var checkResult = comparer(query, checkChar);

            if (checkResult == searchingFlags.match) {
                var previousKey = ((checkPosition + 1) < backCheckArray.length) ?
                    backCheckArray[checkPosition + 1] : backCheckArray[checkPosition];

                if (previousKey.indexOf(query, 0) == -1) {
                    results = results.concat(backCheckArray.slice(0, checkPosition));
                    break;
                }
                else {
                    rangeStart = checkPosition;
                    checkPosition = checkPosition + Math.floor((rangeEnd - checkPosition) / 2);
                }
            }
            else {
                rangeEnd = checkPosition;
                checkPosition = checkPosition - Math.floor((checkPosition - rangeStart) / 2);
            }
            numberOfChecks++;
        }

        return results.map(function (i, n) {
            return parseEntryToHTML(_dictionary[i]);
        });
    }

    // Get a random entry out of the dictionary.
    function getRandomEntryAsync() {
        return _dictionaryPromise.then(function () {
            var randInt = Math.floor(Math.random() * _keys.length);
            var keyToGet = _keys[randInt];
            var vocabWord = getEntry(keyToGet);

            // Check to see if the value is null and
            // provide a substitute if it is.
            if (vocabWord == null) {
                return getRandomEntryAsync();
            }
            else {
                return WinJS.Promise.as(vocabWord);
            }
        });
    }

    // Get a reference to the entire dictionary.
    function getData() {
        return _dictionary;
    }

    WinJS.Namespace.define("Dictionary",
        {
            load: load,
            getRandomEntryAsync: getRandomEntryAsync,
            getEntry: getEntry,
            getData: getData
        });

})();
