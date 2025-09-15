package com.ericmschmidt.classicsreader.ui.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ericmschmidt.classicsreader.R
import com.mukesh.MarkdownView
import java.io.BufferedReader

/**
 * Displays the app info, like version number, version name, and feedback link
 */
class InfoFragment : Fragment() {

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View? {
    return inflater.inflate(R.layout.fragment_info, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val markdownView = view.findViewById<MarkdownView>(R.id.info_markdown_view)
    val infoString = buildInfoString(requireContext())
    markdownView.setMarkDownText(infoString)
  }

  companion object InfoStringBuilder {
      fun buildInfoString(context: Context): String {

        // Get the app context
        val resources = context.resources
        val packageManager = context.packageManager
        val packageName = context.packageName

        // Open the info.md file from the resources
        val inputStream = resources.openRawResource(R.raw.info)
        var infoString = inputStream.bufferedReader().use(BufferedReader::readText)

        // Get the app name, version name, and version number info
        val appName = context.getString(R.string.app_name)
        val appDescription = context.getString(R.string.app_description)
        val versionInfo = packageManager.getPackageInfo(packageName, 0)
        val versionName = versionInfo.versionName

        // longVersionCode is an Android Pie feature
        val versionNumber = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
          versionInfo.longVersionCode
        } else {
          @Suppress("DEPRECATION")
          versionInfo.versionCode
        }

        // Build the Markdown-formatted information screen
        // It would be SO NICE to have a templating engine for this
        infoString = infoString.replace("{{appName}}", appName)
        infoString = infoString.replace("{{appDescription}}", appDescription)
        infoString = infoString.replace("{{versionName}}", versionName.toString())
        infoString = infoString.replace("{{versionCode}}", versionNumber.toString())

        return infoString
      }
  }
}