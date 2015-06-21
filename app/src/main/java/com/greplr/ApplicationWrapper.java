package com.greplr;

import android.app.Application;
import android.content.SharedPreferences;

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
        SharedPreferences sPref = getSharedPreferences("location", MODE_PRIVATE);
        currentLatitude = Double.valueOf(sPref.getString("lastLatitude", "28.6328"));
        currentLongitude = Double.valueOf(sPref.getString("lastLongitude", "77.2197"));
        super.onCreate();

    }

    @Override
    public void onTerminate() {
        SharedPreferences sPref = getSharedPreferences("location", MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString("lastLatitude", String.valueOf(currentLatitude));
        editor.putString("lastLongitude", String.valueOf(currentLongitude));
        editor.commit();
        super.onTerminate();
    }
}
