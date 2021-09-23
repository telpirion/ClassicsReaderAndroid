package com.ericmschmidt.latinreader.datamodel;

import com.ericmschmidt.latinreader.MyApplication;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

/** Stores information about the works (source works, translations) in this app.
 * @author Eric Schmidt
 * @author http://telpirion.com
 * @version 1.5
 * @since 1.1
 */
public class Manifest {

    public Manifest() {}
    public ArrayList<WorkInfo> getCollection() {
        return null;
    }
    public WorkInfo getDictionaryInfo() {
        return null;
    }
    public int getDictionaryEntryResource() {
        return 0;
    }

    /**
     * Get the manifest class out of the package using reflection.
     * @param className the fully-qualified class name as a string.
     * @return Manifest
     */
    public static Manifest getManifest(String className) {
        Manifest manifest = null;
        try {
            Class<?> manifestClass = Class.forName(className);
            Constructor<?>[] constructors = manifestClass.getConstructors();
            manifest = (Manifest) constructors[0].newInstance();
        } catch (Exception ex) {
            MyApplication.logError(Manifest.class, ex.getMessage());
        }
        return manifest;
    }
}
