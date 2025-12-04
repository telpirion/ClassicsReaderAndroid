package com.ericmschmidt.classicsreader.data.placeholders

import com.ericmschmidt.classicsreader.R
import com.ericmschmidt.classicsreader.data.Manifest
import com.ericmschmidt.classicsreader.data.WorkInfo
import com.ericmschmidt.classicsreader.data.WorkInfo.WorkType.POEM
import com.ericmschmidt.classicsreader.data.WorkInfo.WorkType.PROSE
import java.util.ArrayList

class PseudoManifest : Manifest() {

    val test2Description = """
        This is the very long description for a very long work. The description could go on for
        several paragraphs. It's going and going and going and going. This should help us figure
        out whether we have to wrap the text or not.
    """.trimIndent()

    val workInfos : ArrayList<WorkInfo> = arrayListOf(WorkInfo.Builder("test")
            .author("testAuthor")
            .title("testTitle")
            .englishTitle("testTitle")
            .englishAuthor("testAuthor")
            .location(R.raw.text_work)
            .englishLocation(R.raw.text_work)
            .image(R.drawable.work_default_1)
            .workType(PROSE)
            .translator("test translator")
            .description("test description")
            .editor("test editor")
            .build(),
        WorkInfo.Builder("test2")
            .author("Very very super long never-ending author name")
            .title("Very very long title that could go on forever")
            .englishTitle("test2 Title")
            .englishAuthor("test2 author")
            .image(R.drawable.work_default_2)
            .location(R.raw.text_work)
            .englishLocation(R.raw.text_work)
            .workType(PROSE)
            .translator("test2 translator")
            .description(test2Description)
            .editor("test2 editor")
            .build(),
        WorkInfo.Builder("test3")
            .author("Ξενοφῶν")
            .title("Ἀνάβασις")
            .englishTitle("Anabasis")
            .englishAuthor("Xenophon")
            .image(R.drawable.work_default_3)
            .location(R.raw.text_work)
            .englishLocation(R.raw.text_work)
            .workType(POEM)
            .translator("test3 translator")
            .description("test3 description")
            .editor("test3 editor")
            .build())

    override fun getCollection(): ArrayList<WorkInfo>? {
        return workInfos
    }

    override fun getDictionaryInfo(): WorkInfo? {
        return WorkInfo.Builder("test_dictionary")
            .author("Dictionary author")
            .title("Dictionary name")
            .englishTitle("test dictionary")
            .englishAuthor("dictionary author")
            .image(R.drawable.work_default_2)
            .location(R.raw.text_work)
            .englishLocation(R.raw.text_work)
            .build()
    }

    override fun getDictionaryEntryResource(): Int {
        return R.raw.test_entries
    }
}
