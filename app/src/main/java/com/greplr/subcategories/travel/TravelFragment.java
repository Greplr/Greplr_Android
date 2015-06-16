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
    public Fragment getUnderSubFragments(int pos) {
        switch (pos) {
            case 0:default: return TravelCabFragment.newInstance();
            case 1: return TravelBusFragment.newInstance();
        }
    }

    @Override
    public int getUnderSubFragCount() {
        return 2;
    }
}
