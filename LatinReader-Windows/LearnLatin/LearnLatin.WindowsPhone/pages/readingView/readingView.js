/*
    Learn Latin for Windows Phone
    Version 1.1
    Eric Schmidt
    2014-11-25

*/
(function () {
    "use strict";

    var work, currentPage, currentBook;
    var app = WinJS.Application;
    var view, lang;

    WinJS.UI.Pages.define("/pages/readingView/readingView.html", {

        ready: function (element, options) {
            view = element;

            if (!options || !options.book) {
                throw new WinJS.ErrorFromName("Error loading book", "Could not find the book specified.");
            }

            // Add the events to the book text.
            registerSwipeEvents($('.bookText')[0]);
            $('.bookText')[0].addEventListener('contextmenu', showContextMenu);

            var bookInfo = options.book;
            lang = options.lang;

            // Set the reading area for the appropriate language setting.
            toggleLanguage(bookInfo);

            Library.getBook(bookInfo).then(function (doc) {
                work = doc;

                currentBook = work.currentBook;
                currentPage = work.currentPage;

                Notes.openNotes(work.id);

                // Load the user's current position in the work.
                advancePage();

            });
        }
    });

    /* PAGE UTILITIES */

    // Store the user's notes, get the next page in the work,
    // and update the reading UI.
    function advancePage(evt, delta) {

        // If the call comes from the app bar, determine get
        // the new page value from the event.
        if (evt && (evt.target.type != "button")) {
            currentPage = Number(evt.target.value) - 1;
        }
        else if (evt && (evt.target.type == "button")) {
            currentPage++;
        }

        // If the call comes from the click event on the page,
        // determine whether the page is going forward or backwards.
        if (delta) {
            currentPage = currentPage + delta;
        }

        // Store the user's position and notes.
        storeUserData();

        // Update the reading page UI.
        updateReadingPage();
    }

    // Update the app.sessionState data with the user's info.
    function storeUserData() {
        work.savePosition();
    }

    /* UI EVENT HANDLERS */

    // Set up event handlers to click event on the reading page.
    function registerSwipeEvents(target) {
        var targetRect = target.getBoundingClientRect();
        var readingX = (targetRect.left + targetRect.width) / 2;

        var detectPageTurn = function (x) {
            (x < readingX) ?
                advancePage(null, -1) :
                advancePage(null, 1);
        }

        target.addEventListener('click', function (evt) {
            var x = evt.x;
            detectPageTurn(x);
        });
    }

    // Update the reading page UI.
    function updateReadingPage() {

        var content = work.getContent(currentBook, currentPage);

        currentBook = work.currentBook;
        currentPage = work.currentPage;

        $('.bookNum').text(currentBook + 1)
        $('.pageNum').text(currentPage + 1);

        if (lang == "eng") {
            Library.getTranslationLines(work, currentBook, currentPage).then(function (text) {
                $('.bookText').text(text);
            });
        } else {
            $('.bookText').text(content);
        }
    }

    // Change the UI to reflect the English reading experience.
    function toggleLanguage(bookInfo) {
        if (lang == "eng") {
            $('.lang-page')[0].textContent = "Book";
            $(".workTitle").text(bookInfo.engTitle);
            $(".author").text(bookInfo.engAuthor);
        }
        else {
            $(".workTitle").text(bookInfo.title);
            $(".author").text(bookInfo.author);
        }
    }

    // Handle the show context menu event.
    function showContextMenu(evt) {

        var menuText = (lang == "lat") ?
            "See translation" : "See source text";

        // Calculate midpoint to display window.
        var midPointX = (window.innerWidth / 2) - 150,
            midPointY = (window.innerHeight / 2) - 150;

        // Create a menu and add commands with callbacks.
        var menu = new Windows.UI.Popups.PopupMenu();
        menu.commands.append(new Windows.UI.Popups.UICommand(menuText, function () {
             
            if (lang == "lat") {
                lang = "eng";
            }
            else {
                lang = "lat";
            }

            updateReadingPage();
        }));

        menu.showAsync({ x: evt.x, y: evt.y });
    }

})();
