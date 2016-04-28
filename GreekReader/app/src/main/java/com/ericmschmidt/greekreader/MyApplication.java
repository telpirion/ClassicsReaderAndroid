package com.ericmschmidt.greekreader;

import android.app.Application;
import android.content.Context;

/**
 * A subclass of the Application class to help get the app context.
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