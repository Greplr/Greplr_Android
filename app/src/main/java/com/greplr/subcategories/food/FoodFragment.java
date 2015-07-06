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

package com.greplr.subcategories.food;

import com.greplr.R;
import com.greplr.subcategories.SubCategoryFragment;
import com.greplr.subcategories.UnderSubCategory;

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
