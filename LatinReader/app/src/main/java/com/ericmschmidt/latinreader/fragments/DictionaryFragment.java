package com.ericmschmidt.latinreader.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ericmschmidt.classicsreader.R;
import com.ericmschmidt.latinreader.MyApplication;
import com.ericmschmidt.latinreader.datamodel.Dictionary;
import com.ericmschmidt.latinreader.utilities.ITextConverter;

public class DictionaryFragment extends Fragment {

    public static final String QUERY = "query";

    private String query;
    private Dictionary dictionary;


    public DictionaryFragment() {
        // Required empty public constructor
    }

    public static DictionaryFragment newInstance(String queryString) {
        DictionaryFragment fragment = new DictionaryFragment();
        Bundle args = new Bundle();
        args.putString(QUERY, queryString);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            query = getArguments().getString(QUERY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dictionary, container, false);
    }

    /**
     * Loads the fragment and the associated ReadingViewModel.
     * @param onSavedInstanceState Bundle
     */
    public void onActivityCreated(Bundle onSavedInstanceState) {

        super.onActivityCreated(onSavedInstanceState);

        // Instantiate the dictionary.
        dictionary = new Dictionary();

        EditText searchQuery = (EditText)getActivity().findViewById(R.id.search_query);

        // Convert text as user types.
        if (MyApplication.isNonRomanChar()) {
            TextWatcher watcher = new TextWatcher() {

                private boolean isCanceled;

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!isCanceled) {
                        isCanceled = true;
                        convertText();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    isCanceled = false;
                }
            };
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

    // Converts the characters typed into the EditText box to another orthography.
    private void convertText(){
        EditText searchQuery = (EditText)getActivity().findViewById(R.id.search_query);
        String searchString = searchQuery.getText().toString();

        ITextConverter converter = MyApplication.getTextConverter();
        String formattedString = converter.convertSourceToTargetCharacters(searchString);

        formattedString = formattedString.replace("\n", "").replace(" ", "");
        searchQuery.setText(formattedString);
        searchQuery.setSelection(formattedString.length());
    }

    // Sends and receives a search query from the integrated dictionary.
    private void submitSearchQuery(String query) {
        TextView resultsField = (TextView)getActivity().findViewById(R.id.dictionary_result);
        String queryResults;

        if(dictionary.isInDictionary(query)) {
            queryResults = dictionary.getEntry(query);
        } else {
            Resources resources = getResources();
            queryResults = resources.getString(R.string.dictionary_query_no_results);
        }
        resultsField.setText(queryResults);
    }
}
