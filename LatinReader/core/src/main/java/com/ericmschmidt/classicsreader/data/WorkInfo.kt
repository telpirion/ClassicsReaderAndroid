package com.ericmschmidt.classicsreader.data

import com.ericmschmidt.classicsreader.R
import java.util.Formatter
import java.util.Locale
import kotlin.collections.ArrayList

/**
 * Contains the data for a work contained in the app.
 * <p>
 * It includes the relevant bibliographical info (author, translator)
 * and the locations of the text in the app.
 * <p>
 * Example:
 * <p>
 * id: "CaesarBG",
 * title: "De Bello Gallico", author: "C. Julius Caesar",
 * engTitle: "The Gallic Wars", engAuthor: "Caesar",
 * location: encodeURI(_dataURI + "caes_bg_lat.xml"),
 * translation: encodeURI(_dataURI + "caes_bg_eng.xml")
 * workType: prose || poem
 * <p>
 * @author Eric Schmidt
 * @author <a href="https://telpirion.com">...</a>
 * @version 2.0
 * @since 1.0
 */
class WorkInfo private constructor() {

    var id: String? = null
        private set
    var author: String? = null
        private set
    var title: String? = null
        private set
    var englishTitle: String? = null
        private set
    var englishAuthor: String? = null
        private set
    var location: Int = 0
        private set
    var englishLocation: Int = 0
        private set
    var workType: Int = 0
        private set
    var tocEntries: ArrayList<TOCEntry> = ArrayList()
    var image: Int? = null
        private set
    var description: String? = null
    var translator: String? = null
    var editor: String? = null

    // Unless specified otherwise, assume a 1-to-1 relationship
    // between line numbers in the English and source language
    var offset: Int = 1
        private set
    var englishOffset: Int = 1
        private set

    companion object {
        private val defaultImages = listOf(
            R.drawable.work_default_1,
            R.drawable.work_default_2,
            R.drawable.work_default_3
        )
    }

    /**
     * Gets the table of content entries
     *
     * @return Array of TOCEntry
     */
    fun getTocEntries(): Array<TOCEntry> {
        return tocEntries.toTypedArray()
    }

    /**
     * Gets the number of table of content entries
     * @return the number of table of content entries
     */
    val tocCount: Int
        get() = this.tocEntries.size

    /**
     * Override the toString method for this class to provide
     * a formatted string
     *
     * @return a formatted string
     */
    override fun toString(): String {
        val sb = StringBuilder()
        val formatter = Formatter(sb, Locale.US)
        return formatter.format(
            "%s %s %s %s %s %s",
            this.id,
            this.title,
            this.author,
            this.englishTitle,
            this.englishAuthor,
            this.location
        ).toString()
    }

    /**
     * Specifies the type of work, poem or prose.
     */
    object WorkType {
        const val PROSE = 1
        const val POEM = 2
    }

    /**
     * Builder class for generating new WorkInfo objects.
     */
    class Builder(id: String) {

        private val workInfo: WorkInfo = WorkInfo()

        init {
            workInfo.id = id
            workInfo.tocEntries = ArrayList()

            val idHash = kotlin.math.abs(id.hashCode())
            workInfo.image = defaultImages[idHash % defaultImages.size]
        }

        fun author(author: String) = apply { this.workInfo.author = author }

        fun title(title: String) = apply { this.workInfo.title = title }

        fun englishTitle(englishTitle: String) = apply { this.workInfo.englishTitle = englishTitle }

        fun englishAuthor(englishAuthor: String) = apply { this.workInfo.englishAuthor = englishAuthor }

        fun location(location: Int) = apply { this.workInfo.location = location }

        fun englishLocation(englishLocation: Int) = apply { this.workInfo.englishLocation = englishLocation }

        fun workType(workType: Int) = apply { this.workInfo.workType = workType }

        fun offset(offset: Int, englishOffset: Int) = apply {
            this.workInfo.offset = offset
            this.workInfo.englishOffset = englishOffset
        }

        fun tocEntry(entry: TOCEntry) = apply { this.workInfo.tocEntries.add(entry) }

        fun image(drawable: Int?) = apply { this.workInfo.image = drawable }

        fun description(description: String?) = apply { this.workInfo.description = description}

        fun translator(translator: String?) = apply { this.workInfo.translator = translator}

        fun editor(editor: String?) = apply { this.workInfo.editor = editor}

        fun build(): WorkInfo = this.workInfo
    }
}