package com.ericmschmidt.classicsreader.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ericmschmidt.classicsreader.R
import com.ericmschmidt.classicsreader.views.databinding.ActivityErrorBinding

/**
 * Error message activity for this app.
 * @author Eric Schmidt
 * @author <a href="https://telpirion.com">...</a>
 * @version 1.5
 * @since 1.1
 */
class ErrorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityErrorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityErrorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val errorMessage = savedInstanceState?.getString(ERROR_KEY)
            ?: intent.extras?.getString(ERROR_KEY)
            ?: getString(R.string.greekreader_default_error_message)

        binding.errorActivityContent.text = errorMessage
    }

    companion object {
        const val ERROR_KEY = "com.ericmschmidt.latinreader.ERROR"
    }
}
