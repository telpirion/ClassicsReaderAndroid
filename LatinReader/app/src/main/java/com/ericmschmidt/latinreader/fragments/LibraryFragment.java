package com.ericmschmidt.latinreader.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ericmschmidt.classicsreader.R;
import com.ericmschmidt.latinreader.MyApplication;
import com.ericmschmidt.latinreader.datamodel.Library;
import com.ericmschmidt.latinreader.datamodel.Manifest;
import com.ericmschmidt.latinreader.datamodel.WorkInfo;
import com.ericmschmidt.latinreader.layouts.LibraryRecyclerViewAdapter;

/** Displays works (books) in a RecyclerView.
 *
 *  This class is used for presenting both foreign language and English texts.
 *
 *  Layout files:
 *  - res/layout/fragment_library.xml
 *  - res/layout/cardviewitem_libraryrecyclerview.xml
 *
 * @author Eric Schmidt
 * @author http://telpirion.com
 * @version 1.5
 * @since 1.0
 */
public class LibraryFragment extends Fragment
        implements LibraryRecyclerViewAdapter.Listener {

    public static final String TRANSLATION_FLAG = "isTranslations";
    private static final String TAG = "LibraryFragment";

    private boolean isTranslation;
    private Library library;
    private WorkInfo[] works;

    /**
     * Required empty public constructor
     */
    public LibraryFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isTranslation = getArguments().getBoolean(TRANSLATION_FLAG);
        }
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
            library = new Library(manifest.getCollection());
            works = library.getWorks();

            // Create and populate the RecyclerView.
            RecyclerView recyclerView =
                    (RecyclerView) findViewById(R.id.recyclerview);
            recyclerView.setHasFixedSize(true);

            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(manager);

            LibraryRecyclerViewAdapter adapter =
                    new LibraryRecyclerViewAdapter(works, this.isTranslation);

            adapter.setListener(this);
            recyclerView.setAdapter(adapter);

        } catch (Exception ex) {
            MyApplication.logError(this.getClass(), ex.getMessage());
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onLibraryRecyclerViewClick(int position) {
        WorkInfo clickedWork = works[position];

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
