/*
    Latin Reader for Windows Phone
    Version 1.1
    Eric Schmidt
    2014-11-24

*/
(function () {
    "use strict";

    WinJS.UI.Pages.define("/pages/home/home.html", {
        // This function is called whenever a user navigates to this page. It
        // populates the page elements with the app's data.
        ready: function (element, options) {

            populateBookList()

            // Get the 'word of the day'.
            getVocabWord();

            $('.hubSectionItem').each(function () {
                WinJS.UI.process(this);
                this.winControl.oninvoked = goToLocation;
            })

            // Add event handler to searchbox.
            var searchDictionary = $(".searchDictionary")[0];
            searchDictionary.onkeyup = function (evt) {
                if (evt.keyCode == 13){
                    var location = '/pages/dictionary/dictionary.html';
                    var query = evt.target.value;

                    if (query != "")
                        WinJS.Navigation.navigate(location, { query: query });
                }
            }

            WinJS.UI.processAll();
        }
    });

    // Gets a random vocabulary word from the dictionary.
    function getVocabWord() {
        var vocabSection = $(".vocabSection")[0];
        Dictionary.getRandomEntryAsync().
            then(function (vocabWord) {
                vocabSection.innerHTML = toStaticHTML(vocabWord);
            });
    }

    // Populate the library HubSections
    function populateBookList() {

        var libraryHubSection = $('.library')[0];
        var engLibrary = $(".englishLibrary")[0];
        for (var i = 0; i < Library.bookCount; i++) {
            var bookInfo = Library.books.getItem(i).data;

            // Create the Latin version of the item.
            var latinItem = createLatinSection(bookInfo, bookInfo.title, bookInfo.author, "lat");
            libraryHubSection.appendChild(latinItem.element);

            // Create the English version of the item.
            var englishItem = createLatinSection(bookInfo, bookInfo.engTitle, bookInfo.engAuthor, "eng");
            engLibrary.appendChild(englishItem.element);
        }
    }

    // Create items for the library sections
    function createLatinSection(bookInfo, title, author, lang) {

        var bookHTML = "<h3><i>" + title + "</i></h3>" +
            author;
        var bookDiv = document.createElement('div');
        bookDiv.innerHTML = toStaticHTML(bookHTML);
        bookDiv.setAttribute('data-choice', JSON.stringify(bookInfo));
        bookDiv.setAttribute('data-lang', lang);
        WinJS.Utilities.addClass(bookDiv, 'hubSectionItem');

        var bookItem = new WinJS.UI.ItemContainer(bookDiv, {
            oniteminvoked: goToLocation
        });

        return bookItem;
    }

    // When a hub section header is invoked, go to the appropriate page.
    function goToLocation(evt) {
        var location = "/pages/readingView/readingView.html",
            options;

        if (evt.currentTarget.dataset.choice) {
            options = {
                lang: evt.currentTarget.dataset.lang,
                book: JSON.parse(evt.currentTarget.dataset.choice)
            };
        }

        WinJS.Navigation.navigate(location, options);
    }
})();
