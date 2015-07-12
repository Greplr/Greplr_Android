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

package com.greplr;

import android.support.v4.app.Fragment;

import com.greplr.subcategories.travel.TravelFragment;

/**
 * Created by championswimmer on 22/6/15.
 */
public class Route {


    public static final int CAT_ORDER_TRAVEL = 0;
    public static final int CAT_ORDER_FOOD = 1;
    public static final int CAT_ORDER_EVENT = 2;
    public static final int CAT_ORDER_SHOPPING = 3;
    public static final int CAT_ORDER_NEWS = 4;

    public static interface TRAVEL {
        String ROOT = "/travel";
        String BUS = ROOT + "/bus";
        String CAB = ROOT + "/cab";
        String FLIGHT = ROOT + "/flight";
    }

    public static interface FOOD {
        String ROOT = "/food";
        String ORDER = ROOT + "/order";
        String RESTAURANT = ROOT + "/restaurant";
        String CAFE = ROOT + "/cafe";
        String BAR = ROOT + "/bar";
    }

    public static interface EVENTS {
        String ROOT = "/events";
        String MOVIE = ROOT + "/movie";
        String PLAY = ROOT + "/play";
    }

    public static Fragment getTopcategoryFragByRoute (String dataString) {
        if (dataString.contains(TRAVEL.ROOT)) return TravelFragment.newInstance(CAT_ORDER_TRAVEL);
        if (dataString.contains(FOOD.ROOT)) return TravelFragment.newInstance(CAT_ORDER_FOOD);
        if (dataString.contains(EVENTS.ROOT)) return TravelFragment.newInstance(CAT_ORDER_EVENT);
        return null;
    }

}
