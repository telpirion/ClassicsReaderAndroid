package com.ericmschmidt.latinreader.activities;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.ericmschmidt.classicsreader.R;

/** Error message activity for this app.
 * @author Eric Schmidt
 * @author http://telpirion.com
 * @version 1.5
 * @since 1.1
 */
public class ErrorActivity extends AppCompatActivity {

    public final static String ERROR_KEY = "com.ericmschmidt.latinreader.ERROR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_error);
        TextView errorMessageText = findViewById(R.id.error_activity_content);
        String errorMessage = getString(R.string.default_error_message);

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

