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
 *     You should immediately close your copy of code, and destroy the file. You are not authorised to
 *     be in possession of this code or view or modify it or use it in any capacity.
 */

package com.greplr.subcategories.travel;

import com.greplr.R;
import com.greplr.subcategories.SubCategoryFragment;
import com.greplr.subcategories.UnderSubCategory;

/**
 * Created by championswimmer on 10/6/15.
 */
public class TravelFragment extends SubCategoryFragment {

    public static TravelFragment newInstance(int num) {
        TravelFragment tf = new TravelFragment();
        tf.topCatNo = num;
        return tf;
    }


    @Override
    public UnderSubCategory[] getUnderSubCategories() {
        return new UnderSubCategory[]{
                new UnderSubCategory(
                        "Cab",
                        R.drawable.background_travel_cab,
                        R.drawable.cardicon_travel_taxi,
                        TravelCabFragment.newInstance()
                ),
                new UnderSubCategory(
                        "Bus",
                        R.drawable.background_travel_bus,
                        R.drawable.cardicon_travel_bus,
                        TravelBusFragment.newInstance()
                ),
                new UnderSubCategory(
                        "Flight",
                        R.drawable.background_travel_flight,
                        R.drawable.cardicon_travel_flight,
                        TravelFlightFragment.newInstance()
                )
        };
    }

}
