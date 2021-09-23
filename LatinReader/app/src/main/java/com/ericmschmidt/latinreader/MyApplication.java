package com.ericmschmidt.latinreader;

import android.app.Application;
import android.content.Context;

import com.ericmschmidt.classicsreader.R;
import com.ericmschmidt.latinreader.datamodel.Manifest;
import com.ericmschmidt.latinreader.utilities.ITextConverter;

import java.lang.reflect.Constructor;

/** A subclass of the Application class to help get the app context.
 *
 * @author Eric Schmidt
 * @author http://telpirion.com
 * @version 1.5
 * @since 1.0
 */
public class MyApplication extends Application {

    public static MyApplication instance;

    /**
     * Creates an instance of the MyApplication class.
     */
    public MyApplication() {
        instance = this;
    }

    /**
     * Get the context of the current running app.
     * @return Context
     */
    public static Context getContext() {
        return instance;
    }

    /**
     * Get the manifest of texts for this app.
     * @return Manifest
     */
    public static Manifest getManifest() {
        String manifestName = MyApplication.getContext().getResources().getString(R.string.manifest);
        return Manifest.getManifest(manifestName);
    }

    public static boolean isNonRomanChar() {
        return getContext().getResources().getBoolean(R.bool.non_roman_char);
    }

    public static ITextConverter getTextConverter() {
        ITextConverter converter = null;

        if (isNonRomanChar()) {
            String className = getContext().getResources().getString(R.string.text_converter);

            try {
                Class<?> manifestClass = Class.forName(className);
                Constructor<?>[] constructors = manifestClass.getConstructors();
                converter = (ITextConverter) constructors[0].newInstance();
            } catch (Exception ex) {
                MyApplication.logError(Manifest.class, ex.getMessage());
            }
        }
        return converter;
    }

    /**
     * Log an error message.
     * @param message the message to log.
     */
    public static void logError(String message) {
        logError(Exception.class, message);
    }

    /**
     * Log an error message and the type that raised it.
     * @param type the type that raised the error.
     * @param message the message to write.
     */
    public static void logError(Class type, String message) {
        // Need to add Crash analytics
    }
}