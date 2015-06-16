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
    public Fragment getUnderSubFragments(int pos) {
        switch (pos) {
            case 0: default: return FoodOrderingFragment.newInstance();
            case 1: return FoodRestaurantsFragment.newInstance();
        }
    }

    @Override
    public int getUnderSubFragCount() {
        return 2;
    }
}
