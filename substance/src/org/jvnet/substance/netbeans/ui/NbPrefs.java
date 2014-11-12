/*
 * NbPrefs.java
 *
 * Created on September 26, 2006, 10:04 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jvnet.substance.netbeans.ui;

import java.io.IOException;
import org.openide.ErrorManager;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.Repository;

/**
 * Quick'n'dirty key/value storage;  pending an implementation of Preferences
 * in NB.
 * 
 * @author Tim Boudreau
 */
public class NbPrefs {
    public void put (String key, String value) {
        try {
            getStore().setAttribute(key, value);
        } catch (IOException ex) {
            ErrorManager.getDefault().notify(ex);
        }
    }
    
    public String get (String key, String defaultValue) {
        String result = (String) getStore().getAttribute(key);
        return result == null ? defaultValue : result;
    }
    
    private static FileObject store;
    private static final String FILE_NAME = "SubstanceLFPrefs";
    private static FileObject getStore() {
        if (store == null) {
            FileObject ob = Repository.getDefault().getDefaultFileSystem().getRoot();
            store = ob.getFileObject("SubstanceLFPrefs");
            if (store == null) {
                try {
                    store = ob.createData(FILE_NAME);
                } catch (IOException ex) {
                    //if this happens, whole app is broken - don't try to
                    //be graceful.  Should never occur.
                    throw new IllegalStateException (ex);
                }
            }
        }
        return store;
    }
}
