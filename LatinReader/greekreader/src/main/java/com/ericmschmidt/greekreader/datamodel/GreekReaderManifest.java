package com.ericmschmidt.greekreader.datamodel;

import com.ericmschmidt.classicsreader.datamodel.Manifest;
import com.ericmschmidt.greekreader.R;

import java.util.ArrayList;

/** Contains the resource manifest for the Greek Reader app.
 *
 * @author Eric Schmidt
 * @author <a href="https://telpirion.com">...</a>
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
                        .image(R.drawable.work_politics)
                        .build());

        workInfos.add(new WorkInfo.Builder("HomerIliad")
                        .author("Homer")
                        .title("Iliad")
                        .englishAuthor("Homer")
                        .englishTitle("Iliad")
                        .location(R.raw.gk_hom_il_gk)
                        .englishLocation(R.raw.hom_il_eng)
                        .workType(WorkInfo.WorkType.POEM)
                        .offset(1, 5)
                        .image(R.drawable.work_iliad)
                        .build());

        workInfos.add(new WorkInfo.Builder("HomerOdyssey")
                        .author("Homer")
                        .title("Odyssey")
                        .englishAuthor("Homer")
                        .englishTitle("Odyssey")
                        .location(R.raw.gk_hom_od_gk)
                        .englishLocation(R.raw.hom_od_eng)
                        .workType(WorkInfo.WorkType.POEM)
                        .offset(1, 5)
                        .image(R.drawable.work_odyssey)
                        .build());

        workInfos.add(new WorkInfo.Builder("XenophonAn")
                        .author("Xenophon")
                        .title("Anabasis")
                        .englishAuthor("Xenophon")
                        .englishTitle("Anabasis")
                        .location(R.raw.gk_xen_anab_gk)
                        .englishLocation(R.raw.xen_anab_eng)
                        .workType(WorkInfo.WorkType.PROSE)
                        .image(R.drawable.work_anabasis)
                        .build());

        workInfos.add(new WorkInfo.Builder("Lysias")
                        .author("Lysias")
                        .title("Speeches")
                        .englishTitle("Speeches")
                        .englishAuthor("Lysias")
                        .location(R.raw.gk_lys_gk)
                        .englishLocation(R.raw.lys_eng)
                        .workType(WorkInfo.WorkType.PROSE)
                        .image(R.drawable.work_speeches)
                        .build());

        /*
        // TODO: Fix Herodotus transcription.
        // "Histories" is also a title that breaks the list ...
        workInfos.add(new WorkInfo.Builder("Herodotus")
                        .title("Mysteries")
                        .author("Herodotus")
                        .englishTitle("Histories")
                        .englishAuthor("Herodotus")
                        .location(R.raw.gk_hdt_gk)
                        .englishLocation(R.raw.hdt_eng)
                        .workType(WorkInfo.WorkType.PROSE)
                        .build());

        // TODO: Fix Plato's Republic Transcription
        // "Republic" is also a title that breaks the list ...
        workInfos.add(new WorkInfo.Builder("PlatoRep")
                        .title("Republic")
                        .author("Plato")
                        .englishTitle("Republic")
                        .englishAuthor("Plato")
                        .location(R.raw.gk_plat_rep_gk)
                        .englishLocation(R.raw.plat_rep_eng)
                        .workType(WorkInfo.WorkType.PROSE)
                        .build());
        */
        return workInfos;
    }

    @Override
    public WorkInfo getDictionaryInfo() {
        return new WorkInfo.Builder("dictionary")
                .title("An Intermediate Greek-English Lexicon")
                .author("Henry George Liddell and Robert Scott")
                .englishAuthor("Henry George Liddell and Robert Scott")
                .englishTitle("An Intermediate Greek-English Lexicon")
                .location(R.raw.ml)
                .englishLocation(R.raw.ml)
                .build();
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
