package com.greplr.subcategories.food;

import com.greplr.R;
import com.greplr.subcategories.SubCategoryFragment;
import com.greplr.subcategories.UnderSubCategory;

/**
 * Created by championswimmer on 10/6/15.
 */
public class FoodFragment extends SubCategoryFragment {

    public static FoodFragment newInstance (){
        return new FoodFragment();
    }

    @Override
    public UnderSubCategory[] getUnderSubCategories() {
        return new UnderSubCategory[] {
                new UnderSubCategory(
                        "Order",
                        R.drawable.background_travel_cab,
                        R.drawable.cardicon_food_order,
                        FoodOrderingFragment.newInstance()
                ),
                new UnderSubCategory(
                        "Restaurants",
                        R.drawable.background_travel_bus,
                        R.drawable.cardicon_food_utensils,
                        FoodRestaurantsFragment.newInstance()
                )
        };
    }
    @Override
    public int topCatNo() {
        return 1;
    }
}
