package com.greplr.subcategories.food;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.greplr.R;
import com.greplr.adapters.NumberedAdapter;
import com.greplr.subcategories.UnderSubCategoryFragment;

/**
 * Created by championswimmer on 15/6/15.
 */
public class FoodBarsFragment extends UnderSubCategoryFragment{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    public static FoodBarsFragment newInstance() {
        return new FoodBarsFragment();
    }

    @Override
    public int getFragmentIcon() {
        return R.drawable.cardicon_food_bar;
    }

    @Override
    public String getPageTitle() {
        return "Bars";
    }
    
    @Override
    public int getBackgroundResId() {
        return R.drawable.background_food_bar;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Greplr", "FoodBarsFragment onCreateView");

        View rootView =  inflater.inflate(R.layout.food_bar_fragment, container, false);



        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(
                R.id.recyclerview_food_bar);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerViewMaterialAdapter(new NumberedAdapter(10));
        mRecyclerView.setAdapter(mAdapter);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

    }
}
