package com.ericmschmidt.classicsreader.ui.fragments

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.ericmschmidt.classicsreader.MyApplication
import com.ericmschmidt.classicsreader.views.R
import com.ericmschmidt.classicsreader.activities.MainActivity
import com.ericmschmidt.classicsreader.data.Library
import com.ericmschmidt.classicsreader.data.Manifest
import com.ericmschmidt.classicsreader.data.WorkInfo
import com.ericmschmidt.classicsreader.logError
import com.ericmschmidt.classicsreader.ui.interop.setContentToLazyGrid
import com.ericmschmidt.classicsreader.ui.interop.setContentToLazyList


/**
 * Displays works (books) in a RecyclerView.
 *
 * This class is used for presenting both foreign language and English texts.
 *
 * Layout files:
 * - res/layout/fragment_library.xml
 *
 * @author Eric Schmidt
 * @author <a href="https://telpirion.com">...</a>
 * @version 2.0
 * @since 1.0
 */
class LibraryFragment : Fragment() {

    private var isTranslation = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use safeArgs.
        arguments?.let {
            val args = LibraryFragmentArgs.fromBundle(it)
            isTranslation = args.isTranslations
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_library, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            // Retrieve the manifest from the package using config settings.
            val manifest: Manifest = MyApplication.applicationInstance().manifest

            val library = Library(manifest.getCollection() as ArrayList<WorkInfo>)
            Log.i(TAG, "library length = " + library.collection?.size)

            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
            val displayType = sharedPreferences.getString(
                SettingsFragment.DISPLAY_TYPE,
                SettingsFragment.DISPLAY_TYPE_DEFAULT
            )

            Log.i(TAG, "displayType = $displayType")

            val composeView = view.findViewById<ComposeView>(R.id.compose_view)
            if (displayType == "Grid") {
                setContentToLazyGrid(
                    composeView, library,
                    isTranslation, activity as MainActivity
                )
            } else {
                setContentToLazyList(
                    composeView, library,
                    isTranslation, activity as MainActivity
                )
            }

        } catch (ex: Exception) {
            logError(this.javaClass, ex.message)
        }
    }

    companion object {
        private const val TAG = "LibraryFragment"
    }
}
