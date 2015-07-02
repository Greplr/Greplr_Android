package com.greplr.subcategories.shopping;

import com.greplr.R;
import com.greplr.subcategories.SubCategoryFragment;
import com.greplr.subcategories.UnderSubCategory;

/**
 * Created by raghav on 01/07/15.
 */
public class ShoppingFragment extends SubCategoryFragment {

    public static ShoppingFragment newInstance() {
        return new ShoppingFragment();
    }


    @Override
    public UnderSubCategory[] getUnderSubCategories() {
        return new UnderSubCategory[]{
                new UnderSubCategory(
                        "Search",
                        R.drawable.background_travel_bus,
                        R.drawable.cardicon_travel_bus,
                        ShoppingSearchFragment.newInstance()
                ),
                new UnderSubCategory(
                        "Offers",
                        R.drawable.background_travel_cab,
                        R.drawable.cardicon_travel_taxi,
                        ShoppingOffersFragment.newInstance()
                )
        };
    }

    @Override
    public int topCatNo() {
        return 0;
    }
}
