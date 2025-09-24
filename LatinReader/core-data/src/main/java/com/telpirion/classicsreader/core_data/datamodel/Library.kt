package com.telpirion.classicsreader.core_data.datamodel

/**
 * Stores a set of works in the app
 * @author Eric Schmidt
 * @author <a href="http://telpirion.com">...</a>
 * @version 1.5
 * @since 1.1
 */
class Library(private val workInfos: ArrayList<WorkInfo>) {

    fun getWorkInfoByID(id: String): WorkInfo? {
        return workInfos.find { it.id == id }
    }

    fun getWorkList(): Array<String> {
        return workInfos.map { it.title }.toTypedArray()
    }

    fun getWorks(): Array<WorkInfo> {
        return workInfos.toTypedArray()
    }
}