package com.ericmschmidt.latinreader.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.fragment.app.Fragment;

import com.ericmschmidt.classicsreader.R;
import com.ericmschmidt.classicsreader.databinding.FragmentTocBinding;
import com.ericmschmidt.latinreader.MyApplication;
import com.ericmschmidt.latinreader.datamodel.Library;
import com.ericmschmidt.latinreader.datamodel.Manifest;
import com.ericmschmidt.latinreader.datamodel.TOCEntry;
import com.ericmschmidt.latinreader.datamodel.WorkInfo;
import com.ericmschmidt.latinreader.layouts.TOCListViewAdapter;

/** Displays a work's table of contents.
 *
 *  Source files:
 *  - res/layout/fragment_toc.xml
 *
 * @author Eric Schmidt
 * @author http://telpirion.com
 * @version 1.5
 * @since 1.4
 */
public class TOCFragment extends Fragment {

    public static final String WORK_ID_KEY = "workId";
    public static final String IS_TRANSLATION_KEY = "isTranslation";

    public static final String TAG = "TOCFragment";

    private String workId;
    private boolean isTranslation;
    private WorkInfo work;
    private FragmentTocBinding binding;
    private OnTOCListViewClick mListener;

    /**
     * Required empty constructor.
     */
    public TOCFragment() {}

    /**
     * Creates a new TOCFragment instance for a specified work
     * @param opts the data from the reading view
     * @return TOCFragment
     */
    public static TOCFragment newInstance(TOCViewOptions opts) {
        TOCFragment fragment = new TOCFragment();
        Bundle args = new Bundle();
        args.putString(WORK_ID_KEY, opts.workId);
        args.putBoolean(IS_TRANSLATION_KEY, opts.isTranslation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.workId = getArguments().getString(WORK_ID_KEY);
            this.isTranslation = getArguments().getBoolean(IS_TRANSLATION_KEY);

            Manifest manifest = MyApplication.getManifest();
            Library library = new Library(manifest.getCollection());
            this.work = library.getWorkInfoByID(workId);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.binding = FragmentTocBinding.inflate(inflater,container, false);
        this.binding.setWork(this.work);
        return this.binding.getRoot();
    }

    /**
     * Loads the fragment and the associated ReadingViewModel.
     * @param onSavedInstanceState Bundle
     */
    public void onActivityCreated(Bundle onSavedInstanceState) {
        super.onActivityCreated(onSavedInstanceState);

        ArrayAdapter<TOCEntry> adapter = new TOCListViewAdapter(getActivity(),
                        work.getTocEntries());

        ListView listView = (ListView)this.getView().findViewById(R.id.toc_listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClickListener);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TOCFragment.OnTOCListViewClick) {
            mListener = (TOCFragment.OnTOCListViewClick) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    // Create a message handling object as an anonymous class.
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            TOCEntry entry = work.getTocEntries()[position];
            if (mListener != null) {
                ReadingFragment.ReadingViewOptions options =
                        new ReadingFragment.ReadingViewOptions();
                options.book = entry.getBook();
                options.line = entry.getLine();
                options.workId = work.getId();
                options.isTranslation = isTranslation;
                mListener.onTOCListViewClick(options);
            }
        }
    };

    /**
     * Allows the LibraryFragment to communicate to the Activity
     * when the user clicks an item in the ListView.
     */
    public interface OnTOCListViewClick {
        void onTOCListViewClick(ReadingFragment.ReadingViewOptions options);
    }

    /**
     * Struct for passing information to the TOC fragment.
     */
    public static class TOCViewOptions {
        public TOCViewOptions() {}
        public String workId;
        public boolean isTranslation;
    }
}
