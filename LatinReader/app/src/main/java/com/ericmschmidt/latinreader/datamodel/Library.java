package com.ericmschmidt.latinreader.datamodel;

import java.util.ArrayList;

/**
 * Contains information about the texts included in the reader.
 */
public class Library {

    private ArrayList<WorkInfo>  _workInfos;

    /**
     * Creates an instance of the Library class.
     */
    public Library(ArrayList<WorkInfo> collection) {
        this._workInfos = collection;
    }

    /**
     * Gets the WorkInfo for the specified work.
     * @param id the ID of the work to get.
     * @return WorkInfo object.
     */
    public WorkInfo getWorkInfoByID(String id) {
        WorkInfo workToGet = null;
        for (WorkInfo i : _workInfos) {
            if (i.getId().equals(id)) {
                workToGet = i;
            }
        }
        return workToGet;
    }

    /**
     * Gets the list of works as a series of strings.
     * @return String[]
     */
    public String[] getWorkList() {
        String[] works = new String[this._workInfos.size()];

        for (int i = 0; i < this._workInfos.size(); i++) {
            works[i] = this._workInfos.get(i).getTitle();
        }
        return works;
    }

    /**
     * Gets information about all of the texts in the work.
     * @return WorkInfo[]
     */
    public WorkInfo[] getWorks() {
        WorkInfo[] works = new WorkInfo[this._workInfos.size()];
        return this._workInfos.toArray(works);
    }
}
