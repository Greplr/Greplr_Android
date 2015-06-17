package com.greplr.topcategories;

import android.support.v4.app.Fragment;

import com.greplr.R;
import com.greplr.subcategories.food.FoodFragment;
import com.greplr.subcategories.travel.TravelFragment;

import java.util.ArrayList;

/**
 * Created by championswimmer on 11/6/15.
 */
public class Topcategories {

    public static final int TOTAL_CATEGORIES = 2;

    private static ArrayList<Category> topCategories;

    public static ArrayList<Category> getTopCategories() {
        if (topCategories == null) {
            topCategories = new ArrayList<>(TOTAL_CATEGORIES);
            topCategories.add(new Category(
                    "Travel",
                    R.drawable.cardicon_travel,
                    R.color.travel_cardColor,
                    TravelFragment.newInstance()));
            topCategories.add(new Category(
                    "Food",
                    R.drawable.cardicon_food,
                    R.color.food_cardColor,
                    FoodFragment.newInstance()));
        }
        return topCategories;
    }

    public static class Category {
        public Category(String name, int cardIcon, int cardColor, Fragment categoryFragment) {
            this.name = name;
            this.cardIcon = cardIcon;
            this.cardColor = cardColor;
            this.categoryFragment = categoryFragment;
        }

        public String name;
        public int cardIcon;
        public int cardColor;
        public Fragment categoryFragment;
    }
}
