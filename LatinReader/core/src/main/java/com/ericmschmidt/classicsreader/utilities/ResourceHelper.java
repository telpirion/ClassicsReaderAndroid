package com.ericmschmidt.classicsreader.utilities;

import android.content.res.Resources;
import android.content.Context;

import com.ericmschmidt.classicsreader.MyApplication;

import java.io.InputStream;

/** A static class to help parse XML resources.
 *
 * @author Eric Schmidt
 * @author http://telpirion.com
 * @version 1.5
 * @since 1.0
 */
public class ResourceHelper {

    /**
     * Gets an input stream from the given internal resource.
     * @param resourceID the ID of the resource to get.
     * @return an InputStream from the specified resource.
     */
    public static InputStream getResourceStream(int resourceID) {
        Context context = MyApplication.getContext();
        Resources resources = context.getResources();
        InputStream inputStream = resources.openRawResource(resourceID);

        return inputStream;
    }
}
