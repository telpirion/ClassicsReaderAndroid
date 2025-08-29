package com.ericmschmidt.classicsreader.data.placeholders

import com.ericmschmidt.classicsreader.R
import com.ericmschmidt.classicsreader.data.Manifest
import com.ericmschmidt.classicsreader.data.WorkInfo
import java.util.ArrayList

class PseudoManifest : Manifest() {
    val workInfos : ArrayList<WorkInfo> = arrayListOf(WorkInfo.Builder("test")
            .author("testAuthor")
            .title("testTitle")
            .englishTitle("testTitle")
            .englishAuthor("testAuthor")
            .location(R.raw.text_work)
            .englishLocation(R.raw.text_work)
            .image(R.drawable.work_default_1)
            .build(),
        WorkInfo.Builder("test2")
            .author("Very very super long never-ending author name")
            .title("Very very long title that could go on forever")
            .englishTitle("testTitle")
            .englishAuthor("testAuthor")
            .image(R.drawable.work_default_2)
            .location(R.raw.text_work)
            .englishLocation(R.raw.text_work)
            .build(),
        WorkInfo.Builder("test3")
            .author("Ξενοφῶν")
            .title("Ἀνάβασις")
            .englishTitle("Anabasis")
            .englishAuthor("Xenophon")
            .image(R.drawable.work_default_3)
            .location(R.raw.text_work)
            .englishLocation(R.raw.text_work)
            .build())

    override fun getCollection(): ArrayList<WorkInfo>? {
        return workInfos
    }

    override fun getDictionaryInfo(): WorkInfo? {
        return WorkInfo.Builder("test_dictionary")
            .author("Dictionary author")
            .title("Dictionary name")
            .englishTitle("test dictionary")
            .englishAuthor("dictinary author")
            .image(R.drawable.work_default_2)
            .location(R.raw.text_work)
            .englishLocation(R.raw.text_work)
            .build()
    }

    override fun getDictionaryEntryResource(): Int {
        return R.raw.test_entries
    }
}
