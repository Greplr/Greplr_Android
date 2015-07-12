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

/**
 * Created by championswimmer on 22/6/15.
 */
public class Route {

    public static final String TRAVEL = "travel";
    public static final String FOOD = "food";
    public static final String EVENTS = "events";

    public static interface TRAVEL {
        String travel = "/"+ TRAVEL +"/";
        String BUS = travel + "bus";
        String CAB = travel + "cab";
        String FLIGHT = travel + "flight";
    }

    public static interface FOOD {
        String food = "/"+ FOOD +"/";
        String ORDER = food + "order";
        String RESTAURANT = food + "restaurant";
        String CAFE = food + "cafe";
        String BAR = food + "bar";
    }

    public static interface EVENTS {
        String events = "/"+ EVENTS +"/";
        String MOVIE = events + "movie";
        String PLAY = events + "play";
    }

}
