package com.ericmschmidt.classicsreader.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.compose.ui.platform.ComposeView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.ericmschmidt.classicsreader.R;
import com.ericmschmidt.classicsreader.MyApplication;
import com.ericmschmidt.classicsreader.activities.MainActivity;
import com.ericmschmidt.classicsreader.datamodel.Library;
import com.ericmschmidt.classicsreader.datamodel.Manifest;
import com.ericmschmidt.classicsreader.datamodel.WorkInfo;
import com.ericmschmidt.classicsreader.ui.interop.ComposeViewAdapter;
import com.ericmschmidt.classicsreader.ui.layouts.LibraryRecyclerViewAdapter;

/** Displays works (books) in a RecyclerView.
 *
 *  This class is used for presenting both foreign language and English texts.
 *
 *  Layout files:
 *  - res/layout/fragment_library.xml
 *  - res/layout/cardviewitem_libraryrecyclerview.xml
 *
 * @author Eric Schmidt
 * @author <a href="https://telpirion.com">...</a>
 * @version 1.5
 * @since 1.0
 */
public class LibraryFragment extends Fragment
        implements LibraryRecyclerViewAdapter.Listener {

    private static final String TAG = "LibraryFragment";

    private boolean isTranslation;
    private WorkInfo[] works;

    /**
     * Required empty public constructor
     */
    public LibraryFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Use safeArgs.
        assert getArguments() != null;
        LibraryFragmentArgs args = LibraryFragmentArgs.fromBundle(getArguments());
        this.isTranslation = args.getIsTranslations();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_library, container, false);
    }

    public void onActivityCreated(Bundle onSavedInstanceState) {
        super.onActivityCreated(onSavedInstanceState);

        try {
            // Retrieve the manifest from the package using config settings.
            Manifest manifest = MyApplication.getManifest();

            Library library = new Library(manifest.getCollection());
            Log.i("LibraryFragment", "library length = " + library.getWorks().length);
            works = library.getWorks();

            ComposeView composeView = (ComposeView) findViewById(R.id.compose_view);
            ComposeViewAdapter.setContentToLazyList(composeView, library,
                    this.isTranslation, (MainActivity) this.getActivity());

        } catch (Exception ex) {
            MyApplication.logError(this.getClass(), ex.getMessage());
        }
    }

    @Override
    public void onLibraryRecyclerViewClick(int position) {
        WorkInfo clickedWork = works[position];

        // Navigate to the ReadingFragment based upon clicks on the RecyclerView.
        NavController navController = NavHostFragment.findNavController(this);
        LibraryFragmentDirections.ActionLibraryFragmentToReadingDest action =
                LibraryFragmentDirections.actionLibraryFragmentToReadingDest(clickedWork.getId());
        action.setIsTranslation(isTranslation);
        navController.navigate(action);
    }

    private View findViewById(int id) {
        return this.getView().findViewById(id);
    }
}
