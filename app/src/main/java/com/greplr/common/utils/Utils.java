/*
 * Greplr : A super-aggregator. One app to rule them all.
 *     Copyright (C) 2015  Greplr Team
 *     Where Greplr Team consists of :
 *       1. Arnav Gupta
 *       2. Abhinav Sinha
 *       3. Prempal Singh
 *       4. Raghav Apoorv
 *       5. Shubham Dokania
 *       6. Yogesh Balan
 *
 *     The source code of this program is confidential and proprietary. If you are not part of the
 *     Greplr Team (one of the above 6 named individuals) you should not be viewing this code.
 *
 *     You should immediately close your copy of code, and destroy the file. You are not authorised to
 *     be in possession of this code or view or modify it or use it in any capacity.
 */

package com.greplr.common.utils;

import android.content.Context;
import android.content.res.Resources;
import android.location.Location;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;

import com.greplr.common.ui.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Calendar;

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


    public static boolean hasL() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static Location adjustLocationDecimalPrecision(Location loc) {
        DecimalFormat df = new DecimalFormat("#.####");
        loc.setLatitude(Double.parseDouble(df.format(loc.getLatitude())));
        loc.setLongitude(Double.parseDouble(df.format(loc.getLongitude())));
        return loc;
    }

    public static String adjustFlightFarePrecision(String fare) {
        DecimalFormat df = new DecimalFormat("#");
        return df.format(Double.valueOf(fare));
    }

    public static String editOrderLogoUrl(String logoUrl) {
        try {
            logoUrl = logoUrl.replace("%s", "200");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logoUrl;
    }

    public static int dp2px(Context context, float dp) {
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return Math.round(px);
    }

    public static String processWitOutcome(String jsonOutput) throws JSONException {

        JSONArray jsonArray = new JSONArray(jsonOutput);
        String intent = jsonArray.getJSONObject(0).getString("intent");
        JSONObject entities = jsonArray.getJSONObject(0).getJSONObject("entities");

        String BASE_URL = "greplr://";

        if (intent.equals("find_cabs")) {
            return BASE_URL + "travel/cab";
        } else {
            return intent;
        }

    }

}
