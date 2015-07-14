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
 *     You should immediately close your copy of code, and destory the file. You are not authorised to
 *     be in possession of this code or view or modify it or use it in any capacity.
 */

package com.greplr.common.utils;

import android.location.Location;

import java.text.DecimalFormat;

/**
 * Created by championswimmer on 15/7/15.
 */
public class DecimalUtils {
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
}
