package com.ericmschmidt.classicsreader.data

import androidx.compose.runtime.Composable
import com.ericmschmidt.classicsreader.logError
import java.util.ArrayList

/** Contains information about the texts included in the reader.
 * @author Eric Schmidt
 * @author <a href="https://telpirion.com">...</a>
 * @version 2.0
 * @since 1.0
 */
open class Library {

    open fun getCollection(): ArrayList<WorkInfo>? {
        return null
    }

    open fun getDictionaryInfo(): WorkInfo? {
        return null
    }

    open fun getDictionaryEntryResource(): Int {
        return 0
    }

    private val collection: ArrayList<WorkInfo>? = getCollection()

    /**
     * Gets the WorkInfo for the specified work ID.
     */
    fun getWorkInfoByID(id: String?): WorkInfo? {
        return collection?.find { it.id == id }
    }

    /**
     * Gets information about all of the texts in the work.
     */
    fun getWorks(): Array<WorkInfo> {
        return collection?.toTypedArray() ?: emptyArray()
    }

    @Composable
    open fun GetHeaderIcon() {}

    companion object {
        /**
         * Get the library class out of the package using reflection.
         * @param className the fully-qualified class name as a string.
         * @return Library
         */
        fun getLibrary(className: String): Library? {
            var library: Library? = null
            try {
                val libraryClass = Class.forName(className)
                val constructors = libraryClass.constructors
                library = constructors[0]!!.newInstance() as Library
            } catch (ex: Exception) {
                logError(Library::class.java, ex.message)
            }
            return library
        }
    }
}