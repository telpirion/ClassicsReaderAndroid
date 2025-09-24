package com.telpirion.classicsreader.core_data.datamodel

import java.lang.reflect.InvocationTargetException

/**
 * Stores information about the works (source works, translations) in this app.
 * @author Eric Schmidt
 * @author <a href="http://telpirion.com">...</a>
 * @version 1.5
 * @since 1.1
 */
open class Manifest {

    open fun getCollection(): ArrayList<WorkInfo>? {
        return null
    }

    open fun getDictionaryInfo(): WorkInfo? {
        return null
    }

    open fun getDictionaryEntryResource(): Int {
        return 0
    }

    companion object {
        /**
         * Get the manifest class out of the package using reflection.
         * @param className the fully-qualified class name as a string.
         * @return Manifest
         */
        @Throws(
            ClassNotFoundException::class,
            InvocationTargetException::class,
            IllegalAccessException::class,
            InstantiationException::class
        )
        fun getManifest(className: String): Manifest {
            return try {
                val manifestClass = Class.forName(className)
                val constructor = manifestClass.constructors.first()
                constructor.newInstance() as Manifest
            } catch (ex: Exception) {
                println("Manifest: ${ex.message}")
                throw ex
            }
        }
    }
}