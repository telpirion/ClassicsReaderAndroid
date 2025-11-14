package com.ericmschmidt.classicsreader.datamodel

import com.ericmschmidt.classicsreader.MyApplication.Factory.applicationInstance
import com.ericmschmidt.classicsreader.logError
import com.ericmschmidt.classicsreader.utilities.DictionaryXMLHelper
import com.ericmschmidt.classicsreader.utilities.ITextConverter
import com.ericmschmidt.classicsreader.utilities.ResourceHelper
import java.util.Random

/** Contains the data and methods for getting dictionary entries.
 * @author Eric Schmidt
 * @author <a href="https://telpirion.com">...</a>
 * @version 1.5
 * @since 1.1
 */
class Dictionary(val converter: ITextConverter? = null) {

    val manifest: Manifest = applicationInstance().manifest
    val dictionaryInfo: WorkInfo? = manifest.getDictionaryInfo()
    var entryHeaders: ArrayList<String> = ArrayList()

    init {
       initEntries()
    }

    /**
     * Gets the number of entries in this dictionary.
     */
    fun entryCount(): Int {
        return this.entryHeaders.size
    }

    /**
     * Checks whether a specific string is an entry in the dictionary.
     */
    fun isInDictionary(searchEntry: String?): Boolean {
        return this.entryHeaders.contains(searchEntry)
    }

    /**
     * Gets a definition from the dictionary.
     */
    fun getEntry(searchEntry: String?): String? {
        var definition: String? = ""

        if (!isInDictionary(searchEntry)) {
            return definition
        }

        ResourceHelper.getResourceStream(this.dictionaryInfo?.location as Int).use { stream ->
            definition = DictionaryXMLHelper.getEntry(stream, searchEntry, converter)
        }

        return definition
    }

    /**
     * Gets a randomly selected from the dictionary.
     */
    fun getRandomEntry(): String? {
        val random = Random()
        val numberOfEntries: Int = entryCount()

        val randomNumber = random.nextInt(numberOfEntries + 1)
        val randomEntryKey: String = this.entryHeaders[randomNumber]
        return getEntry(randomEntryKey)
    }

    // Gets the number of alphabet chapters in dictionary.
    private fun initEntries() {
        try {
            val stream = ResourceHelper.getResourceStream(manifest.getDictionaryEntryResource())
            this.entryHeaders = DictionaryXMLHelper.getEntryHeaders(stream)
        } catch (ex: java.lang.Exception) {
            val errorMessage = ex.message
            logError(errorMessage)
        }
    }
}