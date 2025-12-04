package com.ericmschmidt.classicsreader.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.ericmschmidt.classicsreader.MyApplication
import com.ericmschmidt.classicsreader.views.R
import com.ericmschmidt.classicsreader.views.databinding.FragmentTocBinding
import com.ericmschmidt.classicsreader.datamodel.Library
import com.ericmschmidt.classicsreader.datamodel.TOCEntry
import com.ericmschmidt.classicsreader.datamodel.WorkInfo
import com.ericmschmidt.classicsreader.ui.layouts.TOCListViewAdapter

/**
 * Displays a work's table of contents.
 * <br/>
 * Source files:
 * - res/layout/fragment_toc.xml
 * <br/>
 * @author Eric Schmidt
 * @author <a href="https://telpirion.com">...</a>
 * @version 2.0
 * @since 1.4
 */
class TOCFragment : Fragment() {

    private var work: WorkInfo? = null
    private lateinit var binding: FragmentTocBinding
    private val args: TOCFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val manifest = MyApplication.Factory.applicationInstance().manifest
        val collection = manifest.getCollection()
        if (collection != null) {
            val library = Library(collection)
            work = library.getWorkInfoByID(args.workId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTocBinding.inflate(inflater, container, false)
        binding.work = work
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter: ArrayAdapter<TOCEntry> = TOCListViewAdapter(
            requireActivity(),
            work?.tocEntries?.toTypedArray() ?: arrayOf()
        )

        val listView = view.findViewById<ListView>(R.id.toc_listView)
        listView.adapter = adapter
        listView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val entry = work?.tocEntries?.get(position)
                val navController = NavHostFragment.findNavController(this)
                if (entry != null) {
                    work?.id?.let { workId ->
                        val action =
                            TOCFragmentDirections.actionTocDestToReadingDest(
                                workId = workId,
                                book = entry.book,
                                line = entry.line,
                                isTranslation = args.isTranslation
                            )
                        navController.navigate(action)
                    }
                }
            }
    }

    companion object {
        const val TAG = "TOCFragment"
    }
}
