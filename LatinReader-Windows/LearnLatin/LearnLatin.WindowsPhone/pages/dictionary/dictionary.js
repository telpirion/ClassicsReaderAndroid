/*
    Learn Latin for Windows Phone
    Version 1.1
    Eric Schmidt
    2014-11-25

*/
(function () {
    "use strict";

    WinJS.UI.Pages.define("/pages/dictionary/dictionary.html", {

        ready: function (element, options) {
            options && options.query && getEntry(options.query);

            $(".searchDictionary")[0].onkeyup = function (evt) {
                if (evt.keyCode == 13) {
                    var query = evt.target.value;

                    if (query != "") getEntry(query);
                }
            }
        }
    });

    function getEntry(query) {
        var result = Dictionary.getEntry(query);

        var displayedResult = result ? result :
            "Cannot find matching entry";
        $('.output').html(displayedResult);
    }
})();
