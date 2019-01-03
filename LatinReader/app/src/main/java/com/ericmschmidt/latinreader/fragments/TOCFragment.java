package com.ericmschmidt.latinreader.fragments;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ericmschmidt.classicsreader.R;
import com.ericmschmidt.classicsreader.databinding.FragmentTocBinding;
import com.ericmschmidt.latinreader.MyApplication;
import com.ericmschmidt.latinreader.datamodel.Library;
import com.ericmschmidt.latinreader.datamodel.Manifest;
import com.ericmschmidt.latinreader.datamodel.WorkInfo;

/**
 * Fragment that presents work's table of contents.
 */
public class TOCFragment extends Fragment {

    public static final String ID = "work_id";


    private String workId;
    private WorkInfo work;
    private FragmentTocBinding binding;
    /**
     * Required empty constructor.
     */
    public TOCFragment() {}

    /**
     * Creates a new TOCFragment instance for a specified work
     * @param workId the work to show the TOC
     * @return TOCFragment
     */
    public static TOCFragment newInstance(String workId) {
        TOCFragment fragment = new TOCFragment();
        Bundle args = new Bundle();
        args.putString(ID, workId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            workId = getArguments().getString(ID);

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

    }
}
