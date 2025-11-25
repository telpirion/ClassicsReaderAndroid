package com.ericmschmidt.latinreader.datamodel;

import static com.ericmschmidt.latinreader.datamodel.LatinReaderManifestDescriptionsKt.getCaesarDescription;
import static com.ericmschmidt.latinreader.datamodel.LatinReaderManifestDescriptionsKt.getHoraceDescription;
import static com.ericmschmidt.latinreader.datamodel.LatinReaderManifestDescriptionsKt.getLivyDescription;
import static com.ericmschmidt.latinreader.datamodel.LatinReaderManifestDescriptionsKt.getLucretiusDescription;
import static com.ericmschmidt.latinreader.datamodel.LatinReaderManifestDescriptionsKt.getOvidDescription;
import static com.ericmschmidt.latinreader.datamodel.LatinReaderManifestDescriptionsKt.getPetroniusDescription;
import static com.ericmschmidt.latinreader.datamodel.LatinReaderManifestDescriptionsKt.getSallustDescription;
import static com.ericmschmidt.latinreader.datamodel.LatinReaderManifestDescriptionsKt.getSenecaDescription;
import static com.ericmschmidt.latinreader.datamodel.LatinReaderManifestDescriptionsKt.getVergilDescription;

import com.ericmschmidt.classicsreader.data.Manifest;
import com.ericmschmidt.classicsreader.data.TOCEntry;
import com.ericmschmidt.classicsreader.data.WorkInfo;
import com.ericmschmidt.latinreader.R;

import java.util.ArrayList;

