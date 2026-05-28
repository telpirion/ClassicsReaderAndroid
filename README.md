# Design

The Classics Reader is an app designed for reading and translating ancient texts. It provides
translations of texts along side the source texts alongside a dictionary.

## App Architecture

This project contains three modules:

+ The base app (the module named `app`) that provides the base functionality of the app.
+ A module for each flavor--a Latin and Ancient Greek flavor. Each flavor is built into a
  separate app, along with the base app, into a separate APK.

## The `app` module architecture

The `app` module has six packages within it:

* The `activities` package, which includes the `MainActivity` and `ErrorActivity` classes.

* The `datamodel` package, which includes classes that contain the information about the texts
  within the app.

* The `exceptions` package, which includes a single class, `ForceCloseHandler`, which allows the
  app to close gracefully from unhandled exceptions.

* The `fragments` package, which includes all of the fragments or "pages" within the app.

* The `layouts` package, which includes the specialized adapters used for recycler views and list
  views used within the app.
  
* The `utilities` package, which includes several helper classes used for opening and parsing the
  XML resources used within the app.

### Data model

The `datamodel` package includes the following classes:

* The [`WorkInfo`][workinfo] class is the foundational data structure for the app. It coordinates
  the user experience of a work (current page, location, offset, translation name) with the text
  itself. It also includes the resource ID for the work along with its URI.
  
* The [`Work`][work] class acts as a controller between the data storage of the text
  (XML file) and the app.
  
* The [`TOCEntry`][tocentry] class contains information about a table of contents entry for a work.
  It connects a "chapter" title with a location (int) with in the book.

* The [`Book`][book] class contains information for accessing lines of text within a section,
  chapter, or "book" within a larger work. 
  
* The [`Library`][library] class contains the list of `WorkInfo` objects contained in the app.

* The [`Manifest`][manifest] class contains the concrete list of works, stored as XML or JSON,
  within the app. It essentially acts as an interface for each APK or app bundle.
  
* The [`ReadingViewModel`][readingviewmodel] class allows the app to manage the reading position
  in the UI along with the source `Work` object.

[work]: https://github.com/telpirion/ClassicsReaderAndroid/blob/main/LatinReader/app/src/main/java/com/ericmschmidt/latinreader/datamodel/Work.java
[workinfo]: https://github.com/telpirion/ClassicsReaderAndroid/blob/main/LatinReader/app/src/main/java/com/ericmschmidt/latinreader/datamodel/WorkInfo.java
[tocentry]: https://github.com/telpirion/ClassicsReaderAndroid/blob/main/LatinReader/app/src/main/java/com/ericmschmidt/latinreader/datamodel/TOCEntry.java
[book]: https://github.com/telpirion/ClassicsReaderAndroid/blob/main/LatinReader/app/src/main/java/com/ericmschmidt/latinreader/datamodel/Book.java
[library]: https://github.com/telpirion/ClassicsReaderAndroid/blob/main/LatinReader/app/src/main/java/com/ericmschmidt/latinreader/datamodel/Library.java
[manifest]: https://github.com/telpirion/ClassicsReaderAndroid/blob/main/LatinReader/app/src/main/java/com/ericmschmidt/latinreader/datamodel/Manifest.java
[readingviewmodel]: https://github.com/telpirion/ClassicsReaderAndroid/blob/main/LatinReader/app/src/main/java/com/ericmschmidt/latinreader/datamodel/ReadingViewModel.java