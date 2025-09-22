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
                .image(R.drawable.work_metamorphoses)
                .description(getOvidDescription())
                .build());

        workInfos.add(new WorkInfo.Builder("Petronius")
                .title("Satyricon, Fragmenta, and Poems")
                .author("G. Petronius Arbiter")
                .englishTitle("Satyricon, Fragmenta, and Poems")
                .englishAuthor("Petronius")
                .location(R.raw.petr_lat)
                .englishLocation(R.raw.petr_eng)
                .workType(WorkInfo.WorkType.PROSE)
                .image(R.drawable.work_satyricon)
                .description(getPetroniusDescription())
                .build());

        workInfos.add(new WorkInfo.Builder("SalJug")
                .title("Bellum Jugurthinum")
                .author("C. Sallusti Crispi")
                .englishTitle("The Jugurthine War")
                .englishAuthor("Sallust")
                .location(R.raw.sallust_jugur_lat)
                .englishLocation(R.raw.sallust_jugur_eng)
                .workType(WorkInfo.WorkType.PROSE)
                .image(R.drawable.work_de_bellum_jugurthinum)
                .description(getSallustDescription())
                .build());

        workInfos.add(new WorkInfo.Builder("SenApoc")
                .title("Apocolocyntosis")
                .author("L. Annaeus Seneca")
                .englishTitle("Apocolocyntosis")
                .englishAuthor("Seneca")
                .location(R.raw.sen_apoc_lat)
                .englishLocation(R.raw.sen_apoc_eng)
                .workType(WorkInfo.WorkType.PROSE)
                .image(R.drawable.work_apocolocyntosis)
                .description(getSenecaDescription())
                .build());

        workInfos.add(new WorkInfo.Builder("VirgA")
                .title("Aeneid")
                .author("P. Vergilius Maro")
                .englishTitle("The Aeneid")
                .englishAuthor("Vergil")
                .location(R.raw.verg_a_lat)
                .englishLocation(R.raw.verg_a_eng)
                .workType(WorkInfo.WorkType.POEM)
                .image(R.drawable.work_aeneid)
                .description(getVergilDescription())
                .build());

        workInfos.add(new WorkInfo.Builder("Livy1")
                .author("Titus Livius")
                .title("Ab Urbe Condita, liber I-II")
                .englishAuthor("Livy")
                .englishTitle("The History of Rome, books 1-2")
                .location(R.raw.livy_01_02_lat)
                .englishLocation(R.raw.livy_01_02_eng)
                .workType(WorkInfo.WorkType.PROSE)
                .image(R.drawable.work_ab_urbe_condita)
                .description(getLivyDescription())
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