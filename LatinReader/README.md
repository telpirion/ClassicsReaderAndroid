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


[work]: https://github.com/telpirion/ClassicsReaderAndroid/blob/main/LatinReader/app/src/main/java/com/ericmschmidt/latinreader/datamodel/Work.java
[workinfo]: https://github.com/telpirion/ClassicsReaderAndroid/blob/main/LatinReader/app/src/main/java/com/ericmschmidt/latinreader/datamodel/WorkInfo.java
[tocentry]: https://github.com/telpirion/ClassicsReaderAndroid/blob/main/LatinReader/app/src/main/java/com/ericmschmidt/latinreader/datamodel/TOCEntry.java