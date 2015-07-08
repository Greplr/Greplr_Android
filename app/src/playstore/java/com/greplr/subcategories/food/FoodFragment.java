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

package com.greplr.subcategories.food;

import com.greplr.R;
import com.greplr.subcategories.SubCategoryFragment;
import com.greplr.subcategories.UnderSubCategory;
import com.greplr.subcategories.food.FoodCafesFragment;

/**
 * Created by championswimmer on 10/6/15.
 */
public class FoodFragment extends SubCategoryFragment {

    public static FoodFragment newInstance() {
        return new FoodFragment();
    }

    @Override
    public UnderSubCategory[] getUnderSubCategories() {
        return new UnderSubCategory[]{
                new UnderSubCategory(
                        "Order",
                        R.drawable.background_food_order,
                        R.drawable.cardicon_food_order,
                        FoodOrderingFragment.newInstance()
                ),
                new UnderSubCategory(
                        "Restaurants",
                        R.drawable.background_food_restaurant,
                        R.drawable.cardicon_food_restaurant,
                        FoodRestaurantsFragment.newInstance()
                ),
                new UnderSubCategory(
                        "Cafes",
                        R.drawable.background_food_cafe,
                        R.drawable.cardicon_food_cafe,
                        FoodCafesFragment.newInstance()
                ),
                new UnderSubCategory(
                        "Bars",
                        R.drawable.background_food_bar,
                        R.drawable.cardicon_food_bar,
                        FoodBarsFragment.newInstance()
                )
        };
    }

    @Override
    public int topCatNo() {
        return 1;
    }
}
