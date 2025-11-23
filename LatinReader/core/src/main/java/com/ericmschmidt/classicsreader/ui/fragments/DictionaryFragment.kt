package com.ericmschmidt.classicsreader.ui.fragments

import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.ericmschmidt.classicsreader.MyApplication
import com.ericmschmidt.classicsreader.R
import com.ericmschmidt.classicsreader.datamodel.Dictionary
import com.ericmschmidt.classicsreader.utilities.ITextConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Objects

/**
 * Displays the dictionary page.
 *
 * @author Eric Schmidt
 * @author <a href="https://telpirion.com">...</a>
 * @version 2.0
 * @since 1.0
 */
class DictionaryFragment : Fragment() {
    private var query: String? = null
    private var converter: ITextConverter? = null
    private var isNonRomanChar = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use safeArgs to get data out of navigation.
        arguments?.let {
            val args = DictionaryFragmentArgs.fromBundle(it)
            query = args.dictionaryQuery
        }

        val applicationInstance = MyApplication.Factory.applicationInstance()
        isNonRomanChar = applicationInstance.isNonRomanChar
        if (isNonRomanChar) {
            converter = applicationInstance.textConverter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dictionary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dictionaryProgress = view.findViewById<ProgressBar>(R.id.dictionary_progress)
        val searchQuery = view.findViewById<EditText>(R.id.search_query)

        dictionaryProgress.visibility = View.INVISIBLE

        // Convert text as user types.
        if (isNonRomanChar) {
            val watcher: TextWatcher? = converter?.getTextWatcher(searchQuery)
                ?: Objects.requireNonNull(
                    MyApplication.Factory
                        .applicationInstance()
                        .textConverter
                )
                    ?.getTextWatcher(searchQuery)
            searchQuery.addTextChangedListener(watcher)
        }

        searchQuery.setOnEditorActionListener { v, _, _ ->
            val searchQueryText = v.text.toString()
            submitSearchQuery(searchQueryText)
            false
        }

        query?.let {
            searchQuery.setText(it)
            submitSearchQuery(it)
        }
    }

    // Sends and receives a search query from the integrated dictionary.
    private fun submitSearchQuery(query: String) {
        val dictionaryProgress = view?.findViewById<ProgressBar>(R.id.dictionary_progress)
        dictionaryProgress?.visibility = View.VISIBLE

        // To improve fragment UI responsiveness, submit query
        // and load dictionary in a coroutine.
        lifecycleScope.launch {
            searchDictionary(query)
        }
    }

    private suspend fun searchDictionary(query: String) {
        val queryResults = withContext(Dispatchers.IO) {
            val transcribedQuery = if (isNonRomanChar) {
                converter?.convertTargetToSourceCharacters(query) ?: query
            } else {
                query
            }

            val dictionary = if (isNonRomanChar) {
                Dictionary(converter)
            } else {
                Dictionary()
            }

            if (dictionary.isInDictionary(transcribedQuery)) {
                dictionary.getEntry(transcribedQuery)
            } else {
                resources.getString(R.string.dictionary_query_no_results)
            }
        }

        val resultsField = view?.findViewById<TextView>(R.id.dictionary_result)
        resultsField?.text = queryResults

        val dictionaryProgress = view?.findViewById<ProgressBar>(R.id.dictionary_progress)
        dictionaryProgress?.visibility = View.INVISIBLE
    }
}
