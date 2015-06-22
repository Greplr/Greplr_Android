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

package com.greplr.subcategories.travel;

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

    @Override
    public int topCatNo() {
        return 0;
    }
}
