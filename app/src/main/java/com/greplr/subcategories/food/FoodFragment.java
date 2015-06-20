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
                        R.drawable.background_food_order,
                        R.drawable.cardicon_food_order,
                        FoodOrderingFragment.newInstance()
                ),
                new UnderSubCategory(
                        "Restaurants",
                        R.drawable.background_food_restaurant,
                        R.drawable.cardicon_food_restaurant,
                        FoodRestaurantsFragment.newInstance()
                ),
                new UnderSubCategory(
                        "Cafes",
                        R.drawable.background_food_cafe,
                        R.drawable.cardicon_food_cafe,
                        FoodCafesFragment.newInstance()
                ),
                new UnderSubCategory(
                        "Bars",
                        R.drawable.background_food_bar,
                        R.drawable.cardicon_food_bar,
                        FoodBarsFragment.newInstance()
                )
        };
    }
    @Override
    public int topCatNo() {
        return 1;
    }
}
