package com.ericmschmidt.latinreader.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ericmschmidt.classicsreader.R
import com.ericmschmidt.latinreader.MyApplication
import com.mukesh.MarkdownView


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

    var markdownView = view.findViewById<MarkdownView>(R.id.info_markdown_view)

    // Get the app name, version name, and version number info
    var appName = MyApplication.getContext().getString(R.string.app_name)
    var developerSite = MyApplication.getContext().getString(R.string.developer_site)
    var appDescription = MyApplication.getContext().getString(R.string.app_description)

    var packageManager = MyApplication.getContext().packageManager
    var packageName = MyApplication.getContext().packageName;
    var versionInfo = packageManager.getPackageInfo(packageName, 0)
    var versionName = versionInfo.versionName

    // longVersionCode is an Android Pie feature
    var versionNumber = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
      versionInfo.longVersionCode
    } else {
      versionInfo.versionCode
    }

    // Build the Markdown-formatted information screen
    var stringBuilder = StringBuilder()
    stringBuilder.append(String.format("# About %s\n\n", appName))
    stringBuilder.append(appDescription)
    stringBuilder.append("\n\n## App details\n")
    stringBuilder.append(String.format("+ **Version name**: %s\n", versionName))
    stringBuilder.append(String.format("+ **Version code**: %d\n", versionNumber))

    // TODO(telpirion): Ensure website is ready for customer feedback
    //stringBuilder.append(String.format("+ **Developer website**: %s\n", developerSite))
    
    markdownView.setMarkDownText(stringBuilder.toString())
  }
}