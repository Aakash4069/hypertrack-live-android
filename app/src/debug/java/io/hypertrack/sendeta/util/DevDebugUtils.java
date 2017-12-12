package io.hypertrack.sendeta.util;


import android.app.Application;

import com.facebook.stetho.Stetho;
import com.hypertrack.lib.HyperTrack;



/**
 * Created by piyush on 03/07/16.
 */
public class DevDebugUtils {

    private static final String TAG = DevDebugUtils.class.getSimpleName();

    public static void installStetho(Application application) {
        Stetho.initializeWithDefaults(application);
    }

    public static void setHyperLogLevel(int logLevel) {
        HyperTrack.enableDebugLogging(logLevel);
    }

    public static void sdkVersionMessage() {
        HyperLog.i(TAG, "HyperTrack Live: SDK Version " + HyperTrack.getSDKVersion());
    }
}
