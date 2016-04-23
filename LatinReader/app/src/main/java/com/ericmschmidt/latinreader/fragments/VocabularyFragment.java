package com.ericmschmidt.latinreader.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ericmschmidt.latinreader.R;
import com.ericmschmidt.latinreader.datamodel.Dictionary;

public class VocabularyFragment extends Fragment {

    public VocabularyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vocabulary, container, false);
    }

    public void onActivityCreated(Bundle onSavedInstanceState) {

        super.onActivityCreated(onSavedInstanceState);

        // Get a random entry from the dictionary to sow.
        Dictionary dictionary = new Dictionary();
        String vocabEntry = dictionary.getRandomEntry();

        TextView vocabTextView = (TextView)getActivity().findViewById(R.id.vocab_result);
        vocabTextView.setText(vocabEntry);

    }
}
