package com.ericmschmidt.classicsreader.fragments;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.ericmschmidt.classicsreader.R;
import com.ericmschmidt.classicsreader.MyApplication;
import com.ericmschmidt.classicsreader.datamodel.Dictionary;
import com.ericmschmidt.classicsreader.utilities.ITextConverter;

/** Displays the dictionary page.
 *
 * @author Eric Schmidt
 * @author http://telpirion.com
 * @version 1.5
 * @since 1.0
 */
public class DictionaryFragment extends Fragment {
    private String query;
    private ITextConverter converter;

    /**
     * Required empty public constructor
     */
    public DictionaryFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Use safeArgs to get data out of navigation.
        assert getArguments() != null;
        DictionaryFragmentArgs args = DictionaryFragmentArgs.fromBundle(getArguments());
        this.query = args.getDictionaryQuery();

        if (MyApplication.isNonRomanChar()) {
            converter = MyApplication.getTextConverter();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dictionary, container, false);
    }

    /**
     * Loads the fragment and the associated ReadingViewModel.
     * @param onSavedInstanceState Bundle
     */
    public void onActivityCreated(Bundle onSavedInstanceState) {

        super.onActivityCreated(onSavedInstanceState);

        // Hide progress bar.
        getActivity().findViewById(R.id.dictionary_progress).setVisibility(View.INVISIBLE);

        EditText searchQuery = (EditText)getActivity().findViewById(R.id.search_query);

        // Convert text as user types.
        if (MyApplication.isNonRomanChar()) {
            TextWatcher watcher = converter != null ?
                    converter.getTextWatcher(searchQuery) :
                    MyApplication.getTextConverter().getTextWatcher(searchQuery);
            searchQuery.addTextChangedListener(watcher);
        }

        searchQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                String searchQueryText = v.getText().toString();
                submitSearchQuery(searchQueryText);
                return false;
            }
        });

        if (query != null) {
            searchQuery.setText(query);
            submitSearchQuery(query);
        }
    }

    // Sends and receives a search query from the integrated dictionary.
    private void submitSearchQuery(String query) {

        // Show the indefinite progress indicator.
        ProgressBar progress = (ProgressBar)getActivity().findViewById(R.id.dictionary_progress);
        progress.setVisibility(View.VISIBLE);

        // To improve fragment UI responsiveness, submit query
        // and load dictionary in an AsyncTask.
        new SearchDictionaryTask().execute(query);
    }

    // Create AsyncTask for fetching and displaying dictionary search result.
    private class SearchDictionaryTask extends AsyncTask<String, Integer, Long> {

        protected String queryResults = "";
        private Dictionary dictionary;

        protected Long doInBackground(String... query) {

            String transcribedQuery = query[0];

            if (MyApplication.isNonRomanChar()) {
                transcribedQuery = converter.convertTargetToSourceCharacters(transcribedQuery);
                dictionary = new Dictionary(converter);
            } else {
                dictionary = new Dictionary();
            }

            if(dictionary.isInDictionary(transcribedQuery)) {
                queryResults = dictionary.getEntry(transcribedQuery);
            } else {
                Resources resources = getResources();
                queryResults = resources.getString(R.string.dictionary_query_no_results);
            }

            return  new Long(1);
        }

        protected void onPostExecute(Long result){
            TextView resultsField = (TextView)getActivity().findViewById(R.id.dictionary_result);
            resultsField.setText(queryResults);

            ProgressBar progress = (ProgressBar)getActivity().findViewById(R.id.dictionary_progress);
            progress.setVisibility(View.INVISIBLE);
        }
    }
}
