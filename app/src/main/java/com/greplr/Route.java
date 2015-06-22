/*
 * Greplr : A super-aggregator. One app to rule them all.
 *     Copyright (C) 2015  Greplr Team
 *     Where Greplr Team consists of :
 *       Arnav Gupta, Abhinv Sinha, Raghav Apoorv,
 *       Shubham Dokania, Yogesh Balan
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
