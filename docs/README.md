# Classics Reader Android App Documentation

Welcome to the technical documentation for the **Classics Reader** Android project. This repository contains the source code for building the Latin Reader and Greek Reader apps, available in both traditional XML-View and modern Jetpack Compose architectures.

## Project Overview

Classics Reader is a specialized e-reading application designed for Classicists, students, and educators. The app enables users to read ancient Greek and Latin texts in their original languages side-by-side with English translations, perform inline or manual dictionary lookups using integrated dictionaries, navigate via structural Tables of Contents, and study grammar rules.

## Documentation Structure

This documentation is divided into the following sections:

1. **[System Architecture](architecture.md)**
   * Outlines the multi-module build system structure.
   * Details the dependency relationships between library modules and application modules.
   * Compares the View-based and Jetpack Compose-based compiler workflows.

2. **[Data Model & Processing Strategy](data_model.md)**
   * Documents the core domain model (`Work`, `Book`, `WorkInfo`, `Library`, `TOCEntry`, `Dictionary`).
   * Explains XML parsing using the `ResourceHelpers` utility.
   * Illustrates the UML Class Relationships diagram.
   * Reviews settings state management (SharedPreferences vs Jetpack DataStore).

3. **[UI & Presentation Layer](ui_presentation.md)**
   * Discusses layout implementation inside the `:views` module (Fragments, Data Binding, Activity routing).
   * Discusses screen composition inside the `:compose` module (ViewModels, screen responsiveness, supporting panes).
   * Outlines the adaptive layouts utilizing `WindowSizeClass`.

4. **[Language Customization & Injection](flavor_comparison.md)**
   * Deep-dives into the customization layers of `:latinreader` and `:greekreader`.
   * Explains how `MyApplication` uses Java reflection to inject specific library catalogs and text converter converters dynamically.
   * Details the Greek polytonic transliteration system (`TextConverter`).

***

*Documentation Version: 3.0.0*  
*Last updated: May 2026*
