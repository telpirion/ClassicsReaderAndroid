package com.ericmschmidt.latinreader.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ericmschmidt.latinreader.R;
import com.ericmschmidt.latinreader.datamodel.Library;
import com.ericmschmidt.latinreader.datamodel.WorkInfo;
import com.ericmschmidt.latinreader.layouts.LibraryListViewAdapter;
import com.ericmschmidt.latinreader.layouts.TranslationListViewAdapter;

public class LibraryFragment extends Fragment {

    public static final String TRANSLATION_FLAG = "translation";

    private boolean translationFlag;
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
            String translationParam= getArguments().getString(TRANSLATION_FLAG);
            translationFlag = (translationParam != null);
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

        // Set the content of the main list view.
        library = new Library();
            works = library.getWorks();

        ArrayAdapter<WorkInfo> adapter;

        if (translationFlag) {
            adapter = new TranslationListViewAdapter(getActivity(), works);
        } else {
            adapter = new LibraryListViewAdapter(getActivity(), works);
        }

        ListView listView = (ListView)getActivity().findViewById(R.id.library_listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(mMessageClickedHandler);
    }

    // Create a message handling object as an anonymous class.
    private OnItemClickListener mMessageClickedHandler = new OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            WorkInfo clickedWork = works[position];

            // Need to navigate to ReadingFragment.
            if (mListener != null && translationFlag) {
                mListener.onLibraryListViewClick(clickedWork.getId(), "true");
            } else if (mListener != null) {
                mListener.onLibraryListViewClick(clickedWork.getId(), "false");
            }
        }
    };

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

    /**
     * Allows the LibraryFragment to communicate to the Activity
     * when the user clicks an item in the ListView.
     */
    public interface OnLibraryListViewClick {
        void onLibraryListViewClick(String workId, String isTranslation);
    }
}
