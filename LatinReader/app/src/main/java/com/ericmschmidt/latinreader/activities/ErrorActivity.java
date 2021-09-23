package com.ericmschmidt.latinreader.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ericmschmidt.classicsreader.R;

public class ErrorActivity extends AppCompatActivity {

    public final static String ERROR_KEY = "com.ericmschmidt.latinreader.ERROR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_error);
        TextView errorMessageText = (TextView) findViewById(R.id.error_activity_content);
        String errorMessage = getString(R.string.default_error_message);;

        Bundle extras = getIntent().getExtras();

        if ((savedInstanceState != null) &&
                savedInstanceState.containsKey(ERROR_KEY)) {
            errorMessage = savedInstanceState.getString(ERROR_KEY);

        } else if (extras != null) {
            errorMessage = extras.getString(ERROR_KEY);

        }

        errorMessageText.setText(errorMessage);
    }
}

