package com.ericmschmidt.latinreader.datamodel;

import com.ericmschmidt.latinreader.R;
import com.ericmschmidt.latinreader.datamodel.WorkInfo;

import java.util.ArrayList;

/**
 * Contains the resource manifest for the app.
 */
public class Manifest {

    /**
     * Gets the collection of works for this app.
     * @return WorkInfo the collection
     */
    public static ArrayList<WorkInfo> getCollection() {
        ArrayList<WorkInfo> workInfos = new ArrayList<WorkInfo>();

        workInfos.add(new WorkInfo("CaesarBG",
                "De Bello Gallico",
                "C. Julius Caesar",
                "The Gallic War",
                "Caesar",
                R.raw.caes_bg_lat,
                R.raw.caes_bg_eng,
                WorkInfo.WorkType.prose));

        workInfos.add(new WorkInfo("Lucretius",
                "De Rerum Natura",
                "T. Lucretius Caro",
                "On the Nature of Things",
                "Lucretius",
                R.raw.lucretius_lat,
                R.raw.lucretius_eng,
                WorkInfo.WorkType.poem));

        workInfos.add(new WorkInfo("OvidM",
                "Metamorphosis",
                "P. Ovidius Naso",
                "Metamorphosis",
                "Ovid",
                R.raw.ovid_met_lat,
                R.raw.ovid_met_eng,
                WorkInfo.WorkType.poem));

        workInfos.add(new WorkInfo("Petronius",
                "Satyricon, Fragmenta, and Poems",
                "G. Petronius Arbiter",
                "Satyricon, Fragmenta, and Poems",
                "Petronius",
                R.raw.petr_lat,
                R.raw.petr_eng,
                WorkInfo.WorkType.prose));

        workInfos.add(new WorkInfo("SalJug",
                "Bellum Jugurthinum",
                "C. Sallusti Crispi",
                "The Jugurthine War",
                "Sallust",
                R.raw.sallust_jugur_lat,
                R.raw.sallust_jugur_eng,
                WorkInfo.WorkType.prose));

        workInfos.add(new WorkInfo("SenApoc",
                "Apocolocyntosis",
                "L. Annaeus Seneca",
                "Apocolocyntosis",
                "Seneca",
                R.raw.sen_apoc_lat,
                R.raw.sen_apoc_eng,
                WorkInfo.WorkType.prose));

        workInfos.add(new WorkInfo("VirgA",
                "Aeneid",
                "P. Vergilius Maro",
                "The Aeneid",
                "Vergil",
                R.raw.verg_a_lat,
                R.raw.verg_a_eng,
                WorkInfo.WorkType.poem));

        return workInfos;
    }

    /**
     * Gets the resource information of the dictionary file.
     * @return WorkInfo
     */
    public static WorkInfo getDictionaryInfo() {
        return new WorkInfo("dictionary",
                "An Elementary Latin Dictionary",
                "Charles Lewis",
                "",
                "",
                R.raw.lewis,
                R.raw.lewis,
                WorkInfo.WorkType.prose);
    }

    /**
     * Gets the resource ID of the dictionary entry file.
     * @return int the dictionary entry file's ID.
     */
    public static int getDictionaryEntryResource() {
        return R.raw.dictionary_entries;
    }
}