/** Contains the resource manifest for the Latin Reader app.
 *
 * @author Eric Schmidt
 * @author <a href="https://telpirion.com">...</a>
 * @version 1.5
 * @since 1.1
 * @noinspection unused
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
        ArrayList<WorkInfo> workInfos = new ArrayList<>();

        workInfos.add(new WorkInfo.Builder("CaesarBG")
                .author("C. Julius Caesar")
                .title("De Bello Gallico")
                .englishAuthor("Caesar")
                .englishTitle("The Gallic War")
                .location(R.raw.caes_bg_lat)
                .englishLocation(R.raw.caes_bg_eng)
                .workType(WorkInfo.WorkType.PROSE)
                .tocEntry(new TOCEntry("COMMENTARIUS PRIMUS", 0, 0))
                .tocEntry(new TOCEntry("COMMENTARIUS SECUNDUS", 1, 0))
                .tocEntry(new TOCEntry("COMMENTARIUS TERTIUS", 2, 0))
                .tocEntry(new TOCEntry("COMMENTARIUS QUARTUS", 3, 0))
                .tocEntry(new TOCEntry("COMMENTARIUS QUINTUS", 4, 0))
                .tocEntry(new TOCEntry("COMMENTARIUS SEXTUS", 5, 0))
                .tocEntry(new TOCEntry("COMMENTARIUS SEPTIMUS", 6, 0))
                .tocEntry(new TOCEntry("COMMENTARIUS OCTAVUS", 7, 0))
                .image(R.drawable.work_de_bello_gallico)
                .description(getCaesarDescription())
                .editor("T. Rice Holmes")
                .translator("W. A. McDevitte, W. S. Bohn")
                .build());

        // TODO(telpirion): parse <div2> into poem numbers or headings?
        workInfos.add(new WorkInfo.Builder("Horace")
                .title("Carmina")
                .author("Q. Horatius Flaccus")
                .englishTitle("The Odes and Carmen Saeculare of Horace")
                .englishAuthor("Horace")
                .location(R.raw.hor_carm_lat)
                .englishLocation(R.raw.hor_carm_eng)
                .workType(WorkInfo.WorkType.POEM)
                .tocEntry(new TOCEntry("Book 1", 0, 0))
                .tocEntry(new TOCEntry("Book 2", 1, 0))
                .tocEntry(new TOCEntry("Book 3", 2, 0))
                .tocEntry(new TOCEntry("Book 4", 3, 0))
                .image(R.drawable.work_carmina)
                .description(getHoraceDescription())
                .editor("Paul Shorey and Gordon J. Laing")
                .translator("John Conington")
                .build());

        workInfos.add(new WorkInfo.Builder("Lucretius")
                .title("De Rerum Natura")
                .author("T. Lucretius Caro")
                .englishTitle("On the Nature of Things")
                .englishAuthor("Lucretius")
                .location(R.raw.lucretius_lat)
                .englishLocation(R.raw.lucretius_eng)
                .workType(WorkInfo.WorkType.POEM)
                .tocEntry(new TOCEntry("Liber Primus", 0, 0))
                .tocEntry(new TOCEntry("Liber Secundus", 1, 0))
                .tocEntry(new TOCEntry("Liber Tertius", 2, 0))
                .tocEntry(new TOCEntry("Liber Quartus", 3, 0))
                .tocEntry(new TOCEntry("Liber Quintus", 4, 0))
                .tocEntry(new TOCEntry("Liber Sextus", 5, 0))
                .image(R.drawable.work_de_rerum_natura)
                .description(getLucretiusDescription())
                .editor("William Ellery Leonard")
                .translator("William Ellery Leonard")
                .build());

        workInfos.add(new WorkInfo.Builder("OvidM")
                .title("Metamorphoses")
                .author("P. Ovidius Naso")
                .englishTitle("Metamorphoses")
                .englishAuthor("Ovid")
                .location(R.raw.ovid_met_lat)
                .englishLocation(R.raw.ovid_met_eng)
                .workType(WorkInfo.WorkType.POEM)
                .tocEntry(new TOCEntry("Book 1", 0, 0))
                .tocEntry(new TOCEntry("Book 2", 1, 0))
                .tocEntry(new TOCEntry("Book 3", 2, 0))
                .tocEntry(new TOCEntry("Book 4", 3, 0))
                .tocEntry(new TOCEntry("Book 5", 4, 0))
                .tocEntry(new TOCEntry("Book 6", 5, 0))
                .tocEntry(new TOCEntry("Book 7", 6, 0))
                .tocEntry(new TOCEntry("Book 8", 7, 0))
                .tocEntry(new TOCEntry("Book 9", 8, 0))
                .tocEntry(new TOCEntry("Book 10", 9, 0))
                .tocEntry(new TOCEntry("Book 11", 10, 0))
                .tocEntry(new TOCEntry("Book 12", 11, 0))
                .tocEntry(new TOCEntry("Book 13", 12, 0))
                .tocEntry(new TOCEntry("Book 14", 13, 0))
                .tocEntry(new TOCEntry("Book 15", 14, 0))
                .image(R.drawable.work_metamorphoses)
                .description(getOvidDescription())
                .editor("Hugo Magnus")
                .translator("Brookes More")
                .build());

        workInfos.add(new WorkInfo.Builder("Petronius")
                .title("Satyricon, Fragmenta, and Poems")
                .author("G. Petronius Arbiter")
                .englishTitle("Satyricon, Fragmenta, and Poems")
                .englishAuthor("Petronius")
                .location(R.raw.petr_lat)
                .englishLocation(R.raw.petr_eng)
                .workType(WorkInfo.WorkType.PROSE)
                .tocEntry(new TOCEntry("Satyricon", 0, 0))
                .tocEntry(new TOCEntry("Fragments", 1, 0))
                .tocEntry(new TOCEntry("Poems", 2, 0))
                .image(R.drawable.work_satyricon)
                .description(getPetroniusDescription())
                .editor("Michael Heseltine")
                .translator("Michael Heseltine")
                .build());

        workInfos.add(new WorkInfo.Builder("SalJug")
                .title("Bellum Jugurthinum")
                .author("C. Sallusti Crispi")
                .englishTitle("The Jugurthine War")
                .englishAuthor("Sallust")
                .location(R.raw.sallust_jugur_lat)
                .englishLocation(R.raw.sallust_jugur_eng)
                .workType(WorkInfo.WorkType.PROSE)
                .tocEntry(new TOCEntry("Chapter 1", 0, 0))
                .tocEntry(new TOCEntry("Chapter 2", 1, 0))
                .tocEntry(new TOCEntry("Chapter 3", 2, 0))
                .tocEntry(new TOCEntry("Chapter 4", 3, 0))
                .tocEntry(new TOCEntry("Chapter 5", 4, 0))
                .tocEntry(new TOCEntry("Chapter 6", 5, 0))
                .tocEntry(new TOCEntry("Chapter 7", 6, 0))
                .tocEntry(new TOCEntry("Chapter 8", 7, 0))
                .tocEntry(new TOCEntry("Chapter 9", 8, 0))
                .tocEntry(new TOCEntry("Chapter 10", 9, 0))
                .tocEntry(new TOCEntry("Chapter 11", 10, 0))
                .tocEntry(new TOCEntry("Chapter 12", 11, 0))
                .tocEntry(new TOCEntry("Chapter 13", 12, 0))
                .tocEntry(new TOCEntry("Chapter 14", 13, 0))
                .tocEntry(new TOCEntry("Chapter 15", 14, 0))
                .tocEntry(new TOCEntry("Chapter 16", 15, 0))
                .tocEntry(new TOCEntry("Chapter 17", 16, 0))
                .tocEntry(new TOCEntry("Chapter 18", 17, 0))
                .tocEntry(new TOCEntry("Chapter 19", 18, 0))
                .tocEntry(new TOCEntry("Chapter 20", 19, 0))
                .tocEntry(new TOCEntry("Chapter 21", 20, 0))
                .tocEntry(new TOCEntry("Chapter 22", 21, 0))
                .tocEntry(new TOCEntry("Chapter 23", 22, 0))
                .tocEntry(new TOCEntry("Chapter 24", 23, 0))
                .tocEntry(new TOCEntry("Chapter 25", 24, 0))
                .tocEntry(new TOCEntry("Chapter 26", 25, 0))
                .tocEntry(new TOCEntry("Chapter 27", 26, 0))
                .tocEntry(new TOCEntry("Chapter 28", 27, 0))
                .tocEntry(new TOCEntry("Chapter 29", 28, 0))
                .tocEntry(new TOCEntry("Chapter 30", 29, 0))
                .tocEntry(new TOCEntry("Chapter 31", 30, 0))
                .tocEntry(new TOCEntry("Chapter 32", 31, 0))
                .tocEntry(new TOCEntry("Chapter 33", 32, 0))
                .tocEntry(new TOCEntry("Chapter 34", 33, 0))
                .tocEntry(new TOCEntry("Chapter 35", 34, 0))
                .tocEntry(new TOCEntry("Chapter 36", 35, 0))
                .tocEntry(new TOCEntry("Chapter 37", 36, 0))
                .tocEntry(new TOCEntry("Chapter 38", 37, 0))
                .tocEntry(new TOCEntry("Chapter 39", 38, 0))
                .tocEntry(new TOCEntry("Chapter 40", 39, 0))
                .tocEntry(new TOCEntry("Chapter 41", 40, 0))
                .tocEntry(new TOCEntry("Chapter 42", 41, 0))
                .tocEntry(new TOCEntry("Chapter 43", 42, 0))
                .tocEntry(new TOCEntry("Chapter 44", 43, 0))
                .tocEntry(new TOCEntry("Chapter 45", 44, 0))
                .tocEntry(new TOCEntry("Chapter 46", 45, 0))
                .tocEntry(new TOCEntry("Chapter 47", 46, 0))
                .tocEntry(new TOCEntry("Chapter 48", 47, 0))
                .tocEntry(new TOCEntry("Chapter 49", 48, 0))
                .tocEntry(new TOCEntry("Chapter 50", 49, 0))
                .tocEntry(new TOCEntry("Chapter 51", 50, 0))
                .tocEntry(new TOCEntry("Chapter 52", 51, 0))
                .tocEntry(new TOCEntry("Chapter 53", 52, 0))
                .tocEntry(new TOCEntry("Chapter 54", 53, 0))
                .tocEntry(new TOCEntry("Chapter 55", 54, 0))
                .tocEntry(new TOCEntry("Chapter 56", 55, 0))
                .tocEntry(new TOCEntry("Chapter 57", 56, 0))
                .tocEntry(new TOCEntry("Chapter 58", 57, 0))
                .tocEntry(new TOCEntry("Chapter 59", 58, 0))
                .tocEntry(new TOCEntry("Chapter 60", 59, 0))
                .tocEntry(new TOCEntry("Chapter 61", 60, 0))
                .tocEntry(new TOCEntry("Chapter 62", 61, 0))
                .tocEntry(new TOCEntry("Chapter 63", 62, 0))
                .tocEntry(new TOCEntry("Chapter 64", 63, 0))
                .tocEntry(new TOCEntry("Chapter 65", 64, 0))
                .tocEntry(new TOCEntry("Chapter 66", 65, 0))
                .tocEntry(new TOCEntry("Chapter 67", 66, 0))
                .tocEntry(new TOCEntry("Chapter 68", 67, 0))
                .tocEntry(new TOCEntry("Chapter 69", 68, 0))
                .tocEntry(new TOCEntry("Chapter 70", 69, 0))
                .tocEntry(new TOCEntry("Chapter 71", 70, 0))
                .tocEntry(new TOCEntry("Chapter 72", 71, 0))
                .tocEntry(new TOCEntry("Chapter 73", 72, 0))
                .tocEntry(new TOCEntry("Chapter 74", 73, 0))
                .tocEntry(new TOCEntry("Chapter 75", 74, 0))
                .tocEntry(new TOCEntry("Chapter 76", 75, 0))
                .tocEntry(new TOCEntry("Chapter 77", 76, 0))
                .tocEntry(new TOCEntry("Chapter 78", 77, 0))
                .tocEntry(new TOCEntry("Chapter 79", 78, 0))
                .tocEntry(new TOCEntry("Chapter 80", 79, 0))
                .tocEntry(new TOCEntry("Chapter 81", 80, 0))
                .tocEntry(new TOCEntry("Chapter 82", 81, 0))
                .tocEntry(new TOCEntry("Chapter 83", 82, 0))
                .tocEntry(new TOCEntry("Chapter 84", 83, 0))
                .tocEntry(new TOCEntry("Chapter 85", 84, 0))
                .tocEntry(new TOCEntry("Chapter 86", 85, 0))
                .tocEntry(new TOCEntry("Chapter 87", 86, 0))
                .tocEntry(new TOCEntry("Chapter 88", 87, 0))
                .tocEntry(new TOCEntry("Chapter 89", 88, 0))
                .tocEntry(new TOCEntry("Chapter 90", 89, 0))
                .tocEntry(new TOCEntry("Chapter 91", 90, 0))
                .tocEntry(new TOCEntry("Chapter 92", 91, 0))
                .tocEntry(new TOCEntry("Chapter 93", 92, 0))
                .tocEntry(new TOCEntry("Chapter 94", 93, 0))
                .tocEntry(new TOCEntry("Chapter 95", 94, 0))
                .tocEntry(new TOCEntry("Chapter 96", 95, 0))
                .tocEntry(new TOCEntry("Chapter 97", 96, 0))
                .tocEntry(new TOCEntry("Chapter 98", 97, 0))
                .tocEntry(new TOCEntry("Chapter 99", 98, 0))
                .tocEntry(new TOCEntry("Chapter 100", 99, 0))
                .tocEntry(new TOCEntry("Chapter 101", 100, 0))
                .tocEntry(new TOCEntry("Chapter 102", 101, 0))
                .tocEntry(new TOCEntry("Chapter 103", 102, 0))
                .tocEntry(new TOCEntry("Chapter 104", 103, 0))
                .tocEntry(new TOCEntry("Chapter 105", 104, 0))
                .tocEntry(new TOCEntry("Chapter 106", 105, 0))
                .tocEntry(new TOCEntry("Chapter 107", 106, 0))
                .tocEntry(new TOCEntry("Chapter 108", 107, 0))
                .tocEntry(new TOCEntry("Chapter 109", 108, 0))
                .tocEntry(new TOCEntry("Chapter 110", 109, 0))
                .tocEntry(new TOCEntry("Chapter 111", 110, 0))
                .tocEntry(new TOCEntry("Chapter 112", 111, 0))
                .tocEntry(new TOCEntry("Chapter 113", 112, 0))
                .tocEntry(new TOCEntry("Chapter 114", 113, 0))
                .image(R.drawable.work_de_bellum_jugurthinum)
                .description(getSallustDescription())
                .editor("John Selby Watson")
                .translator("John Selby Watson")
                .build());

        workInfos.add(new WorkInfo.Builder("SenApoc")
                .title("Apocolocyntosis")
                .author("L. Annaeus Seneca")
                .englishTitle("Apocolocyntosis")
                .englishAuthor("Seneca")
                .location(R.raw.sen_apoc_lat)
                .englishLocation(R.raw.sen_apoc_eng)
                .workType(WorkInfo.WorkType.PROSE)
                .tocEntry(new TOCEntry("Section 1", 0, 0))
                .tocEntry(new TOCEntry("Section 2", 1, 0))
                .tocEntry(new TOCEntry("Section 3", 2, 0))
                .tocEntry(new TOCEntry("Section 4", 3, 0))
                .tocEntry(new TOCEntry("Section 5", 4, 0))
                .tocEntry(new TOCEntry("Section 6", 5, 0))
                .tocEntry(new TOCEntry("Section 7", 6, 0))
                .tocEntry(new TOCEntry("Section 8", 7, 0))
                .tocEntry(new TOCEntry("Section 9", 8, 0))
                .tocEntry(new TOCEntry("Section 10", 9, 0))
                .tocEntry(new TOCEntry("Section 11", 10, 0))
                .tocEntry(new TOCEntry("Section 12", 11, 0))
                .tocEntry(new TOCEntry("Section 13", 12, 0))
                .tocEntry(new TOCEntry("Section 14", 13, 0))
                .tocEntry(new TOCEntry("Section 15", 14, 0))
                .image(R.drawable.work_apocolocyntosis)
                .description(getSenecaDescription())
                .editor("W.H.D. Rouse,  M.A. Litt. D.")
                .translator("W.H.D. Rouse")
                .build());

        workInfos.add(new WorkInfo.Builder("VirgA")
                .title("Aeneid")
                .author("P. Vergilius Maro")
                .englishTitle("The Aeneid")
                .englishAuthor("Vergil")
                .location(R.raw.verg_a_lat)
                .englishLocation(R.raw.verg_a_eng)
                .workType(WorkInfo.WorkType.POEM)
                .tocEntry(new TOCEntry("Book 1", 0, 0))
                .tocEntry(new TOCEntry("Book 2", 1, 0))
                .tocEntry(new TOCEntry("Book 3", 2, 0))
                .tocEntry(new TOCEntry("Book 4", 3, 0))
                .tocEntry(new TOCEntry("Book 5", 4, 0))
                .tocEntry(new TOCEntry("Book 6", 5, 0))
                .tocEntry(new TOCEntry("Book 7", 6, 0))
                .tocEntry(new TOCEntry("Book 8", 7, 0))
                .tocEntry(new TOCEntry("Book 9", 8, 0))
                .tocEntry(new TOCEntry("Book 10", 9, 0))
                .tocEntry(new TOCEntry("Book 11", 10, 0))
                .tocEntry(new TOCEntry("Book 12", 11, 0))
                .image(R.drawable.work_aeneid)
                .description(getVergilDescription())
                .editor("J. B. Greenough")
                .translator("Theodore C. Williams")
                .build());

        workInfos.add(new WorkInfo.Builder("Livy1")
                .author("Titus Livius")
                .title("Ab Urbe Condita, liber I-II")
                .englishAuthor("Livy")
                .englishTitle("The History of Rome, books 1-2")
                .location(R.raw.livy_01_02_lat)
                .englishLocation(R.raw.livy_01_02_eng)
                .workType(WorkInfo.WorkType.PROSE)
                .tocEntry(new TOCEntry("Book 1", 0, 0))
                .tocEntry(new TOCEntry("Book 2", 1, 0))
                .image(R.drawable.work_ab_urbe_condita)
                .description(getLivyDescription())
                .editor("Benjamin Oliver Foster, Ph.D.")
                .translator("Benjamin Oliver Foster, Ph.D.")
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
