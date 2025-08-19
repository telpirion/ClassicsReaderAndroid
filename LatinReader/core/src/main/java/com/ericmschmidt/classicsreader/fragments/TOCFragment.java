package com.ericmschmidt.classicsreader.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.ericmschmidt.classicsreader.R;
import com.ericmschmidt.classicsreader.databinding.FragmentTocBinding;
import com.ericmschmidt.classicsreader.MyApplication;
import com.ericmschmidt.classicsreader.datamodel.Library;
import com.ericmschmidt.classicsreader.datamodel.Manifest;
import com.ericmschmidt.classicsreader.datamodel.TOCEntry;
import com.ericmschmidt.classicsreader.datamodel.WorkInfo;
import com.ericmschmidt.classicsreader.layouts.TOCListViewAdapter;

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
    public static final String TAG = "TOCFragment";

    private String workId;
    private boolean isTranslation;
    private WorkInfo work;
    private FragmentTocBinding binding;

    /**
     * Required empty constructor.
     */
    public TOCFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Use safeArgs to extract data from bundle.
        assert getArguments() != null;
        TOCFragmentArgs args = TOCFragmentArgs.fromBundle(getArguments());
        this.workId = args.getWorkId();
        this.isTranslation = args.getIsTranslation();

        Manifest manifest = MyApplication.getManifest();
        Library library = new Library(manifest.getCollection());
        this.work = library.getWorkInfoByID(workId);
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

    // Create a message handling object as an anonymous class.
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            TOCEntry entry = work.getTocEntries()[position];

            assert getParentFragment() != null;
            NavController navController = NavHostFragment.findNavController(getParentFragment());

            TOCFragmentDirections.ActionTocDestToReadingDest action =
                    TOCFragmentDirections.actionTocDestToReadingDest(work.getId());
            action.setBook(entry.getBook());
            action.setLine(entry.getLine());
            action.setIsTranslation(isTranslation);

            navController.navigate(action);
        }
    };
}
