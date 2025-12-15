package com.ericmschmidt.latin.data

import com.ericmschmidt.classicsreader.data.Manifest
import com.ericmschmidt.classicsreader.data.TOCEntry
import com.ericmschmidt.classicsreader.data.WorkInfo
import com.ericmschmidt.latinreader.R

/**
 * Contains the resource manifest for the Latin Reader app.
 *
 * @author Eric Schmidt
 * @author [Telpirion](https://telpirion.com)
 * @version 2.0
 * @since 1.1
 */
class LatinReaderManifest : Manifest() {

    /**
     * Gets the collection of works for this app.
     * @return WorkInfo the collection
     */
    override fun getCollection() = arrayListOf(
        WorkInfo(
            id = "CaesarBG",
            author = "C. Julius Caesar",
            title = "De Bello Gallico",
            englishAuthor = "Caesar",
            englishTitle = "The Gallic War",
            location = R.raw.caes_bg_lat,
            englishLocation = R.raw.caes_bg_eng,
            workType = WorkInfo.WorkType.PROSE,
            tocEntries = arrayListOf(
                TOCEntry("", 0, 0),
                TOCEntry("", 1, 0),
                TOCEntry("", 2, 0),
                TOCEntry("", 3, 0),
                TOCEntry("", 4, 0),
                TOCEntry("", 5, 0),
                TOCEntry("", 6, 0),
                TOCEntry("", 7, 0)
            ),
            image = R.drawable.work_de_bello_gallico),
        WorkInfo(
            id = "Horace",
            title = "Carmina",
            author = "Q. Horatius Flaccus",
            englishTitle = "The Odes and Carmen Saeculare of Horace",
            englishAuthor = "Horace",
            location = R.raw.hor_carm_lat,
            englishLocation = R.raw.hor_carm_eng,
            workType = WorkInfo.WorkType.POEM,
            image = R.drawable.work_carmina),
        WorkInfo(
            id = "Lucretius",
            title = "De Rerum Natura",
            author = "T. Lucretius Caro",
            englishTitle = "On the Nature of Things",
            englishAuthor = "Lucretius",
            location = R.raw.lucretius_lat,
            englishLocation = R.raw.lucretius_eng,
            workType = WorkInfo.WorkType.POEM,
            image = R.drawable.work_de_rerum_natura),
        WorkInfo(
            id = "OvidM",
            title = "Metamorphoses",
            author = "P. Ovidius Naso",
            englishTitle = "Metamorphoses",
            englishAuthor = "Ovid",
            location = R.raw.ovid_met_lat,
            englishLocation = R.raw.ovid_met_eng,
            workType = WorkInfo.WorkType.POEM,
            image = R.drawable.work_metamorphoses),
        WorkInfo(
            id = "Petronius",
            title = "Satyricon, Fragmenta, and Poems",
            author = "G. Petronius Arbiter",
            englishTitle = "Satyricon, Fragmenta, and Poems",
            englishAuthor = "Petronius",
            location = R.raw.petr_lat,
            englishLocation = R.raw.petr_eng,
            workType = WorkInfo.WorkType.PROSE,
            image = R.drawable.work_satyricon),
        WorkInfo(
            id = "SalJug",
            title = "Bellum Jugurthinum",
            author = "C. Sallusti Crispi",
            englishTitle = "The Jugurthine War",
            englishAuthor = "Sallust",
            location = R.raw.sallust_jugur_eng,
            workType = WorkInfo.WorkType.PROSE,
            image = R.drawable.work_de_bellum_jugurthinum),
        WorkInfo(
            id = "SenApoc",
            title = "Apocolocyntosis",
            author = "L. Annaeus Seneca",
            englishTitle = "Apocolocyntosis",
            englishAuthor = "Seneca",
            location = R.raw.sen_apoc_lat,
            englishLocation = R.raw.sen_apoc_eng,
            workType = WorkInfo.WorkType.PROSE,
            image = R.drawable.work_apocolocyntosis),
        WorkInfo(
            id = "VirgA",
            title = "Aeneid",
            author = "P. Vergilius Maro",
            englishTitle = "The Aeneid",
            englishAuthor = "Vergil",
            location = R.raw.verg_a_lat,
            englishLocation = R.raw.verg_a_eng,
            workType = WorkInfo.WorkType.POEM,
            image = R.drawable.work_aeneid),
        WorkInfo(
            id = "Livy1",
            author = "Titus Livius",
            title = "Ab Urbe Condita, liber I-II",
            englishAuthor = "Livy",
            englishTitle = "The History of Rome, books 1-2",
            location = R.raw.livy_01_02_lat,
            englishLocation = R.raw.livy_01_02_eng,
            workType = WorkInfo.WorkType.PROSE,
            image = R.drawable.work_ab_urbe_condita)
    )

    /**
     * Gets the resource information of the dictionary file.
     * @return WorkInfo
     */
    override fun getDictionaryInfo(): WorkInfo =
        WorkInfo(
            id = "dictionary",
            title = "An Elementary Latin Dictionary",
            author = "Charles Lewis",
            location = R.raw.lewis,
            englishLocation = R.raw.lewis,
            workType = WorkInfo.WorkType.PROSE,
            image = com.ericmschmidt.classicsreader.R.drawable.work_default_1)


    /**
     * Gets the resource ID of the dictionary entry file.
     * @return int the dictionary entry file's ID.
     */
    override fun getDictionaryEntryResource(): Int = R.raw.dictionary_entries
}