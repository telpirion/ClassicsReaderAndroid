package com.ericmschmidt.latinreader.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
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
    private OnLibraryListViewClick mListener;

    public LibraryFragment() {
        // Required empty public constructor
    }

    public static LibraryFragment newInstance(String translationFlag) {
        LibraryFragment fragment = new LibraryFragment();
        Bundle args = new Bundle();
        args.putString(TRANSLATION_FLAG, translationFlag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            boolean translationParam = getArguments().getBoolean(TRANSLATION_FLAG);
            isTranslation = translationParam;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
        if (context instanceof OnLibraryListViewClick) {
            mListener = (OnLibraryListViewClick) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(int position) {
        WorkInfo clickedWork = works[position];

        // Need to navigate to ReadingFragment.
        if (mListener != null) {
            mListener.onLibraryListViewClick(clickedWork.getId(), isTranslation);
        }
    }

    private View findViewById(int id) {
        return this.getView().findViewById(id);
    }

    /**
     * Allows the LibraryFragment to communicate to the Activity
     * when the user clicks an item in the ListView.
     */
    public interface OnLibraryListViewClick {
        void onLibraryListViewClick(String workId, boolean isTranslation);
    }
}
