package com.ericmschmidt.latinreader.datamodel;

import com.ericmschmidt.latinreader.R;
import com.ericmschmidt.latinreader.datamodel.WorkInfo;

import java.util.ArrayList;

/**
 * Contains the resource manifest for the app.
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

        workInfos.add(new WorkInfo("CaesarBG",
                "De Bello Gallico",
                "C. Julius Caesar",
                "The Gallic War",
                "Caesar",
                R.raw.caes_bg_lat,
                R.raw.caes_bg_eng,
                WorkInfo.WorkType.PROSE));

        workInfos.add(new WorkInfo("Horace",
                "Carmina",
                "Q. Horatius Flaccus",
                "The Odes and Carmen Saeculare of Horace",
                "Horace",
                R.raw.hor_carm_lat,
                R.raw.hor_carm_eng,
                WorkInfo.WorkType.POEM));

        workInfos.add(new WorkInfo("Lucretius",
                "De Rerum Natura",
                "T. Lucretius Caro",
                "On the Nature of Things",
                "Lucretius",
                R.raw.lucretius_lat,
                R.raw.lucretius_eng,
                WorkInfo.WorkType.POEM));

        workInfos.add(new WorkInfo("OvidM",
                "Metamorphosis",
                "P. Ovidius Naso",
                "Metamorphosis",
                "Ovid",
                R.raw.ovid_met_lat,
                R.raw.ovid_met_eng,
                WorkInfo.WorkType.POEM));

        workInfos.add(new WorkInfo("Petronius",
                "Satyricon, Fragmenta, and Poems",
                "G. Petronius Arbiter",
                "Satyricon, Fragmenta, and Poems",
                "Petronius",
                R.raw.petr_lat,
                R.raw.petr_eng,
                WorkInfo.WorkType.PROSE));

        workInfos.add(new WorkInfo("SalJug",
                "Bellum Jugurthinum",
                "C. Sallusti Crispi",
                "The Jugurthine War",
                "Sallust",
                R.raw.sallust_jugur_lat,
                R.raw.sallust_jugur_eng,
                WorkInfo.WorkType.PROSE));

        workInfos.add(new WorkInfo("SenApoc",
                "Apocolocyntosis",
                "L. Annaeus Seneca",
                "Apocolocyntosis",
                "Seneca",
                R.raw.sen_apoc_lat,
                R.raw.sen_apoc_eng,
                WorkInfo.WorkType.PROSE));

        workInfos.add(new WorkInfo("VirgA",
                "Aeneid",
                "P. Vergilius Maro",
                "The Aeneid",
                "Vergil",
                R.raw.verg_a_lat,
                R.raw.verg_a_eng,
                WorkInfo.WorkType.POEM));

        workInfos.add(new WorkInfo.Builder("Livy1")
                .author("Titus Livius")
                .title("Ab Urbe Condita, liber I-II")
                .englishAuthor("Livy")
                .englishTitle("The History of Rome, books 1-2")
                .location(R.raw.livy_01_02_lat)
                .englishLocation(R.raw.livy_01_02_eng)
                .workType(WorkInfo.WorkType.PROSE)
                .create());

        return workInfos;
    }

    /**
     * Gets the resource information of the dictionary file.
     * @return WorkInfo
     */
    @Override
    public WorkInfo getDictionaryInfo() {
        return new WorkInfo("dictionary",
                "An Elementary Latin Dictionary",
                "Charles Lewis",
                "",
                "",
                R.raw.lewis,
                R.raw.lewis,
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
