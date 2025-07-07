package com.ericmschmidt.greekreader.datamodel;

import com.ericmschmidt.greekreader.R;
import com.ericmschmidt.classicsreader.datamodel.Manifest;
import com.ericmschmidt.classicsreader.datamodel.WorkInfo;

import java.util.ArrayList;

/** Contains the resource manifest for the Greek Reader app.
 *
 * @author Eric Schmidt
 * @author http://telpirion.com
 * @version 0.1
 */
public class GreekReaderManifest extends Manifest {

    /**
     * Gets the collection of works for this app.
     * @return the collection
     */
    @Override
    public ArrayList<WorkInfo> getCollection() {
        ArrayList<WorkInfo> workInfos = new ArrayList<WorkInfo>();

        // TODO: Break each chapter into separate pages.
        workInfos.add(new WorkInfo.Builder("AristotlePol")
                .title("Politics")
                .author("Aristotle")
                .englishTitle("Politics")
                .englishAuthor("Aristotle")
                .location(R.raw.gk_aristot_pol_gk)
                .englishLocation(R.raw.aristot_pol_eng)
                .workType(WorkInfo.WorkType.PROSE)
                .create());

        // TODO: Fix Herodotus transcription.
        /*workInfos.add(new WorkInfo("Herodotus",
                "Histories",
                "Herodotus",
                "Histories",
                "Herodotus",
                R.raw.gk_hdt_gk,
                R.raw.hdt_eng,
                WorkInfo.WorkType.PROSE));*/

        workInfos.add(new WorkInfo.Builder("HomerIliad")
                .author("Homer")
                .title("Iliad")
                .englishAuthor("Homer")
                .englishTitle("Iliad")
                .location(R.raw.gk_hom_il_gk)
                .englishLocation(R.raw.hom_il_eng)
                .workType(WorkInfo.WorkType.POEM)
                .offset(1, 5)
                .create());

        workInfos.add(new WorkInfo.Builder("HomerOdyssey")
                .author("Homer")
                .title("Odyssey")
                .englishAuthor("Homer")
                .englishTitle("Odyssey")
                .location(R.raw.gk_hom_od_gk)
                .englishLocation(R.raw.hom_od_eng)
                .workType(WorkInfo.WorkType.POEM)
                .offset(1, 5)
                .create());

        workInfos.add(new WorkInfo("Lysias",
                "Speeches",
                "Lysias",
                "Speeches",
                "Lysias",
                R.raw.gk_lys_gk,
                R.raw.lys_eng,
                WorkInfo.WorkType.PROSE));

        // TODO: Fix Plato's Republic Transcription
        /*workInfos.add(new WorkInfo("PlatoRep",
                "Republic",
                "Plato",
                "Republic",
                "Plato",
                R.raw.gk_plat_rep_gk,
                R.raw.plat_rep_eng,
                WorkInfo.WorkType.PROSE));*/

        workInfos.add(new WorkInfo("XenophonAn",
                "Anabasis",
                "Xenophon",
                "Anabasis",
                "Xenophon",
                R.raw.gk_xen_anab_gk,
                R.raw.xen_anab_eng,
                WorkInfo.WorkType.PROSE));

        return workInfos;
    }

    @Override
    public WorkInfo getDictionaryInfo() {
        return new WorkInfo("dictionary",
                "An Intermediate Greek-English Lexicon",
                "Henry George Liddell and Robert SCott",
                "",
                "",
                R.raw.ml,
                R.raw.ml,
                WorkInfo.WorkType.PROSE);
    }

    /**
     * Gets the resource ID of the dictionary entry file.
     * @return int the dictionary entry file's ID.
     */
    @Override
    public int getDictionaryEntryResource() {
        return R.raw.dictionary_entries;
    }
}
