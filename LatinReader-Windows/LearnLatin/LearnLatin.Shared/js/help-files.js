/*
    Latin Reader for Windows and Windows Phone
    help files
    copyright Eric Schmidt, 2014.

    Rev: 2015-02-04
*/
(function () {

    var items = [
        {
            title: "Reading a Latin or English text",
            info: "You can open a text for reading, Latin or English, in a couple of ways:" +
                "<ul><li>From the home page, under the hub section for Latin works or English works, select the work that you want to read.</li>" +
                "<li>From the <b>Library (Latin)</b> or <b>Library (English)</b> page, select the work that you want to read.</li>" +
                "<li><b>Windows</b> From a text that you are currently reading, right-click the page and select see translation.</li>" +
                "<li><b>Windows Phone</b> From a text that you are currently reading, tap and hold the screen and select either <b>see source text</b> or <b>see translation</b></li></ul>"
        },
        {
            title: "Searching for an exact word in the dictionary",
            info: "If you know the exact dictionary entry, as would be found in a standard Latin dictionary, for a Latin word you can enter that word into the search box on the <b>Dictionary</b> "+
                "page or <b>Dictionary</b> pop-up on the reading view.<br/><br/>" +
                "For example, to find the dictionary entry for the verb 'fero, ferre, tuli, latum', you enter <i>fero</i> into the dictionary search box. To find the noun 'nauta, nautae', you enter <i>nauta</i>."
        },
        {
            title: "Searching for a word stem in the dictionary",
            info: "On the <b>Dictionary</b> page or <b>Dictionary</b> pop-up, you can search for a word stem (or search for a partial match to a word) by entering the stem of the word that you want to look for and add a '-' at the end.<br/><br/>" +
                "For example, if you wanted to find all the words that start with 'iac', on the <b>Dictionary</b> page, enter 'iac-' into the search box. The app returns all words that match that stem."
        },
        {
            title: "Browsing the collection of texts",
            info: "The Latin Reader has a collection of works in both Latin and English. You can browse the collected works as grouped by language.<br/><br/>" +
                "<ul><li>To access the collection of texts in Latin, right-click the top edge of the app and select <b>Library (Latin)</b>.</li>" +
                "<li>To access the collection of texts in English, right-click the top edge of the app and select <b>Library (English)</b>.</li></ul>"
        }

    ];

    var _helpItems = new WinJS.Binding.List(items);

    WinJS.Namespace.define("Help", {
        assets: _helpItems
    });

})();