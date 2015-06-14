package com.greplr.subcategories.food;

import com.greplr.R;
import com.greplr.subcategories.UnderSubCategoryFragment;

/**
 * Created by championswimmer on 15/6/15.
 */
public class FoodRestaurantsFragment extends UnderSubCategoryFragment{
    @Override
    public int getFragmentIcon() {
        return R.drawable.cardicon_food_utensils;
    }

    @Override
    public String getPageTitle() {
        return "Restaurants";
    }
}
