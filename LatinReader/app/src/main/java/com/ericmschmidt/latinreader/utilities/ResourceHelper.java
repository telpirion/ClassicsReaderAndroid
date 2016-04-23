package com.ericmschmidt.latinreader.utilities;
import android.content.res.Resources;
import android.content.Context;

import com.ericmschmidt.latinreader.MyApplication;

import java.io.InputStream;

/**
 * A static class to help parse XML resources.
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
