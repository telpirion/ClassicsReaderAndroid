package com.ericmschmidt.classicsreader.data

import com.ericmschmidt.classicsreader.R
import java.util.Formatter
import java.util.Locale
import kotlin.collections.ArrayList
import kotlin.math.abs

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
data class WorkInfo(
    val id: String,
    val author: String,
    val title: String,
    val englishTitle: String,
    val englishAuthor: String,
    val location: Int,
    val englishLocation: Int,
    val workType: Int,
    val tocEntries: ArrayList<TOCEntry> = ArrayList(),
    val image: Int?,
    val descriptionLocation: Int?,
    val offset: Int = 1,
    val englishOffset: Int = 1
) {

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

    class Builder(private val id: String) {
        private var author: String = ""
        private var title: String = ""
        private var englishTitle: String = ""
        private var englishAuthor: String = ""
        private var location: Int = 0
        private var englishLocation: Int = 0
        private var workType: Int = 0
        private var tocEntries: ArrayList<TOCEntry> = ArrayList()
        private var image: Int?
        private var descriptionLocation: Int? = null
        private var offset: Int = 1
        private var englishOffset: Int = 1

        init {
            val idHash = abs(id.hashCode())
            image = defaultImages[idHash % defaultImages.size]
        }

        fun author(author: String) = apply { this.workInfo.author = author }

        fun title(title: String) = apply { this.workInfo.title = title }

        fun englishTitle(englishTitle: String) = apply { this.workInfo.englishTitle = englishTitle }

        fun englishAuthor(englishAuthor: String) = apply { this.workInfo.englishAuthor = englishAuthor }

        fun location(location: Int) = apply { this.workInfo.location = location }

        fun englishLocation(englishLocation: Int) = apply { this.workInfo.englishLocation = englishLocation }

        fun workType(workType: Int) = apply { this.workInfo.workType = workType }

        fun author(author: String) = apply { this.author = author }
        fun title(title: String) = apply { this.title = title }
        fun englishTitle(englishTitle: String) = apply { this.englishTitle = englishTitle }
        fun englishAuthor(englishAuthor: String) = apply { this.englishAuthor = englishAuthor }
        fun location(location: Int) = apply { this.location = location }
        fun englishLocation(englishLocation: Int) = apply { this.englishLocation = englishLocation }
        fun workType(workType: Int) = apply { this.workType = workType }
        fun offset(offset: Int, englishOffset: Int) = apply {
            this.offset = offset
            this.englishOffset = englishOffset
        }
        fun TOCEntry(entry: TOCEntry) = apply { this.tocEntries.add(entry) }
        fun image(drawable: Int?) = apply { this.image = drawable }
        fun descriptionLocation(description: Int?) = apply { this.descriptionLocation = description }

        fun build() = WorkInfo(
            id,
            author,
            title,
            englishTitle,
            englishAuthor,
            location,
            englishLocation,
            workType,
            tocEntries,
            image,
            descriptionLocation,
            offset,
            englishOffset
        )

        companion object {
            private val defaultImages = listOf(
                R.drawable.work_default_1,
                R.drawable.work_default_2,
                R.drawable.work_default_3
            )
        }
    }
}