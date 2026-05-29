# Release notes for Latin Reader and Greek Reader apps

## Release 3.0 (Version 22)

_Date: 2026-05-28_

* Full migration of app UI to modern Jetpack Compose and Material 3 design with Adaptive support.
* Unified experience for both Latin and Greek Readers across multiple device sizes (phones, tablets, foldables).
* Integrates a dedicated dual-pane / supporting-pane overlay layout for seamless translation and dictionary viewing.
* Introduces the `NavigationSuiteScaffold` supporting adaptive navigation patterns.
* Added `DictionaryTest` unit test suite and configured unit testing CI via GitHub Actions.
* Refactored project structure to a flatter hierarchy, introducing `:latinreader` and `:greekreader` submodule architecture.
* Updated dependencies, Gradle versions, and target SDK to Android 15 (targetSdk 37).
* Added selected item indications and optimized image sizes.
* Swapped in vectorized header icons.

## Release 2.0 (Version 20)

_Date: 2025-12-04_

* Translated the entire codebase from Java to Kotlin.
* Refactored layout and resource files to views-based and code-centric submodules.
* Restructured data and business logic to modern Android patterns with Kotlin.
* Added extensive Robolectric tests to verify reader logic and utility helpers.
* Standardized application configurations and established GitHub Actions Android Build CI workflows.

## Release 1.9 (Version 18)

_Date: 2025-08-26_

* Began integration of Jetpack Compose by converting library views to Composable cards and lists.
* Switched lists to Lazy Column/Row implementations (`PrettyRowLazyList`).
* Added awareness of parallel translation files directly to the library cards.
* Expanded work information in `LatinReaderManifest` with editors, translators, and descriptions.
* Added descriptive images/thumbnails for works and integrated grid/list view toggle switches.

## Release 1.8 (Version 16)

_Date: 2025-07-06_

* Updated Latin Reader to target Android 15.
* Cleaned up and updated gradle files and system dependencies to current SDK versions.
* Incorporated explicit app privacy policy and LICENSE files.
* Fixed small typos and resolved UI layout spacing issues.

## Release 1.7 (Version 14)

_Date: 2021-10-31_

* Adds previous and next page buttons to app
* Fixes some padding bugs.

## Release 1.6 (Version 12)

_Date: 2021-10-21_

* Adds a "Help" page to the app.
* Adds an "About" page to the app.

## Release 1.5.1 (Version 11)

_Date: 2021-10-15_

* Fixed navigation drawer image issue.

## Release 1.5 (Version 10)

_Date: 2021-10-01_

* Converted navigation from `FragmentManager` to navigation graph.
* Added animations between navigations.
* Updated `targetSdkVersion` to 30.
* Converted app to App Bundles format.

## Release Version 9 (No version name)

_Date: 2021-09-22_

* Updated `targetSdkVersion` to 29.
* Converted all components to Jetpack (`androidx` equivalents).
* Started documentation of app architecture.
* Added unit test for `WorkInfo` class.

## Release 1.4 (Version 8)

_Date: 2019-11-17_

* Fixed a bug where reader position in texts weren't being stored.
* Added cards to Library and Translations pages.
* Added unit tests for `Book` and `Work` classes.

## Release 1.3.2 (Version 7)

_Date: 2019-11-17_

* Updated build configuration to comply with Google Play Store regulations.

## Release 1.3.1 (Version 6)

_Date: 2018-10-27_

* Updated build configuration to comply with Google Play Store regulations.

## Release 1.3 (Version 5)

_Date: 2017-03-18_

* Added page position within book.
* Increased font for reading information (author, title).
* Added Livy's _History of Rome (I-II)_.

## Release 1.2 (Version 4)

_Date: 2017-02-06_

* Added progress bar to dictionary and vocabulary pages.
* Fixed multiple bugs:
  - Removed phantom blank page issue on some prose texts.
  - Fixed line offset bug.
  - Improved load time for dictionary and vocabulary pages.

## Release 1.1 (Version 3)

_Date: 2016-12-29_

* Added Horace's _Carmina_ to texts.
* Fixed scrolling problem on dictionary results.
* More minor bug fixes.

## Release 1.0 (Version 2)

_Date: 2016-04-27_

* Initial release!
