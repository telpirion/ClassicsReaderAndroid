package com.telpirion.classicsreader.core_data.utilities

/**
 * Interface for a converter that changes one orthography for another.
 *
 * @author Eric Schmidt
 * @author http://telpirion.com
 * @version 1.5
 * @since 1.1
 */
interface ITextConverter {
    fun convertSourceToTargetCharacters(source: String): String
    fun convertTargetToSourceCharacters(target: String): String
    val lang: String
}
