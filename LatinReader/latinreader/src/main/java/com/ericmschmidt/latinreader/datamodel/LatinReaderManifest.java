package com.ericmschmidt.latinreader.datamodel;

import com.ericmschmidt.classicsreader.datamodel.Manifest;
import com.ericmschmidt.classicsreader.datamodel.TOCEntry;
import com.ericmschmidt.classicsreader.datamodel.WorkInfo;
import com.ericmschmidt.latinreader.R;

import java.util.ArrayList;

/** Contains the resource manifest for the Latin Reader app.
 *
 * @author Eric Schmidt
 * @author http://telpirion.com
 * @version 1.5
 * @since 1.1
 */
public class LatinReaderManifest extends Manifest {

    public LatinReaderManifest() {
        super();
    }

    /**
     * Gets the collection of works for this app.
     * @return WorkInfo the collection
     */
    @Override
    public ArrayList<WorkInfo> getCollection() {
        ArrayList<WorkInfo> workInfos = new ArrayList<WorkInfo>();

        workInfos.add(new WorkInfo.Builder("CaesarBG")
                .author("C. Julius Caesar")
                .title("De Bello Gallico")
                .englishAuthor("Caesar")
                .englishTitle("The Gallic War")
                .location(R.raw.caes_bg_lat)
                .englishLocation(R.raw.caes_bg_eng)
                .workType(WorkInfo.WorkType.PROSE)
                .TOCEntry(new TOCEntry("", 0, 0))
                .TOCEntry(new TOCEntry("", 1, 0))
                .TOCEntry(new TOCEntry("", 2, 0))
                .TOCEntry(new TOCEntry("", 3, 0))
                .TOCEntry(new TOCEntry("", 4, 0))
                .TOCEntry(new TOCEntry("", 5, 0))
                .TOCEntry(new TOCEntry("", 6, 0))
                .TOCEntry(new TOCEntry("", 7, 0))
                .image(R.drawable.work_de_bello_gallico)
                .build());


        workInfos.add(new WorkInfo.Builder("Horace")
                .title("Carmina")
                .author("Q. Horatius Flaccus")
                .englishTitle("The Odes and Carmen Saeculare of Horace")
                .englishAuthor("Horace")
                .location(R.raw.hor_carm_lat)
                .englishLocation(R.raw.hor_carm_eng)
                .workType(WorkInfo.WorkType.POEM)
                .image(R.drawable.work_carmina)
                .build());


        workInfos.add(new WorkInfo.Builder("Lucretius")
                .title("De Rerum Natura")
                .author("T. Lucretius Caro")
                .englishTitle("On the Nature of Things")
                .englishAuthor("Lucretius")
                .location(R.raw.lucretius_lat)
                .englishLocation(R.raw.lucretius_eng)
                .workType(WorkInfo.WorkType.POEM)
                .build());

        workInfos.add(new WorkInfo.Builder("OvidM")
                .title("Metamorphosis")
                .author("P. Ovidius Naso")
                .englishTitle("Metamorphosis")
                .englishAuthor("Ovid")
                .location(R.raw.ovid_met_lat)
                .englishLocation(R.raw.ovid_met_eng)
                .workType(WorkInfo.WorkType.POEM)
                .build());

        workInfos.add(new WorkInfo.Builder("Petronius")
                .title("Satyricon, Fragmenta, and Poems")
                .author("G. Petronius Arbiter")
                .englishTitle("Satyricon, Fragmenta, and Poems")
                .englishAuthor("Petronius")
                .location(R.raw.petr_lat)
                .englishLocation(R.raw.petr_eng)
                .workType(WorkInfo.WorkType.PROSE)
                .build());

        workInfos.add(new WorkInfo.Builder("SalJug")
                .title("Bellum Jugurthinum")
                .author("C. Sallusti Crispi")
                .englishTitle("The Jugurthine War")
                .englishAuthor("Sallust")
                .location(R.raw.sallust_jugur_lat)
                .englishLocation(R.raw.sallust_jugur_eng)
                .workType(WorkInfo.WorkType.PROSE)
                .build());

        workInfos.add(new WorkInfo.Builder("SenApoc")
                .title("Apocolocyntosis")
                .author("L. Annaeus Seneca")
                .englishTitle("Apocolocyntosis")
                .englishAuthor("Seneca")
                .location(R.raw.sen_apoc_lat)
                .englishLocation(R.raw.sen_apoc_eng)
                .workType(WorkInfo.WorkType.PROSE)
                .build());

        workInfos.add(new WorkInfo.Builder("VirgA")
                .title("Aeneid")
                .author("P. Vergilius Maro")
                .englishTitle("The Aeneid")
                .englishAuthor("Vergil")
                .location(R.raw.verg_a_lat)
                .englishLocation(R.raw.verg_a_eng)
                .workType(WorkInfo.WorkType.POEM)
                .build());

        workInfos.add(new WorkInfo.Builder("Livy1")
                .author("Titus Livius")
                .title("Ab Urbe Condita, liber I-II")
                .englishAuthor("Livy")
                .englishTitle("The History of Rome, books 1-2")
                .location(R.raw.livy_01_02_lat)
                .englishLocation(R.raw.livy_01_02_eng)
                .workType(WorkInfo.WorkType.PROSE)
                .build());

        return workInfos;
    }

    /**
     * Gets the resource information of the dictionary file.
     * @return WorkInfo
     */
    @Override
    public WorkInfo getDictionaryInfo() {
        return new WorkInfo.Builder("dictionary")
                .title("An Elementary Latin Dictionary")
                .author("Charles Lewis")
                .location(R.raw.lewis)
                .englishLocation(R.raw.lewis)
                .workType(WorkInfo.WorkType.PROSE)
                .image(com.ericmschmidt.classicsreader.R.drawable.work_default_1)
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