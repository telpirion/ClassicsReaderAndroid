/*
    Learn Latin for Windows Runtime
    Version 1.0
    Eric Schmidt
    2014-07-10
*/
(function () {
    "use strict";

    var _notes = {},
        app = WinJS.Application,
        fileName = "notes.txt",
        isLocked;

    function openNotes(workId) {

        fileName = workId + "_" + fileName;

        if (app.local.exists(fileName)) {
            isLocked = true;
            app.local.readText(fileName).
                then(function (rawNotes) {
                    if (rawNotes != undefined) {
                        _notes = JSON.parse(rawNotes);
                        isLocked = false;
                    }
                });
        }
    }

    function getPageNotes(book, page) {
        var key = createNotesKey(book, page);
        if (_notes[key]) {
            return toStaticHTML(_notes[key]);
        }
        return "";
    }

    function saveNotes(book, page, notes) {
        if (!isLocked) {
            var key = createNotesKey(book, page);
            _notes[key] = notes;
            serialize();
        }
    }

    /*
        Writes the notes to local storage.
        @return {Promise}: A promise representing the work being done.
    */
    function serialize() {
        var savedNotes = JSON.stringify(_notes);
        app.local.writeText(fileName, savedNotes);
    }

    function createNotesKey(book, page) {
        return book.toString() + "." + page.toString();
    }

    WinJS.Namespace.define("Notes", {
        openNotes: openNotes,
        saveNotes: saveNotes,
        serialize: serialize,
        getPageNotes: getPageNotes
    });
})();