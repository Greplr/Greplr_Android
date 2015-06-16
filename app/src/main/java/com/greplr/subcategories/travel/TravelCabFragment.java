package com.greplr.subcategories.travel;

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
public class TravelCabFragment extends UnderSubCategoryFragment {

    public static TravelCabFragment newInstance() {
        return new TravelCabFragment();
    }

    @Override
    public int getFragmentIcon() {
        return R.drawable.cardicon_travel_taxi;
    }

    @Override
    public String getPageTitle() {
        return "Cabs";
    }

    @Override
    public int getBackgroundResId() {
        return R.drawable.background_cab;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Greplr", "TravelCabFragment onCreateView");
        return inflater.inflate(R.layout.travel_cab_fragment, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ObservableScrollView osv = (ObservableScrollView) view.findViewById(R.id.travel_cab_scrollview);
        MaterialViewPagerHelper.registerScrollView(getActivity(), osv, null);

    }
}
