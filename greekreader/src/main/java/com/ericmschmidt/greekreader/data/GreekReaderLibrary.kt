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
                description = homerIliadDescription,
                tocEntries = arrayListOf(
                    TOCEntry("Book 1", 0, 0),
                    TOCEntry("Book 2", 1, 0),
                    TOCEntry("Book 3", 2, 0),
                    TOCEntry("Book 4", 3, 0),
                    TOCEntry("Book 5", 4, 0),
                    TOCEntry("Book 6", 5, 0),
                    TOCEntry("Book 7", 6, 0),
                    TOCEntry("Book 8", 7, 0),
                    TOCEntry("Book 9", 8, 0),
                    TOCEntry("Book 10", 9, 0),
                    TOCEntry("Book 11", 10, 0),
                    TOCEntry("Book 12", 11, 0),
                    TOCEntry("Book 13", 12, 0),
                    TOCEntry("Book 14", 13, 0),
                    TOCEntry("Book 15", 14, 0),
                    TOCEntry("Book 16", 15, 0),
                    TOCEntry("Book 17", 16, 0),
                    TOCEntry("Book 18", 17, 0),
                    TOCEntry("Book 19", 18, 0),
                    TOCEntry("Book 20", 19, 0),
                    TOCEntry("Book 21", 20, 0),
                    TOCEntry("Book 22", 21, 0),
                    TOCEntry("Book 23", 22, 0),
                    TOCEntry("Book 24", 23, 0)
                )),
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
                description = homerOdysseyDescription,
                tocEntries = arrayListOf(
                    TOCEntry("Book 1", 0, 0),
                    TOCEntry("Book 2", 1, 0),
                    TOCEntry("Book 3", 2, 0),
                    TOCEntry("Book 4", 3, 0),
                    TOCEntry("Book 5", 4, 0),
                    TOCEntry("Book 6", 5, 0),
                    TOCEntry("Book 7", 6, 0),
                    TOCEntry("Book 8", 7, 0),
                    TOCEntry("Book 9", 8, 0),
                    TOCEntry("Book 10", 9, 0),
                    TOCEntry("Book 11", 10, 0),
                    TOCEntry("Book 12", 11, 0),
                    TOCEntry("Book 13", 12, 0),
                    TOCEntry("Book 14", 13, 0),
                    TOCEntry("Book 15", 14, 0),
                    TOCEntry("Book 16", 15, 0),
                    TOCEntry("Book 17", 16, 0),
                    TOCEntry("Book 18", 17, 0),
                    TOCEntry("Book 19", 18, 0),
                    TOCEntry("Book 20", 19, 0),
                    TOCEntry("Book 21", 20, 0),
                    TOCEntry("Book 22", 21, 0),
                    TOCEntry("Book 23", 22, 0),
                    TOCEntry("Book 24", 23, 0)
                )),
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
                description = xenophonAnabasisDescription,
                tocEntries = arrayListOf(
                    TOCEntry("Book 1", 0, 0),
                    TOCEntry("Book 2", 1, 0),
                    TOCEntry("Book 3", 2, 0),
                    TOCEntry("Book 4", 3, 0),
                    TOCEntry("Book 5", 4, 0),
                    TOCEntry("Book 6", 5, 0),
                    TOCEntry("Book 7", 6, 0)
                )),
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
                description = lysiasDescription,
                tocEntries = arrayListOf(
                    TOCEntry("On the Murder of Eratosthenes", 0, 0),
                    TOCEntry("Funeral Oration", 1, 0),
                    TOCEntry("Against Simon: Defense", 2, 0),
                    TOCEntry("On A Wound By Premeditation", 3, 0),
                    TOCEntry("For Callias", 4, 0),
                    TOCEntry("Against Andocides", 5, 0),
                    TOCEntry("Defense in the Matter of the Olive Stump", 6, 0),
                    TOCEntry("Accusation of Calumny", 7, 0),
                    TOCEntry("For The Soldier", 8, 0),
                    TOCEntry("Against Theomnestus 1", 9, 0),
                    TOCEntry("Against Theomnestus 2", 10, 0),
                    TOCEntry("Against Eratosthenes", 11, 0),
                    TOCEntry("Against Agoratus", 12, 0),
                    TOCEntry("Against Alcibiades 1", 13, 0),
                    TOCEntry("Against Alcibiades 2", 14, 0),
                    TOCEntry("In Defense of Mantitheus", 15, 0),
                    TOCEntry("On The Property Of Eraton", 16, 0),
                    TOCEntry("On The Property Of The Brother Of Nicias: Peroration", 17, 0),
                    TOCEntry("On the Property of Aristophanes", 18, 0),
                    TOCEntry("For Polystratus", 19, 0),
                    TOCEntry("Defence Against A Charge Of Taking Bribes", 20, 0),
                    TOCEntry("Against The Corn-Dealers", 21, 0),
                    TOCEntry("Against Pancleon", 22, 0),
                    TOCEntry("On The Refusal Of A Pension", 23, 0),
                    TOCEntry("Defense Against a Charge of Subverting the Democracy", 24, 0),
                    TOCEntry("On the Scrutiny of Evandros", 25, 0),
                    TOCEntry("Against Epicrates and his Fellow-envoys", 26, 0),
                    TOCEntry("Against Ergocles", 27, 0),
                    TOCEntry("Against Philocrates", 28, 0),
                    TOCEntry("Against Nicomachus", 29, 0),
                    TOCEntry("Against Philon", 30, 0),
                    TOCEntry("Against Diogeiton", 31, 0),
                    TOCEntry("Olympic Oration", 32, 0),
                    TOCEntry("Against The Subversion of the Ancestral Constitution", 33, 0)
                )),
        )


    override fun getDictionaryInfo() = WorkInfo(
            id = "dictionary",
            title = "An Intermediate Greek-English Lexicon",
            author = "Henry George Liddell and Robert Scott",
            englishAuthor = "Henry George Liddell and Robert Scott",
            englishTitle = "An Intermediate Greek-English Lexicon",
            location = R.raw.ml,
            englishLocation = R.raw.ml,
            description = dictionaryDescription,
            workType = WorkInfo.WorkType.DICTIONARY
        )

    /**
     * Gets the resource ID of the dictionary entry file.
     * @return int the dictionary entry file's ID.
     */
    override fun getDictionaryEntryResource(): Int {
        return R.raw.dictionary_entries
    }

    override fun getGrammar(): WorkInfo = WorkInfo(
        id = "grammar",
        title = "A Greek Grammar for Colleges",
        author = "Herbert Weir Smyth",
        englishTitle = "A Greek Grammar for Colleges",
        englishAuthor = "Herbert Weir Smyth",
        location = R.raw.smyth,
        englishLocation = R.raw.smyth,
        workType = WorkInfo.WorkType.GRAMMAR
    )
}