package com.ericmschmidt.classicsreader.data

import com.ericmschmidt.classicsreader.R
import java.util.Formatter
import java.util.Locale
import kotlin.math.abs

/**
 * Contains the data for a work contained in the app.
 *
 * It includes the relevant bibliographical info (author, translator)
 * and the locations of the text in the app.
 *
 * Example:
 *
 * id: "CaesarBG",
 * title: "De Bello Gallico", author: "C. Julius Caesar",
 * engTitle: "The Gallic Wars", engAuthor: "Caesar",
 * location: encodeURI(_dataURI + "caes_bg_lat.xml"),
 * translation: encodeURI(_dataURI + "caes_bg_eng.xml")
 * workType: prose || poem
 *
 * @author Eric Schmidt
 * @author https://telpirion.com
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