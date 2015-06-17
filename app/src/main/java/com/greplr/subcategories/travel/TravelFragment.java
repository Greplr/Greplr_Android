package com.greplr.subcategories.travel;

import android.support.v4.app.Fragment;

import com.greplr.R;
import com.greplr.subcategories.SubCategoryFragment;
import com.greplr.subcategories.UnderSubCategory;

/**
 * Created by championswimmer on 10/6/15.
 */
public class TravelFragment extends SubCategoryFragment {

    public static TravelFragment newInstance() {
        return new TravelFragment();
    }


    @Override
    public UnderSubCategory[] getUnderSubCategories() {
        return new UnderSubCategory[] {
                new UnderSubCategory(
                        "Cab",
                        R.drawable.background_cab,
                        R.drawable.cardicon_travel_taxi,
                        TravelCabFragment.newInstance()
                ),
                new UnderSubCategory(
                        "Bus",
                        R.drawable.background_bus,
                        R.drawable.cardicon_travel_bus,
                        TravelBusFragment.newInstance()
                )
        };
    }

    @Override
    public int topCatNo() {
        return 0;
    }
}
