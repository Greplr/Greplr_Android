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
                    R.color.travel_color_primary,
                    TravelFragment.newInstance()));
            topCategories.add(new Category(
                    "Food",
                    R.drawable.cardicon_food,
                    R.drawable.topcategorycard_bg_food,
                    R.color.food_color_primary,
                    FoodFragment.newInstance()));
            topCategories.add(new Category(
                    "Events",
                    R.drawable.cardicon_events,
                    R.drawable.topcategorycard_bg_events,
                    R.color.event_color_primary,
                    EventFragment.newInstance()));
            topCategories.add(new Category(

                    "Shopping",
                    R.drawable.cardicon_shopping,
                    R.drawable.topcategorycard_bg_events,
                    R.color.shopping_color_primary,
                    ShoppingFragment.newInstance()));

            topCategories.add(new Category(
                    "News",
                    R.drawable.cardicon_news,
                    R.drawable.topcategorycard_bg_events,
                    R.color.news_color_primary,
                            NewsFragment.newInstance()));
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
