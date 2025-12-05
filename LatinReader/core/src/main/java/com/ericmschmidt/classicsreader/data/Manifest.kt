package com.ericmschmidt.classicsreader.data

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image as Img
import com.ericmschmidt.classicsreader.logError

/** Contains information about the texts included in the reader.
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

    @Composable
    open fun GetHeaderIcon():  Unit {
        return
    }

    companion object {
        /**
         * Get the manifest class out of the package using reflection.
         * @param className the fully-qualified class name as a string.
         * @return Manifest
         */
        fun getManifest(className: String): Manifest? {
            var manifest: Manifest? = null
            try {
                val manifestClass = Class.forName(className)
                val constructors = manifestClass.constructors
                manifest = constructors[0]!!.newInstance() as Manifest
            } catch (ex: Exception) {
                logError(Manifest::class.java, ex.message)
            }
            return manifest
        }
    }
}