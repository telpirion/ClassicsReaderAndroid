@file:Suppress("unused", "SpellCheckingInspection")

package com.ericmschmidt.greekreader.data

import com.ericmschmidt.classicsreader.data.Library
import com.ericmschmidt.classicsreader.data.TOCEntry
import com.ericmschmidt.classicsreader.data.WorkInfo
import com.ericmschmidt.greekreader.R

/** Contains the resource manifest for the Greek Reader app.
 * <br/>
 * @author Eric Schmidt
 * @author <a href="https://telpirion.com">...</a>
 * @version 2.0
 * @since 0.1
 */
class GreekReaderLibrary: Library() {
    /**
     * Gets the collection of works for this app.
     * @return the collection
     */
    override fun getCollection() = arrayListOf(
            // TODO: Break each chapter into separate pages.
            WorkInfo(
                id = "AristotlePol",
                title = "Politics",
                author = "Aristotle",
                editor = "W. D. Ross",
                translator = "H. Rackham",
                englishTitle = "Politics",
                englishAuthor = "Aristotle",
                location = R.raw.gk_aristot_pol_gk,
                englishLocation = R.raw.aristot_pol_eng,
                workType = WorkInfo.WorkType.PROSE,
                image = R.drawable.work_politics,
                description = aristotlePoliticsDescription,
                tocEntries = arrayListOf(
                    TOCEntry("Book 1", 0, 0),
                    TOCEntry("Book 2", 1, 0),
                    TOCEntry("Book 3", 2, 0),
                    TOCEntry("Book 4", 3, 0),
                    TOCEntry("Book 5", 4, 0),
                    TOCEntry("Book 6", 5, 0),
                    TOCEntry("Book 7", 6, 0),
                    TOCEntry("Book 8", 7, 0)
                )),
            WorkInfo(
                id = "HomerIliad",
                author = "Homer",
                title = "Iliad",
                translator = "A.T. Murray, Ph.D.",
                editor = "Thomas W. Allen",
                englishAuthor = "Homer",
                englishTitle = "Iliad",
                location = R.raw.gk_hom_il_gk,
                englishLocation = R.raw.hom_il_eng,
                workType = WorkInfo.WorkType.POEM,
                offset = 1,
                image = R.drawable.work_iliad,
                description = homerIliadDescription),
            WorkInfo(
                id = "HomerOdyssey",
                author = "Homer",
                title = "Odyssey",
                editor = "A.T. Murray",
                translator = "A.T. Murray, Ph.D.",
                englishAuthor = "Homer",
                englishTitle = "Odyssey",
                location = R.raw.gk_hom_od_gk,
                englishLocation = R.raw.hom_od_eng,
                workType = WorkInfo.WorkType.POEM,
                offset = 1,
                image = R.drawable.work_odyssey,
                description = homerOdysseyDescription),
            WorkInfo(
                id = "XenophonAn",
                author = "Xenophon",
                title = "Anabasis",
                editor = "E. C. Marchant",
                translator = "Carleton L. Brownson",
                englishAuthor = "Xenophon",
                englishTitle = "Anabasis",
                location = R.raw.gk_xen_anab_gk,
                englishLocation = R.raw.xen_anab_eng,
                workType = WorkInfo.WorkType.PROSE,
                image = R.drawable.work_anabasis,
                description = xenophonAnabasisDescription),
            WorkInfo(
                id = "Lysias",
                author = "Lysias",
                title = "Speeches",
                editor = "W.R.M. Lamb, M.A.",
                translator = "W.R.M. Lamb, M.A.",
                englishTitle = "Speeches",
                englishAuthor = "Lysias",
                location = R.raw.gk_lys_gk,
                englishLocation = R.raw.lys_eng,
                workType = WorkInfo.WorkType.PROSE,
                image = R.drawable.work_speeches,
                description = lysiasDescription),
        )


    override fun getDictionaryInfo() = WorkInfo(
            id = "dictionary",
            title = "An Intermediate Greek-English Lexicon",
            author = "Henry George Liddell and Robert Scott",
            englishAuthor = "Henry George Liddell and Robert Scott",
            englishTitle = "An Intermediate Greek-English Lexicon",
            location = R.raw.ml,
            englishLocation = R.raw.ml,
            description = dictionaryDescription)

    /**
     * Gets the resource ID of the dictionary entry file.
     * @return int the dictionary entry file's ID.
     */
    override fun getDictionaryEntryResource(): Int {
        return R.raw.dictionary_entries
    }
}