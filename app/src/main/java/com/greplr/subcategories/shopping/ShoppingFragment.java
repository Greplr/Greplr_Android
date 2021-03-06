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

package com.greplr.subcategories.shopping;

import com.greplr.R;
import com.greplr.subcategories.SubCategoryFragment;
import com.greplr.subcategories.UnderSubCategory;

/**
 * Created by raghav on 01/07/15.
 */
public class ShoppingFragment extends SubCategoryFragment {

    public static ShoppingFragment newInstance(int num) {
        ShoppingFragment sf = new ShoppingFragment();
        sf.topCatNo = num;
        return sf;
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
    public int getCategoryColorDark() {
        return getResources().getColor(R.color.shopping_color_dark);
    }
}
