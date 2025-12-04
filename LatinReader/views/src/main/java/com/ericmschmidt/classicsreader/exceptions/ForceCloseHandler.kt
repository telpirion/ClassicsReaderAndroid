package com.ericmschmidt.classicsreader.exceptions

import android.app.Activity
import android.content.Intent
import android.os.Build
import com.ericmschmidt.classicsreader.logError
import com.ericmschmidt.classicsreader.activities.ErrorActivity
import java.io.PrintWriter
import java.io.StringWriter

/**
 * Base exception for uncaught exceptions in this app.
 * @author Eric Schmidt
 * @author http://telpirion.com
 * @version 1.5
 * @since 1.1
 */
class ForceCloseHandler(private val context: Activity) : Thread.UncaughtExceptionHandler {

    override fun uncaughtException(thread: Thread, ex: Throwable) {
        val stackTrace = StringWriter()
        ex.printStackTrace(PrintWriter(stackTrace))
        val errorReport = StringBuilder()

        errorReport.append("\n************ DEVICE INFORMATION ***********\n")
        errorReport.append("Brand: ")
        errorReport.append(Build.BRAND)
        errorReport.append(System.lineSeparator())
        errorReport.append("Device: ")
        errorReport.append(Build.DEVICE)
        errorReport.append(System.lineSeparator())
        errorReport.append("Model: ")
        errorReport.append(Build.MODEL)
        errorReport.append(System.lineSeparator())
        errorReport.append("Id: ")
        errorReport.append(Build.ID)
        errorReport.append(System.lineSeparator())
        errorReport.append("Product: ")
        errorReport.append(Build.PRODUCT)
        errorReport.append(System.lineSeparator())
        errorReport.append("\n************ FIRMWARE ************\n")
        errorReport.append(System.lineSeparator())
        errorReport.append("Release: ")
        errorReport.append(Build.VERSION.RELEASE)
        errorReport.append(System.lineSeparator())
        errorReport.append("Incremental: ")
        errorReport.append(Build.VERSION.INCREMENTAL)
        errorReport.append(System.lineSeparator())

        errorReport.append("************ CAUSE OF ERROR ************\n\n")
        errorReport.append(stackTrace.toString())

        // Alert app logging system.
        logError(errorReport.toString())

        val intent = Intent(context, ErrorActivity::class.java)
        intent.putExtra(ErrorActivity.ERROR_KEY, errorReport.toString())
        context.startActivity(intent)

        android.os.Process.killProcess(android.os.Process.myPid())
        System.exit(10)
    }
}
