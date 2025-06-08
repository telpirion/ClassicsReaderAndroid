package com.ericmschmidt.classicsreader.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ericmschmidt.classicsreader.R
import com.ericmschmidt.classicsreader.MyApplication
import com.mukesh.MarkdownView
import java.io.BufferedReader

/**
 * Displays the help file for this app.
 */
class HelpFragment : Fragment() {

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_help, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    var markdownView = view.findViewById<MarkdownView>(R.id.help_markdown_view)

    // Open the help.md file from the resources
    var context = MyApplication.getContext()
    var resources = context.resources
    var inputStream = resources.openRawResource(R.raw.help);
    val helpString = inputStream.bufferedReader().use(BufferedReader::readText)

    markdownView.setMarkDownText(helpString)
  }
}