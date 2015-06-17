package com.greplr.subcategories.food;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.greplr.R;
import com.greplr.subcategories.UnderSubCategoryFragment;

/**
 * Created by championswimmer on 15/6/15.
 */
public class FoodRestaurantsFragment extends UnderSubCategoryFragment{

    public static FoodRestaurantsFragment newInstance() {
        return new FoodRestaurantsFragment();
    }

    @Override
    public int getFragmentIcon() {
        return R.drawable.cardicon_food_utensils;
    }

    @Override
    public String getPageTitle() {
        return "Restaurants";
    }
    
    @Override
    public int getBackgroundResId() {
        return R.drawable.background_food_restaurant;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Greplr", "FoodRestaurantsFragment onCreateView");

        return inflater.inflate(R.layout.food_restaurant_fragment, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ObservableScrollView osv = (ObservableScrollView) view.findViewById(R.id.food_restaurant_scrollview);
        MaterialViewPagerHelper.registerScrollView(getActivity(), osv, null);

    }
}
