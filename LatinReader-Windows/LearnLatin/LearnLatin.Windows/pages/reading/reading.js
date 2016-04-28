///<reference path="/js/Library.js" />
(function () {
    "use strict";

    var lang;

    WinJS.UI.Pages.define("/pages/reading/reading.html", {
        ready: function (element, options) {

            var list = $("#bookList")[0];
            
            // Switch the template if the user clicked an
            // option in English.
            lang = (options && (options == "eng")) ? "eng" : "lat";
            var listTemplate = (lang == "eng") ?
                element.querySelector('.eng') : element.querySelector('.lat')

            element.querySelector('.lang').textContent = (lang == "eng") ?
                "English" : "Latin";

            var bookList = new WinJS.UI.ListView(list, {
                itemTemplate: listTemplate,
                itemDataSource: Library.books.dataSource,
                oniteminvoked: goToBook  
            });

        },

        unload: function () {

        },

        updateLayout: function (element) {
            var list = $("#bookList")[0].winControl.forceLayout();
            WinJS.UI.processAll();
        }
    });

    function goToBook(evt) {
        var index = evt.detail.itemIndex;
        var item = Library.books.getItem(index);
        var options = { book: item.data, lang: lang };

        WinJS.Navigation.navigate("/pages/readingView/readingView.html", options);
    }
})();
