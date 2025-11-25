package com.ericmschmidt.classicsreader.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.ericmschmidt.classicsreader.MyApplication
import com.ericmschmidt.classicsreader.databinding.FragmentVocabularyBinding
import com.ericmschmidt.classicsreader.datamodel.Dictionary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Displays the vocabulary word-builder page.
 * <br/>
 * Source files:
 * - res/layout/fragment_vocabulary.xml
 * <br/>
 * @author Eric Schmidt
 * @author <a href="https://telpirion.com">...</a>
 * @version 2.0
 * @since 1.0
 */
class VocabularyFragment : Fragment() {

    private lateinit var binding: FragmentVocabularyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVocabularyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the random entry from the dictionary.
        lifecycleScope.launch {
            val vocabEntry = withContext(Dispatchers.IO) {
                val applicationInstance = MyApplication.Factory.applicationInstance()
                val converter = if (applicationInstance.isNonRomanChar) {
                    applicationInstance.textConverter
                } else {
                    null
                }
                val dictionary = Dictionary(converter)
                dictionary.getRandomEntry()
            }

            binding.vocabResult.text = vocabEntry
            binding.vocabProgress.visibility = View.INVISIBLE
        }
    }
}
