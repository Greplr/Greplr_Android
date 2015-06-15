package com.greplr.subcategories.food;

import android.support.v4.app.Fragment;

import com.greplr.subcategories.SubCategoryFragment;

/**
 * Created by championswimmer on 10/6/15.
 */
public class FoodFragment extends SubCategoryFragment {

    public static FoodFragment newInstance (){
        return new FoodFragment();
    }

    @Override
    public Fragment[] getUnderSubFragments() {
        Fragment[] underSubFragments = new Fragment[] {
            FoodOrderingFragment.newInstance(),
            FoodRestaurantsFragment.newInstance()
        };
        return underSubFragments;
    }
}
