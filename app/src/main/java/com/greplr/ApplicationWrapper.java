package com.greplr;

import android.app.Application;

/**
 * Created by championswimmer on 17/6/15.
 */
public class ApplicationWrapper extends Application {

    public static boolean DARK_CARDS = false;

    public static double currentLatitude = 28.6328;
    public static double currentLongitude = 77.2197;

    public static boolean locationInitialised = false;

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
