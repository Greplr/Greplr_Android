package com.greplr.subcategories.travel;

import android.support.v4.app.Fragment;

import com.greplr.subcategories.SubCategoryFragment;

/**
 * Created by championswimmer on 10/6/15.
 */
public class TravelFragment extends SubCategoryFragment {

    public static TravelFragment newInstance() {
        return new TravelFragment();
    }

    @Override
    public Fragment[] getUnderSubFragments() {
        return new Fragment[]{
                TravelCabFragment.newInstance(),
                TravelBusFragment.newInstance()
        };
    }
}
