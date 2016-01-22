package com.example.ericmschmidt.classicsreaderandroid;

import java.util.ArrayList;

/**
 * Created by ericmschmidt on 1/6/16.
 */
public class Manifest {

    /**
     * Gets the collection of works for this app.
     * @return the collection
     */
    public static ArrayList<WorkInfo> getCollection()
    {
        ArrayList<WorkInfo> workInfos = new ArrayList<WorkInfo>();

        workInfos.add(new WorkInfo("CaesarBG",
                "De Bello Gallico",
                "C. Julius Caesar",
                "The Gallic War",
                "Caesar",
                "caes.bg_lat.xml",
                "caes.bg_eng.xml"));

        workInfos.add(new WorkInfo("CicOff",
                "De Officiis",
                "M. Tullius Cicero",
                "On Duties",
                "Cicero",
                "cic.off_lat.xml",
                "cic.off_eng.xml"));

        workInfos.add(new WorkInfo("Lucretius",
                "De Rerum Natura",
                "T. Lucretius Caro",
                "On the Nature of Things",
                "Lucretius",
                "lucretius_lat.xml",
                "lucretius_eng.xml"));

        workInfos.add(new WorkInfo("OvidM",
                "Metamorphosis",
                "P. Ovidius Naso",
                "Metamorphosis",
                "Ovid",
                "ovid.met_lat.xml",
                "ovid.met_engl.xml"));

        workInfos.add(new WorkInfo("Petronius",
                "Satyricon, Fragmenta, and Poems",
                "G. Petronius Arbiter",
                "Satyricon, Fragmenta, and Poems",
                "Petronius",
                "petr_lat.xml",
                "petr_eng.xml"));

        workInfos.add(new WorkInfo("SalJug",
                "Bellum Jugurthinum",
                "C. Sallusti Crispi",
                "The Jugurthine War",
                "Sallust",
                "sallust.jugur_lat.xml",
                "sallust.jugur_eng.xml"));

        workInfos.add(new WorkInfo("SenApoc",
                "Apocolocyntosis",
                "L. Annaeus Seneca",
                "Apocolocyntosis",
                "Seneca",
                "sen.apoc_lat.xml",
                "sen.apoc_eng.xml"));

        workInfos.add(new WorkInfo("VirgA",
                "Aeneid",
                "P. Vergilius Maro",
                "The Aeneid",
                "Vergil",
                "verg.a_lat.xml",
                "verg.a.w_eng.xml"));

        return workInfos;
    }
}
