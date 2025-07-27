package org.vosk.android;

import android.content.Context;
import org.vosk.Model;

public class StorageService {
    public static void unpack(Context context, String modelName, String dirName, 
                            ModelCallback onSuccess, ErrorCallback onError) {
        // Stub implementation - call success callback
        try {
            Model model = new Model(""); // Stub model
            onSuccess.onModelReady(model);
        } catch (Exception e) {
            onError.onError(e);
        }
    }
    
    public interface ModelCallback {
        void onModelReady(Model model);
    }
    
    public interface ErrorCallback {
        void onError(Exception e);
    }
}