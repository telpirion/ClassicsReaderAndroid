# UI & Presentation Layer

Classics Reader implements two distinct user interface architectures. This separation allows comparative development and showcases Android layout systems: the traditional XML-View and fragment paradigm, and the modern declarative Jetpack Compose layout system.

---

## Traditional View Architecture (`:views`)

The view presentation layer depends on standard Android UI widgets, XML files, and jetpack libraries.

### 1. Activity and Fragment Structure
* **`MainActivity`**: The single core activity container that implements a `DrawerLayout` for side navigation and a `Toolbar` to host global search.
* **Navigation Component**: Defines a nav graph that routes users between fragments using generated `SafeArgs` direction builders (e.g. `ReadingFragmentDirections` and `LibraryFragmentDirections`).
* **`ErrorActivity`**: Configured on boot via `ForceCloseHandler` to catch uncaught runtime exceptions and show a friendly crash screen instead of crashing silently.

### 2. Layouts and Data Binding
* **Data Binding**: XML layouts (like `fragment_reading.xml` or `fragment_library.xml`) wrap target variables directly. For example, `fragment_toc.xml` binds a local fragment reference to update ListViews automatically.
* **List Adapters**: Custom adapters like `TOCListViewAdapter` translate Table of Contents lists into rows, mapping section titles to text locations.

### 3. Interoperability Adapter (`ComposeViewAdapter`)
To reuse Compose components in the View module, `ComposeViewAdapter` provides a bridge. It hosts Compose components inside a traditional layout view, permitting modern features to run in the legacy module.

---

## Jetpack Compose Architecture (`:compose`)

The Compose module replaces XML files, Fragments, and Drawer layouts with a fully declarative, state-driven rendering engine.

### 1. Navigation & Scaffolding
* **`MainActivity`**: Extends `ComponentActivity` and calls `setContent` to bind `ReaderApp` with custom themed colors schemes.
* **`ReaderAppNavHost`**: Declares routes using a sealed class structure `Screen` (defining route configurations like `/library`, `/reading/{workId}?isTranslation={isTranslation}`, `/dictionary?query={query}`).
* **`NavigationSuiteScaffold`**: Material 3 component that automatically adjusts its navigation container based on screen size (e.g., showing a bottom navigation bar on compact phones, and a side navigation rail on wide tablets).

### 2. Composable Screen Composition
* **`ReadingScreen`**: Integrates `ReadingSupportingPane` to handle scrolling content.
* **`LibraryScreen`**: Renders dynamic grid items (`LazyCardGrid`) displaying available classics, their author profiles, and covers.
* **ViewModels**: 
  * `ReadingViewModel`: Manages scroll indexes and text offsets for reading.
  * `DictionaryViewModel`: Holds dictionary query state, search history lists, and matching lexicon definitions as Flow states.

---

## Responsive & Adaptive Layout Design

To ensure optimal layout formatting across a variety of form factors (phones, foldables, tablets, and chromebooks), Classics Reader implements dynamic UI layout schemes.

### 1. `WindowSizeClass` Integration
Compose reads the window dimensions using `calculateWindowSizeClass(this)` inside `MainActivity` and forwards it to `ReaderApp`. The widths are mapped as:
* **Compact / Medium Width**: Displays `NavigationSuiteType.WideNavigationRailCollapsed` (a bottom bar or narrow rail).
* **Expanded Width**: Displays `NavigationSuiteType.WideNavigationRailExpanded` (a full navigation panel).

### 2. Multi-Pane Scaffolding
The `:compose` module utilizes advanced Material 3 adaptive scaffolds to show content:

* **`ListDetailPane`**: Used on library screens. On standard phones, tapping a book takes the user to a full details screen. On wide screens (Expanded width), the library catalog is shown on the left list panel, and the selected book's info page is displayed side-by-side on the right details panel.
* **`ReadingSupportingPane`**: Used on the reading screen. If a user performs a dictionary lookup on a word, standard phones show a popup or navigate to a lookup tab. Wide screens split the UI into a main column (displaying the text of the epic) and a supporting right-hand column (displaying the dictionary definition or grammar references) for an uninterrupted reading flow.
