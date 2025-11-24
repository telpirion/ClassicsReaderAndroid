package com.ericmschmidt.classicsreader.ui.fragments;

import static com.ericmschmidt.classicsreader.ApplicationLoggingKt.logError;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.compose.ui.platform.ComposeView;
import androidx.fragment.app.Fragment;

import com.ericmschmidt.classicsreader.MyApplication;
import com.ericmschmidt.classicsreader.R;
import com.ericmschmidt.classicsreader.activities.MainActivity;
import com.ericmschmidt.classicsreader.data.Library;
import com.ericmschmidt.classicsreader.data.Manifest;
import com.ericmschmidt.classicsreader.ui.interop.ComposeViewAdapter;

/** Displays works (books) in a RecyclerView.
 *
 *  This class is used for presenting both foreign language and English texts.
 *
 *  Layout files:
 *  - res/layout/fragment_library.xml
 *
 * @author Eric Schmidt
 * @author <a href="https://telpirion.com">...</a>
 * @version 1.5
 * @since 1.0
 */
public class LibraryFragment extends Fragment {

    private static final String TAG = "LibraryFragment";

    private boolean isTranslation;

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
            Manifest manifest = MyApplication.Factory
                    .applicationInstance().getManifest();

            Library library = new Library(manifest.getCollection());
            Log.i(TAG, "library length = " + library.getWorks().length);

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String displayType = sharedPreferences.getString(SettingsFragment.DISPLAY_TYPE,
                    SettingsFragment.DISPLAY_TYPE_DEFAULT);

            Log.i(TAG, "displayType = " + displayType);

            ComposeView composeView = (ComposeView) findViewById(R.id.compose_view);
            if (displayType.equals("Grid")) {
                ComposeViewAdapter.setContentToLazyGrid(composeView, library,
                        this.isTranslation, (MainActivity) this.getActivity());
            } else {
                ComposeViewAdapter.setContentToLazyList(composeView, library,
                        this.isTranslation, (MainActivity) this.getActivity());
            }

        } catch (Exception ex) {
            logError(this.getClass(), ex.getMessage());
        }
    }

    private View findViewById(int id) {
        return this.getView().findViewById(id);
    }
}
