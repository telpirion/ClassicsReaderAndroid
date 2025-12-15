@file:Suppress("SpellCheckingInspection")

package com.ericmschmidt.classicsreader.data

import com.ericmschmidt.classicsreader.R
import java.util.Formatter
import java.util.Locale

val defaultImages = listOf(
    R.drawable.work_default_1,
    R.drawable.work_default_2,
    R.drawable.work_default_3
)

/**
 * Contains the data for a work contained in the app.
 * <br/>
 * It includes the relevant bibliographical info (author, translator)
 * and the locations of the text in the app.
 * <br/>
 * Example:
 * <br/>
 * id: "CaesarBG",
 * title: "De Bello Gallico", author: "C. Julius Caesar",
 * engTitle: "The Gallic Wars", engAuthor: "Caesar",
 * location: encodeURI(_dataURI + "caes_bg_lat.xml"),
 * translation: encodeURI(_dataURI + "caes_bg_eng.xml")
 * workType: prose || poem
 * <br/>
 * @author Eric Schmidt
 * @author https://telpirion.com
 * @version 2.0
 * @since 1.0
 */
data class WorkInfo(
    val id: String,
    val author: String = "",
    val title: String = "",
    val englishTitle: String = "",
    val englishAuthor: String = "",
    val location: Int = 0,
    val englishLocation: Int = 0,
    val workType: Int = 0,
    val tocEntries: ArrayList<TOCEntry> = ArrayList(),
    val image: Int = defaultImages.random(),
    val description: String = "",
    val editor: String = "",
    val translator: String = "",
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
}