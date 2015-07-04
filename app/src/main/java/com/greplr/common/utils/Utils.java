package com.greplr.common.utils;

import android.content.Context;
import android.location.Location;
import android.os.Build;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

/**
 * Created by prempal on 3/7/15.
 */
public class Utils {

    public static String loadJSONFromAsset(Context context, String filename) {
        String json = null;
        try {

            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static boolean hasL(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static Location adjustLocationDecimalPrecision (Location loc) {
        DecimalFormat df = new DecimalFormat("#.####");
        loc.setLatitude(Double.parseDouble(df.format(loc.getLatitude())));
        loc.setLongitude(Double.parseDouble(df.format(loc.getLongitude())));
        return loc;
    }

}
