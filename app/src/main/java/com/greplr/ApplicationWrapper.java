package com.greplr;

import android.app.Application;

/**
 * Created by championswimmer on 17/6/15.
 */
public class ApplicationWrapper extends Application {

    public static boolean DARK_CARDS = false;

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
