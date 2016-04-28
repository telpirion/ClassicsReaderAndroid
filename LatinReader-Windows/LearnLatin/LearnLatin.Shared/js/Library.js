/*
    Learn Latin for Windows Runtime and Windows Phone
    Version 1.1
    Eric Schmidt
    2014-07-10

    Rev: 2015-02-03
*/
(function () {

    var _dataURI = '/data/';
    var _books = [ 
        {
            id: "CaesarBG",
            title: "De Bello Gallico", author: "C. Julius Caesar",
            engTitle: "The Gallic Wars", engAuthor: "Caesar",
            location: encodeURI(_dataURI + "caes.bg_lat.xml"), 
            translation: encodeURI(_dataURI + "caes.bg_eng.xml")
        },
        /*{ // Disabled Catullus on 2015-02-13. Translation does not match source lines numbers.
            id: "Cat",
            title: "Carmina", author: "C. Valerius Catullus",
            engTitle: "Carmina", engAuthor: "Catullus",
            location: encodeURI(_dataURI + "cat_lat.xml"),
            translation: encodeURI(_dataURI + "cat.burton_eng.xml")
        },*/
        {
            id: "CicOff",
            title: "De Officiis", author: "M. Tullius Cicero",
            engTitle: "On Duties", engAuthor: "Cicero",
            location: encodeURI(_dataURI + "cic.off_lat.xml"),
            translation: encodeURI(_dataURI + "cic.off_eng.xml")
        },
        {
            id: "Lucretius",
            title: "De Rerum Natura", author: "T. Lucretius Carus",
            engTitle: "On the Nature of Things", engAuthor: "Lucretius",
            location: encodeURI(_dataURI + "lucretius_lat.xml"),
            translation: encodeURI(_dataURI + "lucretius_eng.xml")
        },
        {
            id:'OvidM',
            title: "Metamorphosis", author: "P. Ovidius Naso",
            engTitle: "Metamorphosis", engAuthor: "Ovid",
            location: encodeURI(_dataURI + "ovid.met_lat.xml"),
            translation: encodeURI(_dataURI + "ovid.met_eng.xml")
        },
        {
            id:'Petronius',
            title: "Satyricon, Fragmenta, and Poems", author: "G. Petronius Arbiter",
            engTitle: "Satyricon, Fragmenta, and Poems", engAuthor: "Petronius",
            location: encodeURI(_dataURI + "petr_lat.xml"),
            translation: encodeURI(_dataURI + "petr_eng.xml")
        },
        {
            id: "SalJug",
            title: "Bellum Jugurthinum", author: "C. Sallusti Crispi",
            engTitle: "The Jugurthine War", engAuthor: "Sallust",
            location: encodeURI(_dataURI + "sallust.jugur_lat.xml"),
            translation: encodeURI(_dataURI + "sallust.jugur_eng.xml")
        },
        {
            id: "SenApoc",
            title: "Apocolocyntosis", author: "L. Annaeus Seneca",
            engTitle: "Apocolocyntosis", engAuthor: "Seneca",
            location: encodeURI(_dataURI + "sen.apoc_lat.xml"),
            translation: encodeURI(_dataURI + "sen.apoc_eng.xml")
        },
        {
            id:"VirgA",
            title: "Aeneid", author: "P. Vergilius Maro",
            engTitle: "The Aeneid", engAuthor: "Vergil",
            translator: "Theodore C. Williams",
            location: encodeURI(_dataURI + "verg.a_lat.xml"),
            translation: encodeURI(_dataURI + "verg.a.w_eng.xml")
        }
    ];

    var data = new WinJS.Binding.List(_books);

    function getBook(bookInfo) {
        return getData(bookInfo.location).
            then(function (text) {
                var work = new Work(text, bookInfo);
                return WinJS.Promise.as(work);
            });
    }

    function getTranslation(bookInfo) {
        return getData(bookInfo.translation).
            then(function (text) {
                var work = new Work(text, bookInfo);
                return WinJS.Promise.as(work);
            });
    }

    function getTranslationLines(bookInfo, book, page) {
        return getTranslation(bookInfo).then(function (trans) {
            return trans.books[book].getLines()[page].textContent;
        });
    }

    function getData(location) {
        var uri = new Windows.Foundation.Uri('ms-appx://' + location);
        return Windows.Storage.StorageFile.getFileFromApplicationUriAsync(uri).
            then(function (file) {
                return Windows.Storage.FileIO.readTextAsync(file);
            });
    }

    // An object representing a specific work.
    function Work(text, bookInfo) {
        this.title = bookInfo.title;
        this.author = bookInfo.author;
        this.books = [];
        this.id = bookInfo.id;
        this.translation = bookInfo.translation;
        this.translator = bookInfo.translator;

        this.getPosition();

        var parser = new DOMParser();
        var entryText;
        var entryStart = text.indexOf('\<body\>', 0);

        if (entryStart < 0) {
            return null;
        }
        else {
            var entryEnd = text.indexOf('\<\/body\>', entryStart) + 7;
            entryText = text.slice(entryStart, entryEnd);
        }

        var work = parser.parseFromString(entryText, 'text/html');
        var _books = work.querySelectorAll('div1');
        
        // Remove all of the notes.
        var notes = work.querySelectorAll('note');
        for (var i = 0; i < notes.length; i++) {
            var currentNote = notes[i];
            currentNote.parentElement.removeChild(currentNote);
        }

        if (_books.length > 0) {
            for (var i = 0; i < _books.length; i++) {
                this.books.push(new Book(_books[i]));
            }
        }
        else {
            this.books.push(new Book(work.body));
        }
    }

    // Get a specific page from the current book.
    Work.prototype.getContentByPage = function (page) {
        this.currentPage = page;
        return this.getContent(this.currentBook, this.currentPage);
    }

    // Get a specific book and page.
    Work.prototype.getContent = function (book, page) {

        this.currentBook = book;
        this.currentPage = page;
        this.savePosition();

        var book = this.books[book];

        // The book is at the first page or it's at the beginning of a book.
        if (page < 0) {
            if (this.currentBook > 0) {
                this.currentBook--;
                book = this.books[this.currentBook];
                this.currentPage = this.books[this.currentBook].length - 1;
            }
            else {
                this.currentPage = 0;
            }
        }
        else if (this.currentPage >= book.length) {

            // Handle the case where the user has come to the end of
            // final book in the work.
            if (this.currentBook < (this.books.length - 1)) {
                this.currentBook++;
                this.currentPage = 0;
                book = this.books[this.currentBook];
            }
            else {
                this.currentPage = book.length - 1
            }
        }

        return book.lines[this.currentPage].textContent;
    }

    // Save the user's reading position in the work.
    Work.prototype.savePosition = function() {
        var appData = Windows.Storage.ApplicationData.current;
        appData.roamingSettings.values[this.id] = this.currentBook + "." + this.currentPage;
    }

    // Get the user's last read position.
    Work.prototype.getPosition = function () {
        var appData = Windows.Storage.ApplicationData.current;
        var value = appData.roamingSettings.values[this.id];

        if (value) {
            var data = value.split(".");
            this.currentBook = Number(data[0]);
            this.currentPage = Number(data[1]);
        }
        else {
            this.currentBook = 0;
            this.currentPage = 0;
        }
    }

    // Stores information about a 'book' in the work.
    function Book(bookElement) {
        this.number = bookElement.getAttribute('n');
        this.type = bookElement.getAttribute('type');
        this.content = bookElement;
        this.lines = this.getLines();
        this.length = this.lines.length;
    }

    Book.prototype.getLines = function () {
        var lines = this.content.querySelectorAll('p');
        if (lines.length == 0) {
            lines = this.content.querySelectorAll('l');
        }
        return lines;
    }

    WinJS.Namespace.define("Library", {
        books: data,
        bookCount: _books.length,
        getBook: getBook,
        getTranslation: getTranslation,
        getTranslationLines: getTranslationLines
    });
})();