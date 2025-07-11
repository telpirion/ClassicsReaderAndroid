package com.ericmschmidt.classicsreader.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.ericmschmidt.classicsreader.R;
import com.ericmschmidt.classicsreader.MyApplication;
import com.ericmschmidt.classicsreader.datamodel.Dictionary;
import com.ericmschmidt.classicsreader.utilities.ITextConverter;

/** Displays the vocabulary word-builder page.
 *
 *  Source files:
 *  - res/layout/fragment_vocabulary.xml
 *
 * @author Eric Schmidt
 * @author http://telpirion.com
 * @version 1.5
 * @since 1.0
 */
public class VocabularyFragment extends Fragment {

    /**
     * Required empty public constructor
     */
    public VocabularyFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vocabulary, container, false);
    }

    public void onActivityCreated(Bundle onSavedInstanceState) {

        super.onActivityCreated(onSavedInstanceState);

        // Get the random entry from the dictionary.
        new VocabDictionaryTask().execute();
    }

    private class VocabDictionaryTask extends AsyncTask<String, Integer, Long>{
        protected String vocabEntry = "";

        protected Long doInBackground(String... query) {

            ITextConverter converter = MyApplication.isNonRomanChar() ?
                    MyApplication.getTextConverter() : null;

            // Get a random entry from the dictionary to sow.
            Dictionary dictionary = new Dictionary(converter);
            vocabEntry = dictionary.getRandomEntry();

            return new Long(1);
        }

        protected void onPostExecute(Long result){
            TextView vocabTextView = (TextView)getActivity().findViewById(R.id.vocab_result);
            vocabTextView.setText(vocabEntry);

            ProgressBar progress = (ProgressBar)getActivity().findViewById(R.id.vocab_progress);
            progress.setVisibility(View.INVISIBLE);
        }
    }
}
