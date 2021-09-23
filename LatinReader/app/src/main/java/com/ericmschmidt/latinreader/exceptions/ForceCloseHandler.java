package com.ericmschmidt.latinreader.exceptions;

import android.app.Activity;
import android.os.Build;
import android.content.Intent;

import com.ericmschmidt.latinreader.activities.ErrorActivity;
import com.ericmschmidt.latinreader.MyApplication;

import java.io.PrintWriter;
import java.io.StringWriter;

/** Base exception for uncaught exceptions in this app.
 * @author Eric Schmidt
 * @author http://telpirion.com
 * @version 1.5
 * @since 1.1
 */
public class ForceCloseHandler implements Thread.UncaughtExceptionHandler {

    private final String LINE_SEPARATOR = "\n";
    private final Activity _context;

    public ForceCloseHandler(Activity context) {
        this._context = context;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        StringWriter stackTrace = new StringWriter();
        ex.printStackTrace(new PrintWriter(stackTrace));
        StringBuilder errorReport = new StringBuilder();

        errorReport.append("\n************ DEVICE INFORMATION ***********\n");
        errorReport.append("Brand: ");
        errorReport.append(Build.BRAND);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Device: ");
        errorReport.append(Build.DEVICE);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Model: ");
        errorReport.append(Build.MODEL);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Id: ");
        errorReport.append(Build.ID);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Product: ");
        errorReport.append(Build.PRODUCT);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("\n************ FIRMWARE ************\n");
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Release: ");
        errorReport.append(Build.VERSION.RELEASE);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Incremental: ");
        errorReport.append(Build.VERSION.INCREMENTAL);
        errorReport.append(LINE_SEPARATOR);

        errorReport.append("************ CAUSE OF ERROR ************\n\n");
        errorReport.append(stackTrace.toString());

        // Alert app logging system.
        MyApplication.logError(errorReport.toString());

        Intent intent = new Intent(_context, ErrorActivity.class);
        intent.putExtra(ErrorActivity.ERROR_KEY, errorReport.toString());
        _context.startActivity(intent);

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }
}
