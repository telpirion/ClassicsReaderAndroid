package com.ericmschmidt.classicsreader.data

import com.ericmschmidt.classicsreader.MyApplication.Factory.applicationInstance
import com.ericmschmidt.classicsreader.logError
import com.ericmschmidt.classicsreader.utilities.ITextConverter
import com.ericmschmidt.classicsreader.utilities.getEntry
import com.ericmschmidt.classicsreader.utilities.getEntryHeaders
import com.ericmschmidt.classicsreader.utilities.getResourceStream
import java.util.Random

/** Contains the data and methods for getting dictionary entries.
 * @author Eric Schmidt
 * @author <a href="https://telpirion.com">...</a>
 * @version 2.0
 * @since 1.1
 */

class Dictionary(val converter: ITextConverter? = null) {

    val library: Library = applicationInstance().library
    val dictionaryInfo: WorkInfo? = library.getDictionaryInfo()
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

        // See if the user is searching for a partial match
        if (searchEntry?.get(searchEntry.length - 1) == '-') {
            return getPartialMatches(searchEntry)
        }

        if (!isInDictionary(searchEntry)) {
            return definition
        }

        getResourceStream(this.dictionaryInfo?.location as Int).use { stream ->
            definition = getEntry(
                stream,
                searchEntry as String,
                converter
            )
        }

        return definition
    }

    private fun getPartialMatches(searchEntry: String): String {
        val entries = searchForPartialMatch(searchEntry)
        val fullEntries = StringBuilder()

        entries.forEachIndexed { i, e ->
            fullEntries.append((i + 1).toString(), ".")
            fullEntries.append(getEntry(e), "\n\n")
        }
        return fullEntries.toString()
    }

    /**
     * Gets a randomly selected from the dictionary.
     */
    fun getRandomEntry(): String? {
        val random = Random()
        val numberOfEntries: Int = entryCount()

        val randomNumber = random.nextInt(numberOfEntries)
        val randomEntryKey: String = this.entryHeaders[randomNumber]
        return getEntry(randomEntryKey)
    }

    /**
     * Gets a list of partial matches.
     */
    fun searchForPartialMatch(entry: String): ArrayList<String> {
        return searchForPartials(entry.replace("-", ""), entryCount(), 0)
    }

    private fun searchForPartials(searchTerm: String, top: Int, bottom: Int): ArrayList<String> {
        val mid = (top + bottom) / 2
        val entryKey = this.entryHeaders[mid]

        // Nothing found
        if ((top - 1) <= bottom) {
            return ArrayList()
        }

        if (entryKey.contains(searchTerm)) {
            return getNeighbors(searchTerm, mid)
        } else if (searchTerm < entryKey) {
            return searchForPartials(searchTerm, mid, bottom)
        } else if (searchTerm > entryKey) {
            return searchForPartials(searchTerm, top, mid)
        }
        return ArrayList()
    }

    private fun getNeighbors(searchTerm: String, mid: Int): ArrayList<String> {
        val allEntries = mutableSetOf<String>()
        var currEntry = entryHeaders[mid]
        var index = mid

        // Go up the list of entries finding all possible matches
        while (currEntry.contains(searchTerm)) {
            allEntries.add(currEntry)
            index++
            if (index >= entryHeaders.size){
                break
            }
            currEntry = entryHeaders[index]
        }

        // Reset the index and go down the list of entries.
        index = mid
        currEntry = entryHeaders[index]
        while (currEntry.contains(searchTerm)) {
            allEntries.add(currEntry)
            index--
            if (index <= 0) {
                break
            }
            currEntry = entryHeaders[index]
        }
        return allEntries.toList() as ArrayList<String>
    }

    // Gets the number of alphabet chapters in dictionary.
    private fun initEntries() {
        try {
            val stream = getResourceStream(library.getDictionaryEntryResource())
            this.entryHeaders = getEntryHeaders(stream)
        } catch (ex: java.lang.Exception) {
            val errorMessage = ex.message
            logError(errorMessage)
        }
    }
}