package com.greplr;

import android.app.Application;

/**
 * Created by championswimmer on 17/6/15.
 */
public class ApplicationWrapper extends Application {

    public static boolean DARK_CARDS = false;

    public static double currentLatitude = 0;
    public static double currentLongitude = 0;

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
