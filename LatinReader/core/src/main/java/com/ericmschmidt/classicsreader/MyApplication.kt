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
 * @version 2.0
 * @since 1.0
 */
class MyApplication : Application() {

    data class ApplicationInstance(
        val context: Context,
        val manifest: Manifest,
        val isNonRomanChar: Boolean,
        val textConverter: ITextConverter?,
    )

    var manifest: Manifest? = null
        private set
    var isNonRomanChar: Boolean? = null
        private set
    var converter: ITextConverter? = null
        private set

    private fun getTextConverter(isNonRomanChar: Boolean): ITextConverter? {
        var converter: ITextConverter? = null
        if (isNonRomanChar) {
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

    init {
        instance = this
    }

    fun populateFields() {
        Log.i("MyApplication", "populating Application fields")
        if (manifest == null) {
            val manifestName =
                instance.resources.getString(R.string.manifest)
            manifest = Manifest.getManifest(manifestName)!!
        }
        if (isNonRomanChar == null) {
            isNonRomanChar = instance.resources.getBoolean(R.bool.non_roman_char)
        }
        if (converter == null) {
            if (getTextConverter(isNonRomanChar as Boolean) != null) {
                converter = getTextConverter(isNonRomanChar as Boolean)
            }
        }
    }


    companion object Factory {
        lateinit var instance: MyApplication
            private set

        fun applicationInstance(): ApplicationInstance {
            instance.populateFields()
            return ApplicationInstance(
                context = instance,
                manifest = instance.manifest as Manifest,
                isNonRomanChar = instance.isNonRomanChar as Boolean,
                textConverter = instance.converter
            )
        }
    }
}