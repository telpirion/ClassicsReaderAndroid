package com.ericmschmidt.greekreader.datamodel

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ericmschmidt.classicsreader.data.Manifest
import com.ericmschmidt.classicsreader.data.WorkInfo
import com.ericmschmidt.greekreader.R

/** Contains the resource manifest for the Greek Reader app.
 * <br/>
 * @author Eric Schmidt
 * @author [...](https://telpirion.com)
 * @version 3.0
 * @noinspection unused
 */
@Suppress("unused", "SpellCheckingInspection")
class GreekReaderManifest : Manifest() {
    /**
     * Gets the collection of works for this app.
     * @return the collection
     */
    @Suppress("UNCHECKED_CAST")
    override fun getCollection(): ArrayList<WorkInfo> {

        val workInfos = arrayListOf<WorkInfo>()

        workInfos.add(
            WorkInfo.Builder("AristotlePol")
                .title("Politics")
                .author("Aristotle")
                .englishTitle("Politics")
                .englishAuthor("Aristotle")
                .location(R.raw.gk_aristot_pol_gk)
                .englishLocation(R.raw.aristot_pol_eng)
                .workType(WorkInfo.WorkType.PROSE)
                .image(R.drawable.work_politics)
                .build(),
            WorkInfo.Builder("HomerIliad")
                .author("Homer")
                .title("Iliad")
                .englishAuthor("Homer")
                .englishTitle("Iliad")
                .location(R.raw.gk_hom_il_gk)
                .englishLocation(R.raw.hom_il_eng)
                .workType(WorkInfo.WorkType.POEM)
                .offset(1, 5)
                .image(R.drawable.work_iliad)
                .build(),
            WorkInfo.Builder("HomerOdyssey")
                .author("Homer")
                .title("Odyssey")
                .englishAuthor("Homer")
                .englishTitle("Odyssey")
                .location(R.raw.gk_hom_od_gk)
                .englishLocation(R.raw.hom_od_eng)
                .workType(WorkInfo.WorkType.POEM)
                .offset(1, 5)
                .image(R.drawable.work_odyssey)
                .build(),
            WorkInfo.Builder("XenophonAn")
                .author("Xenophon")
                .title("Anabasis")
                .englishAuthor("Xenophon")
                .englishTitle("Anabasis")
                .location(R.raw.gk_xen_anab_gk)
                .englishLocation(R.raw.xen_anab_eng)
                .workType(WorkInfo.WorkType.PROSE)
                .image(R.drawable.work_anabasis)
                .build(),
            WorkInfo.Builder("Lysias")
                .author("Lysias")
                .title("Speeches")
                .englishTitle("Speeches")
                .englishAuthor("Lysias")
                .location(R.raw.gk_lys_gk)
                .englishLocation(R.raw.lys_eng)
                .workType(WorkInfo.WorkType.PROSE)
                .image(R.drawable.work_speeches)
                .build()
        )

        return workInfos
    }

    override fun getDictionaryInfo(): WorkInfo {
        return WorkInfo.Builder("dictionary")
            .title("An Intermediate Greek-English Lexicon")
            .author("Henry George Liddell and Robert Scott")
            .englishAuthor("Henry George Liddell and Robert Scott")
            .englishTitle("An Intermediate Greek-English Lexicon")
            .location(R.raw.ml)
            .englishLocation(R.raw.ml)
            .build()
    }

    /**
     * Gets the resource ID of the dictionary entry file.
     * @return int the dictionary entry file's ID.
     */
    override fun getDictionaryEntryResource(): Int {
        return R.raw.dictionary_entries
    }

    @Composable
    override fun GetHeaderIcon() {
        return Image(
            painter = painterResource(id = R.drawable.ic_greekreader),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
    }
}