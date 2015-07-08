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
 *     You should immediately close your copy of code, and destory the file. You are not authorised to
 *     be in possession of this code or view or modify it or use it in any capacity.
 */

package com.greplr.subcategories.food;

import android.content.ActivityNotFoundException;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.greplr.App;
import com.greplr.R;
import com.greplr.adapters.NumberedAdapter;
import com.greplr.api.Api;
import com.greplr.models.food.Bar;
import com.greplr.subcategories.UnderSubCategoryFragment;
import com.parse.ParseAnalytics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by championswimmer on 15/6/15.
 */
public class FoodBarsFragment extends UnderSubCategoryFragment {

    public static final String LOG_TAG = "Greplr/Food/Bars";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Bar> barList;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

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
        Log.d(LOG_TAG, "FoodBarsFragment onCreateView");

        View rootView = inflater.inflate(R.layout.fragment_food_bar, container, false);
        Api apiHandler = ((App) getActivity().getApplication()).getApiHandler();
        apiHandler.getFoodBars(
                String.valueOf(App.currentLatitude),
                String.valueOf(App.currentLongitude),
                new Callback<List<Bar>>() {
                    @Override
                    public void success(List<Bar> bars, Response response) {
                        Log.d(LOG_TAG, "success" + response.getUrl());
                        Log.d(LOG_TAG,"lat " + App.currentLatitude);
                        Log.d(LOG_TAG,"lng " + App.currentLongitude);
                        Log.d(LOG_TAG, "response " + response.getStatus());
                        barList = bars;
                        updateBars(barList);
                        Map<String, String> params = new HashMap<>();
                        params.put("lat", String.valueOf(App.currentLatitude));
                        params.put("lng", String.valueOf(App.currentLongitude));
                        params.put("success", "true");
                        ParseAnalytics.trackEventInBackground("food/bars/search", params);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(LOG_TAG, "failure" + error.getUrl() + error.getMessage());
                        Map<String, String> params = new HashMap<>();
                        params.put("lat", String.valueOf(App.currentLatitude));
                        params.put("lng", String.valueOf(App.currentLongitude));
                        params.put("success", "true");
                        ParseAnalytics.trackEventInBackground("food/bars/search", params);


                        }
                    }
            );

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(
                R.id.recyclerview_food_bar);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerViewMaterialAdapter(new NumberedAdapter(0));
        mRecyclerView.setAdapter(mAdapter);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

    }

    public void updateBars(List<Bar> bars) {
        mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new BarAdapter()));
    }

    public class BarAdapter extends RecyclerView.Adapter<BarAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            CardView v = (CardView) LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.cardview_bar_list_item, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
            viewHolder.restaurantName.setText(barList.get(i).getName());
            viewHolder.distance.setText(String.valueOf(barList.get(i).getDistance()) + " meter");
            viewHolder.address.setText(barList.get(i).getAddress());
            viewHolder.location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                            Uri.parse("http://maps.google.com/maps?q=" + barList.get(i).getLat() + "," + barList.get(i).getLng()));
//                    startActivity(intent);
                    try {
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                Uri.parse("geo:0,0?q=" + barList.get(i).getLat() + "," + barList.get(i).getLng() + "(" + barList.get(i).getName() + ")"));
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://maps.google.com/maps?q=loc:" + barList.get(i).getLat() + "," + barList.get(i).getLng()+"("+ barList.get(i).getName()  + ")&iwloc=A&hl=es")));
                    }
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<lat>,<long>?q="+barList.get(i).getLat()+","+barList.get(i).getLng()+"("+barList.get(i).getName()+")"));
//                    startActivity(intent);

                }
            });
           /* if (viewHolder.provider.getText().toString().equalsIgnoreCase("uber")) {
                viewHolder.icon.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_brand_uber));
            } else if (viewHolder.provider.getText().toString().equalsIgnoreCase("taxiforsure")) {
                viewHolder.icon.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_brand_taxiforsure));
            } else
                viewHolder.icon.setBackgroundDrawable(getResources().getDrawable(R.drawable.placeholder_cab));*/
        }

        @Override
        public int getItemCount() {
            return barList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView restaurantName;
            TextView distance;
            TextView address;
            ImageButton location;
            public ViewHolder(CardView v) {
                super(v);
                restaurantName = (TextView) v.findViewById(R.id.bar_name);
                distance = (TextView) v.findViewById(R.id.bar_distance);
                address = (TextView) v.findViewById(R.id.bar_address);
                location = (ImageButton) v.findViewById(R.id.location_bar);
            }
        }
    }
}
