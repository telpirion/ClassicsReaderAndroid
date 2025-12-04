/**
 * Provides a simple logging wrapper for the application.
 */
package com.ericmschmidt.classicsreader

import android.util.Log

/**
 * Log an error message.
 */
fun logError(message: String?) {
    logError(Exception::class.java, message)
}

/**
 * Log an error message and the type that raised it.
 */
fun logError(type: Class<*>, message: String?) {
    // TODO: add Crash analytics
    Log.e(type.name, message ?: "Unknown error")
}