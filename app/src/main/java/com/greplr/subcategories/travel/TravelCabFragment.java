package com.greplr.subcategories.travel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.greplr.ApplicationWrapper;
import com.greplr.MainActivity;
import com.greplr.api.Api;
import com.greplr.models.travel.Cab;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.greplr.R;
import com.greplr.adapters.NumberedAdapter;
import com.greplr.subcategories.UnderSubCategoryFragment;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by championswimmer on 15/6/15.
 */
public class TravelCabFragment extends UnderSubCategoryFragment {

    private List<Cab> cabList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

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
        return R.drawable.background_travel_cab;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Greplr", "TravelCabFragment onCreateView");
        View rootView = inflater.inflate(R.layout.travel_cab_fragment, container, false);

        Api apiHandler = ((MainActivity) getActivity()).getApiHandler();
        apiHandler.getTravelCabs(
                String.valueOf(ApplicationWrapper.currentLatitude),
                String.valueOf(ApplicationWrapper.currentLongitude),
                new Callback<List<Cab>>() {
                    @Override
                    public void success(List<Cab> cabs, Response response) {
                        Log.d("Greplr", "success" + response.getUrl() + response.getStatus());
                        cabList = cabs;
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
                R.id.recyclerview_cab);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerViewMaterialAdapter(new NumberedAdapter(10));
        mRecyclerView.setAdapter(mAdapter);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

    }
}
