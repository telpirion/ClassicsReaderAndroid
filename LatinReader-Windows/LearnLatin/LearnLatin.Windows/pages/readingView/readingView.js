/*
    Learn Latin for Windows Runtime
    Version 1.1
    Eric Schmidt
    2014-07-10

    Rev: 2014-11-15
*/
(function () {
    "use strict";

    var work, currentPage, currentBook;
    var app = WinJS.Application;
    var view, lang;

    // Page control definition.
    WinJS.UI.Pages.define("/pages/readingView/readingView.html", {
        ready: function (element, options) {

            view = element;

            if (!options || !options.book) {
                throw new WinJS.ErrorFromName("Error loading book", "Could not find the book specified.");
            }

            registerSwipeEvents($('#bookText')[0]);

            var bookInfo = options.book;
            lang = options.lang;

            // Set the reading area for the appropriate language setting.
            toggleLanguage(bookInfo);

            $('#bookText')[0].addEventListener('contextmenu', showContextMenu);
            resources.ondblclick = function (evt) {
                WinJS.Utilities.empty(evt.target);
            }

            $('#bookText')[0].addEventListener('overflow', function (ev) {
                ev;
            });

            $('#readingViewAppBar button').each(function () {
                this.winControl.onclick = appBarClick;
            });

            Library.getBook(bookInfo).then(function (doc) {
                work = doc;

                currentBook = work.currentBook;
                currentPage = work.currentPage;

                Notes.openNotes(work.id);
                
                // Load the user's current position in the work.
                advancePage();

                // Set the event handler for the pages AppBarCommand (range).
                $('#pages').attr('max', work.books[currentBook].length)
                $('#pages')[0].onmouseup = advancePage;
            });
        },

        unload: function () {
            storeUserData();
        }
    });

    /* PAGE UTILITIES */

    // Store the user's notes, get the next page in the work,
    // and update the reading UI.
    function advancePage(evt, delta) {

        // Remove any existing floatie.
        clearFloatie();

        // Store the user's position and notes.
        storeUserData();

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

        // Update the reading page UI.
        updateReadingPage();
    }

    // Update the app.sessionState data with the user's info.
    function storeUserData() {
        saveNotes();
        work.savePosition();
    }

    // Save the current HTML for the notes.
    function saveNotes() {
        var resources = $("#resources")[0];
        var previousNotes = resources.innerHTML;
        
        if (previousNotes == "") {
            return;
        }
        else {
            Notes.saveNotes(currentBook, currentPage, previousNotes);
        }
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

        WinJS.Utilities.empty($("#resources")[0]);
        $('#resources').html(Notes.getPageNotes(currentBook, currentPage));
        $('#bookNum').text(currentBook + 1)
        $('#pageNum').text(currentPage + 1);
        $("#pages")[0].value = currentPage + 1;
        $('#pages').attr('max', work.books[work.currentBook].length);

        if (lang == "eng") {
            Library.getTranslationLines(work, currentBook, currentPage).then(function (text) {
                $('#bookText').text(text);
            });
        } else {
            $('#bookText').text(content);
        }
    }

    // Remove any existing floaties from the reading page.
    function clearFloatie() {
        var oldFloatie = page.querySelector('.floating-menu');
        oldFloatie && oldFloatie.winControl && oldFloatie.winControl.dispose();
    }

    // Change the UI to reflect the English reading experience.
    function toggleLanguage(bookInfo) {
        if (lang == "eng") {
            $('.lang-page')[0].textContent = "Book";
            $("#workTitle").text(bookInfo.engTitle);
            $('#author').text(bookInfo.engAuthor);
        }
        else {
            $("#workTitle").text(bookInfo.title);
            $('#author').text(bookInfo.author);
        }
    }

    // Handle AppBar click events and determine
    // which AppBarCommand raised the event.
    function appBarClick(evt) {
        var command = evt.target.id;

        // Calculate midpoint to display window.
        var midPointX = (window.innerWidth / 2) - 150,
            midPointY = (window.innerHeight / 2) - 150;

        switch (command) {
            case "dictionary":
                openDictionary(midPointX, midPointY);
                break;
            case "translation":
                openTranslation(midPointX, midPointY);
                break;
            case "notes":
                openNotes();
                break;
            case "nextPage":
                advancePage(evt);
                break;
        }
    }

    // Handle the show context menu event.
    function showContextMenu(evt) {

        // Calculate midpoint to display window.
        var midPointX = (window.innerWidth / 2) - 150,
            midPointY = (window.innerHeight / 2) - 150;

        // Create a menu and add commands with callbacks.
        var menu = new Windows.UI.Popups.PopupMenu();
        menu.commands.append(new Windows.UI.Popups.UICommand("Lookup word", function () {
            openDictionary(midPointX, midPointY);
        }));
        menu.commands.append(new Windows.UI.Popups.UICommand("See translation", function () {
            openTranslation(midPointX, midPointY);
        }));
        menu.commands.append(new Windows.UI.Popups.UICommand("Take notes", openNotes));

        menu.showAsync({ x: evt.x, y: evt.y });
    }

    // Handle the AppBar and context menu notes commands.
    function openNotes() {
        var resources = $('#resources')[0];
        var notesText = resources.innerHTML;

        (notesText == "") && $(resources).append("[Type your notes here.]");
        $(resources)[0].focus();
    }

    // Handle the AppBar and context menu dictionary commands.
    function openDictionary(x, y) {

        // Remove any existing floaties.
        clearFloatie();

        var floatie = new CustomControls.Floatie(null, {
            left: x,
            top: y,
            title: "Dictionary"
        });
        page.appendChild(floatie.element);

        var searchBox = new WinJS.UI.SearchBox();
        searchBox.onquerysubmitted = function (evt) {
            var result = Dictionary.getEntry(evt.detail.queryText);
            var displayedResult = result ? result :
                "Cannot find matching entry";
            $('#output').html(displayedResult);
        }
        $(floatie.contentElement).append(searchBox.element);
        $(floatie.contentElement).append("<br/><div id='output'style='overflow:auto;height:300px;font-size:0.8em'></div>");
   
        // Set the keyboard focus to the searchbox.
        searchBox.focusOnKeyboardInput = true;
    }

    // Handle the AppBar and context menu translation commands.
    function openTranslation(x, y) {

        // Remove any existing floatie.
        clearFloatie();

        var floatie = new CustomControls.Floatie(null, {
            left: x,
            top: y,
            title: "Translation"
        });
        page.appendChild(floatie.element);
        $(floatie.contentElement).
            attr("style", "height:350px;overflow:auto;font-size:0.8em");

        Library.getTranslationLines(work, currentBook, currentPage).then(function (content) {
            $(floatie.contentElement).text(content);
        });
    }
})();
