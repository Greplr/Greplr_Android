package com.greplr.common.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by prempal on 3/7/15.
 */
public class Utils {

    public static String loadJSONFromAsset(Context context) {
        String json = null;
        try {

            InputStream is = context.getAssets().open("cities.json");
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

}
