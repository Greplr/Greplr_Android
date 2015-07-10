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

package com.greplr.topcategories;

import android.support.v4.app.Fragment;

import com.greplr.R;
import com.greplr.subcategories.events.EventFragment;
import com.greplr.subcategories.food.FoodFragment;
import com.greplr.subcategories.shopping.ShoppingFragment;
import com.greplr.subcategories.news.NewsFragment;
import com.greplr.subcategories.travel.TravelFragment;

import java.util.ArrayList;

/**
 * Created by championswimmer on 11/6/15.
 */
public class Topcategories {

    public static final int TOTAL_CATEGORIES = 5;

    private static ArrayList<Category> topCategories;

    public static ArrayList<Category> getTopCategories() {
        if (topCategories == null) {
            topCategories = new ArrayList<>(TOTAL_CATEGORIES);
            topCategories.add(new Category(
                    "Travel",
                    R.drawable.cardicon_travel,
                    R.drawable.topcategorycard_bg_travel,
                    R.color.travel_color_dark,
                    TravelFragment.newInstance(0)));
            topCategories.add(new Category(
                    "Food",
                    R.drawable.cardicon_food,
                    R.drawable.topcategorycard_bg_food,
                    R.color.food_color_dark,
                    FoodFragment.newInstance(1)));
            topCategories.add(new Category(
                    "Events",
                    R.drawable.cardicon_events,
                    R.drawable.topcategorycard_bg_events,
                    R.color.events_color_dark,
                    EventFragment.newInstance(2)));
            topCategories.add(new Category(

                    "Shopping",
                    R.drawable.cardicon_shopping,
                    R.drawable.topcategorycard_bg_events,
                    R.color.shopping_color_dark,
                    ShoppingFragment.newInstance(3)));

            topCategories.add(new Category(
                    "News",
                    R.drawable.cardicon_news,
                    R.drawable.topcategorycard_bg_events,
                    R.color.news_color_primary,
                            NewsFragment.newInstance(4)));
        }
        return topCategories;
    }

    public static class Category {
        public String name;
        public int cardIcon;
        public int drawable;
        public int cardColor;
        public Fragment categoryFragment;
        public Category(String name, int cardIcon, int drawable, int cardColor, Fragment categoryFragment) {
            this.name = name;
            this.cardIcon = cardIcon;
            this.drawable = drawable;
            this.cardColor = cardColor;
            this.categoryFragment = categoryFragment;
        }
    }
}
