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

import com.greplr.subcategories.events.EventFragment;
import com.greplr.subcategories.food.FoodFragment;
import com.greplr.subcategories.news.NewsFragment;
import com.greplr.subcategories.shopping.ShoppingFragment;
import com.greplr.subcategories.travel.TravelFragment;

/**
 * Created by championswimmer on 22/6/15.
 */
public class Route {


    public static interface TRAVEL {
        int CAT_NUM = 0;
        String ROOT = "/travel";
        String BUS = ROOT + "/bus";
        String CAB = ROOT + "/cab";
        String FLIGHT = ROOT + "/flight";

        int SUBCAT_NUM_CAB = 0;
        int SUBCAT_NUM_BUS = 1;
        int SUBCAT_NUM_FLIGHT = 2;
    }

    public static interface FOOD {
        int CAT_NUM = 1;
        String ROOT = "/food";
        String ORDER = ROOT + "/order";
        String RESTAURANT = ROOT + "/restaurant";
        String CAFE = ROOT + "/cafe";
        String BAR = ROOT + "/bar";

        int SUBCAT_NUM_ORDER = 0;
        int SUBCAT_NUM_RESTAURANT = 1;
        int SUBCAT_NUM_CAFE = 2;
    }

    public static interface EVENTS {
        int CAT_NUM = 2;
        String ROOT = "/events";
        String MOVIE = ROOT + "/movie";
        String PLAY = ROOT + "/play";
    }

    public static interface SHOPPING {
        int CAT_NUM = 3;
        String ROOT = "/shopping";

    }

    public static interface NEWS {
        int CAT_NUM = 4;
        String ROOT = "/news";

    }

    public static Fragment getTopcategoryFragByRoute (String dataString) {
        if (dataString.contains(TRAVEL.ROOT)) return TravelFragment.newInstance(TRAVEL.CAT_NUM);
        if (dataString.contains(FOOD.ROOT)) return FoodFragment.newInstance(FOOD.CAT_NUM);
        if (dataString.contains(EVENTS.ROOT)) return EventFragment.newInstance(EVENTS.CAT_NUM);
        if (dataString.contains(SHOPPING.ROOT)) return ShoppingFragment.newInstance(SHOPPING.CAT_NUM);
        if (dataString.contains(NEWS.ROOT)) return NewsFragment.newInstance(NEWS.CAT_NUM);
        return null;
    }

    public static int getSubcategoryPositionByRoute (String dataString) {
        if (dataString.contains(TRAVEL.CAB)) return TRAVEL.SUBCAT_NUM_CAB;
        if (dataString.contains(TRAVEL.BUS)) return TRAVEL.SUBCAT_NUM_BUS;
        if (dataString.contains(TRAVEL.FLIGHT)) return TRAVEL.SUBCAT_NUM_FLIGHT;

        if (dataString.contains(FOOD.RESTAURANT)) return FOOD.SUBCAT_NUM_RESTAURANT;

        return 0;
    }

}
