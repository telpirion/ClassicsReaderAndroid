package com.ericmschmidt.classicsreader

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.material3.ColorScheme
import com.ericmschmidt.classicsreader.data.Library
import com.ericmschmidt.classicsreader.utilities.ITextConverter


/**
 * A subclass of the Application class to help get the app context.
 *
 * @author Eric Schmidt
 * @author <a href="https://telpirion.com">...</a>
 * @version 2.0
 * @since 1.0
 */
open class MyApplication(
    val libraryName: String,
    val themeColors: ColorScheme
) : Application() {

    data class ApplicationInstance(
        val context: Context,
        val library: Library,
        val isNonRomanChar: Boolean,
        val textConverter: ITextConverter?,
    )

    val library: Library by lazy {
        Log.i("MyApplication", "library = $libraryName")
        Library.getLibrary(libraryName)!!

    }
    val isNonRomanChar: Boolean by lazy {
        val isIt = instance.resources.getBoolean(R.bool.non_roman_char)
        Log.i("MyApplication", "isNonRomanChar = $isIt")
        isIt
    }
    var converter: ITextConverter? = null
        private set

    private fun getTextConverter(isNonRomanChar: Boolean): ITextConverter? {
        var converter: ITextConverter? = null
        if (isNonRomanChar) {
            val className = instance.resources.getString(R.string.text_converter)
            try {
                val libraryClass = Class.forName(className)
                val constructors = libraryClass.constructors
                converter = constructors[0].newInstance() as ITextConverter
            } catch (ex: Exception) {
                Log.e(Library::class.java.name, ex.message as String)
            }
        }
        return converter
    }

    init {
        instance = this
    }

    fun populateFields() {
        if (converter == null && isNonRomanChar) {
            if (getTextConverter(isNonRomanChar) != null) {
                converter = getTextConverter(isNonRomanChar)
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
                library = instance.library,
                isNonRomanChar = instance.isNonRomanChar,
                textConverter = instance.converter
            )
        }
    }
}