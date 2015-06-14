package com.greplr.subcategories.travel;

import com.greplr.R;
import com.greplr.subcategories.UnderSubCategoryFragment;

/**
 * Created by championswimmer on 15/6/15.
 */
public class TravelBusFragment extends UnderSubCategoryFragment{
    @Override
    public int getFragmentIcon() {
        return R.drawable.cardicon_travel_bus;
    }

    @Override
    public String getPageTitle() {
        return "Bus";
    }
}
