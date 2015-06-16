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

import java.util.Observable;

/**
 * Created by championswimmer on 15/6/15.
 */
public class TravelBusFragment extends UnderSubCategoryFragment{

    public static TravelBusFragment newInstance() {
        return new TravelBusFragment();
    }

    @Override
    public int getFragmentIcon() {
        return R.drawable.cardicon_travel_bus;
    }

    @Override
    public String getPageTitle() {
        return "Bus";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Greplr", "TravelBusFragment onCreateView");

        return inflater.inflate(R.layout.travel_bus_fragment, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ObservableScrollView osv = (ObservableScrollView) view.findViewById(R.id.travel_bus_scrollview);
        MaterialViewPagerHelper.registerScrollView(getActivity(), osv, null);

    }
}
