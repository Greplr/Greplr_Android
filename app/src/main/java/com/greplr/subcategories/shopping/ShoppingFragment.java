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
                        "Offers",
                        R.drawable.background_shopping_offers,
                        R.drawable.cardicon_shopping_offers,
                        ShoppingOffersFragment.newInstance()
                ),
                new UnderSubCategory(
                        "Search",
                        R.drawable.background_shopping_search,
                        R.drawable.cardicon_shopping_search,
                        ShoppingSearchFragment.newInstance()
                )
        };
    }

    @Override
    public int topCatNo() {
        return 3;
    }
}
