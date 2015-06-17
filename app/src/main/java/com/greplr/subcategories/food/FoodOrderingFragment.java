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
public class FoodOrderingFragment extends UnderSubCategoryFragment{

    public static FoodOrderingFragment newInstance() {
        return new FoodOrderingFragment();
    }

    @Override
    public int getFragmentIcon() {
        return R.drawable.cardicon_food_order;
    }

    @Override
    public String getPageTitle() {
        return "Order";
    }

    @Override
    public int getBackgroundResId() {
        return R.drawable.background_food_order;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Greplr", "FoodOrderingFragment onCreateView");

        return inflater.inflate(R.layout.food_order_fragment, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ObservableScrollView osv = (ObservableScrollView) view.findViewById(R.id.food_order_scrollview);
        MaterialViewPagerHelper.registerScrollView(getActivity(), osv, null);

    }
}
