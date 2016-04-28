/*
    Learn Latin for Windows Runtime
    Version 1.1
    Eric Schmidt
    2014-07-10

    Rev: 2014-11-15
*/
(function () {
    "use strict";

    WinJS.UI.Pages.define("/pages/home/home.html", {
        ready: function (element, options) {

            // Apply event handler to hub section headers.
            $('#mainHub')[0].winControl.onheaderinvoked = goToLocation;

            populateBookList()

            // Get the 'word of the day'.
            getVocabWord();

            $('.hubSectionItem').each(function () {
                WinJS.UI.process(this);
                this.winControl.oninvoked = goToLocation;
            })

            // Add event handler to searchbox.
            var searchDictionary = $("#searchDictionary")[0];
            WinJS.UI.process(searchDictionary);
            searchDictionary.winControl.onquerysubmitted = function (evt) {
                var location = '/pages/dictionary/dictionary.html';
                var query = evt.detail.queryText;

                WinJS.Navigation.navigate(location, { query: query });
            }
        }
    });

    // Gets a random vocabulary word from the dictionary.
    function getVocabWord() {
        var vocabSection = $(".vocab")[0];
        Dictionary.getRandomEntryAsync().
            then(function (vocabWord) {
                vocabSection.winControl.contentElement.innerHTML = toStaticHTML(vocabWord);
            });
    }

    // Populate the library HubSections
    function populateBookList() {

        var libraryHubSection = $('#library')[0];
        var engLibrary = $(".englishLibrary")[0];
        for (var i = 0; i < 8; i++) {
            var bookInfo = Library.books.getItem(i).data;

            // Create the Latin version of the item.
            var latinItem = createLatinSection(bookInfo, bookInfo.title, bookInfo.author, "lat");
            libraryHubSection.winControl.contentElement.appendChild(latinItem.element);

            // Create the English version of the item.
            var englishItem = createLatinSection(bookInfo, bookInfo.engTitle, bookInfo.engAuthor, "eng");
            engLibrary.winControl.contentElement.appendChild(englishItem.element);
        }
    }

    // Create items for the library sections
    function createLatinSection (bookInfo, title, author, lang) {
        
        var bookHTML = "<h3><i>" + title + "</i></h3>" +
            author;
        var bookDiv = document.createElement('div');
        bookDiv.innerHTML = toStaticHTML(bookHTML);
        bookDiv.setAttribute('data-choice', JSON.stringify(bookInfo));
        bookDiv.setAttribute('data-lang', lang);
        bookDiv.setAttribute('data-location', "/pages/readingView/readingView.html");
        WinJS.Utilities.addClass(bookDiv, 'hubSectionItem');

        var bookItem = new WinJS.UI.ItemContainer(bookDiv, {
            oniteminvoked: goToLocation
        });

        return bookItem;
    }

    // When a hub section header is invoked, go to the appropriate page.
    function goToLocation(evt) {
        var location, options;

        if (evt.type == 'headerinvoked') {
            location = evt.detail.section.element.dataset.location;
            options = evt.detail.section.element.dataset.lang;
        }
        else {
            location = evt.currentTarget.dataset.location;
            if (evt.currentTarget.dataset.choice) {
                options = {
                    lang: evt.currentTarget.dataset.lang,
                    book: JSON.parse(evt.currentTarget.dataset.choice)
                };
            }
        }
            
        WinJS.Navigation.navigate(location, options);
    }
})();
