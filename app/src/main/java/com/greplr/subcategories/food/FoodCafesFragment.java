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
import com.greplr.ApplicationWrapper;
import com.greplr.MainActivity;
import com.greplr.R;
import com.greplr.adapters.NumberedAdapter;
import com.greplr.api.Api;
import com.greplr.models.food.Cafe;
import com.greplr.subcategories.UnderSubCategoryFragment;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by championswimmer on 15/6/15.
 */
public class FoodCafesFragment extends UnderSubCategoryFragment{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private List<Cafe> cafeList;

    public static FoodCafesFragment newInstance() {
        return new FoodCafesFragment();
    }

    @Override
    public int getFragmentIcon() {
        return R.drawable.cardicon_food_cafe;
    }

    @Override
    public String getPageTitle() {
        return "Cafes";
    }
    
    @Override
    public int getBackgroundResId() {
        return R.drawable.background_food_cafe;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Greplr", "FoodCafesFragment onCreateView");

        View rootView =  inflater.inflate(R.layout.fragment_food_cafe, container, false);

        Api apiHandler = ((MainActivity) getActivity()).getApiHandler();
        apiHandler.getFoodCafes(
                String.valueOf(ApplicationWrapper.currentLatitude),
                String.valueOf(ApplicationWrapper.currentLongitude),
                new Callback<List<Cafe>>() {
                    @Override
                    public void success(List<Cafe> cafes, Response response) {
                        Log.d("Greplr", "success" + response.getUrl() + response.getStatus());
                        cafeList = cafes;
                        updateCafes(cafeList);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("Greplr", "failure" + error.getUrl() + error.getMessage());

                    }
                }
        );


        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(
                R.id.recyclerview_food_cafe);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerViewMaterialAdapter(new NumberedAdapter(10));
        mRecyclerView.setAdapter(mAdapter);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

    }

    public void updateCafes (List<Cafe> cafes) {

    }
}
