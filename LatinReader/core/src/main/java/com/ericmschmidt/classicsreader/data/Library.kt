package com.ericmschmidt.classicsreader.data

/** Contains information about the texts included in the reader.
 * @author Eric Schmidt
 * @author <a href="https://telpirion.com">...</a>
 * @version 2.0
 * @since 1.0
 */
class Library(val collection: ArrayList<WorkInfo>) {

    /**
     * Gets the WorkInfo for the specified work ID.
     */
    fun getWorkInfoByID(id: String?): WorkInfo? {
        return collection.find { it.id == id }
    }

    /**
     * Gets the list of works as a series of strings.
     */
    fun getWorkList(): Array<String?> {
        return collection.map { it.title }.toTypedArray()
    }

    /**
     * Gets information about all of the texts in the work.
     */
    fun getWorks(): Array<WorkInfo> {
        return collection.toTypedArray()
    }
}