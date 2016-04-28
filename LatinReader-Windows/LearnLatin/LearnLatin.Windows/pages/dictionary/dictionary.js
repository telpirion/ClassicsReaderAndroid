/*
    Learn Latin for Windows Runtime
    Version 1.1
    Eric Schmidt
    2014-07-10

*/
(function () {
    "use strict";

    WinJS.UI.Pages.define("/pages/dictionary/dictionary.html", {
        ready: function (element, options) {

            options && options.query && getEntry(options.query);

            $('#searchDictionary')[0].winControl.onquerysubmitted = function (evt) {
                var query = evt.detail.queryText;
                getEntry(query);
            }
        }
    });

    function getEntry(query) {
        var result = Dictionary.getEntry(query);

        var displayedResult = result ? result :
            "Cannot find matching entry";
        $('#output').html(displayedResult);
    }
})();
