package com.ericmschmidt.classicsreader.datamodel

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
        var workToGet: WorkInfo? = null
        for (i in collection) {
            if (i.id == id) {
                workToGet = i
            }
        }
        return workToGet
    }

    /**
     * Gets the list of works as a series of strings.
     */
    fun getWorkList(): Array<String?> {
        val works = arrayOfNulls<String>(this.collection.size)

        for (i in this.collection.indices) {
            works[i] = this.collection[i].title
        }
        return works
    }

    /**
     * Gets information about all of the texts in the work.
     */
    fun getWorks(): Array<WorkInfo?> {
        val works = arrayOfNulls<WorkInfo>(this.collection.size)
        return this.collection.toArray<WorkInfo?>(works)
    }
}