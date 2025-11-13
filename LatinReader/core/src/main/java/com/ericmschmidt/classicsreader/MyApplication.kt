package com.ericmschmidt.classicsreader

import android.app.Application
import android.content.Context
import android.util.Log
import com.ericmschmidt.classicsreader.datamodel.Manifest
import com.ericmschmidt.classicsreader.utilities.ITextConverter


/**
 * A subclass of the Application class to help get the app context.
 *
 * @author Eric Schmidt
 * @author <a href="https://telpirion.com">...</a>
 * @version 1.5
 * @since 1.0
 */
class MyApplication : Application() {

    data class ApplicationInstance(
        val context: Context,
        val manifest: Manifest,
        val isNonRomanChar: Boolean,
        val textConverter: ITextConverter?,
    )

    init {
        instance = this
    }

    companion object Factory {
        lateinit var instance: MyApplication
            private set

        /**
         * Get the manifest of texts for this app.
         * @return Manifest
         */
        private fun getManifest(): Manifest {
            val manifestName =
                instance.resources.getString(R.string.manifest)
            Log.i("MyApplication", "manifestName = $manifestName")
            return Manifest.getManifest(manifestName)
        }

        private fun isNonRomanChar(): Boolean {
            return instance.resources.getBoolean(R.bool.non_roman_char)
        }

        private fun getTextConverter(): ITextConverter? {
            var converter: ITextConverter? = null

            if (isNonRomanChar()) {
                val className = instance.resources.getString(R.string.text_converter)

                try {
                    val manifestClass = Class.forName(className)
                    val constructors = manifestClass.constructors
                    converter = constructors[0].newInstance() as ITextConverter
                } catch (ex: Exception) {
                    Log.e(Manifest::class.java.name, ex.message as String)
                }
            }
            return converter
        }

        fun applicationInstance(): ApplicationInstance {
            return ApplicationInstance(
                context = instance,
                manifest = getManifest(),
                isNonRomanChar = isNonRomanChar(),
                textConverter = getTextConverter()
            )
        }
    }
}