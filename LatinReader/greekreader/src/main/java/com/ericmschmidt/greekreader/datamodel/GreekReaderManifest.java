package com.ericmschmidt.greekreader.datamodel;

import com.ericmschmidt.greekreader.R;
import com.ericmschmidt.latinreader.datamodel.Manifest;
import com.ericmschmidt.latinreader.datamodel.WorkInfo;

import java.util.ArrayList;

public class GreekReaderManifest extends Manifest {

    /**
     * Gets the collection of works for this app.
     * @return the collection
     */
    @Override
    public ArrayList<WorkInfo> getCollection() {
        ArrayList<WorkInfo> workInfos = new ArrayList<WorkInfo>();

        // TODO: Break each chapter into separate pages.
        workInfos.add(new WorkInfo("AristotlePol",
                "Politics",
                "Aristotle",
                "Politics",
                "Aristotle",
                R.raw.gk_aristot_pol_gk,
                R.raw.aristot_pol_eng,
                WorkInfo.WorkType.PROSE));

        // TODO: Fix Herodotus transcription.
        /*workInfos.add(new WorkInfo("Herodotus",
                "Histories",
                "Herodotus",
                "Histories",
                "Herodotus",
                R.raw.gk_hdt_gk,
                R.raw.hdt_eng,
                WorkInfo.WorkType.PROSE));*/

        // TODO: Add translater info
        // Iliad: Richard Lattimore
        workInfos.add(new WorkInfo("HomerIliad",
                "Iliad",
                "Homer",
                "Iliad",
                "Homer",
                R.raw.gk_hom_il_gk,
                R.raw.hom_il_eng,
                WorkInfo.WorkType.POEM));

        workInfos.add(new WorkInfo("HomerOdyssey",
                "Odyssey",
                "Homer",
                "Odyssey",
                "Homer",
                R.raw.gk_hom_od_gk,
                R.raw.hom_od_eng,
                WorkInfo.WorkType.POEM));

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
