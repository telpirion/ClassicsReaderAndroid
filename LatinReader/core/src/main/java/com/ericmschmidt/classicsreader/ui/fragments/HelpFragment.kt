package com.ericmschmidt.classicsreader.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ericmschmidt.classicsreader.R
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
    val markdownView = view.findViewById<MarkdownView>(R.id.help_markdown_view)
    val helpString = buildHelpString(view.context)
    markdownView.setMarkDownText(helpString)
  }

  /**
   * Builds the help string from the raw resource file.
   */
  companion object HelpFragmentBuilder {
    fun buildHelpString(context: Context): String {
      val resources = context.resources
      val inputStream = resources.openRawResource(R.raw.help);
      val helpString = inputStream.bufferedReader().use(BufferedReader::readText)
      return helpString
    }
  }
}