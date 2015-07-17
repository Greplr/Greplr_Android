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
 *     You should immediately close your copy of code, and destroy the file. You are not authorised to
 *     be in possession of this code or view or modify it or use it in any capacity.
 */

package com.greplr.subcategories.food;

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
import com.greplr.adapters.ErrorAdapter;
import com.greplr.adapters.LoaderAdapter;
import com.greplr.api.Api;
import com.greplr.common.utils.Utils;
import com.greplr.models.food.Restaurant;
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
public class FoodRestaurantsFragment extends UnderSubCategoryFragment {

    public static final String LOG_TAG = "Greplr/Food/Restaurants";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private List<Restaurant> restaurantList;

    public static FoodRestaurantsFragment newInstance() {
        return new FoodRestaurantsFragment();
    }

    @Override
    public int getFragmentIcon() {
        return R.drawable.cardicon_food_restaurant;
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
        Log.d(LOG_TAG, "FoodRestaurantsFragment onCreateView");

        View rootView = inflater.inflate(R.layout.fragment_food_restaurant, container, false);
        Api apiHandler = ((App) getActivity().getApplication()).getApiHandler();
        apiHandler.getFoodRestaurants(
                String.valueOf(App.currentLatitude),
                String.valueOf(App.currentLongitude),
                new Callback<List<Restaurant>>() {
                    @Override
                    public void success(List<Restaurant> restaurants, Response response) {
                        Log.d(LOG_TAG, "success" + response.getUrl() + response.getStatus());
                        restaurantList = restaurants;
                        updateRestaurants(restaurantList);
                        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
                        Map<String, String> params = new HashMap<>();
                        params.put("lat", String.valueOf(App.currentLatitude));
                        params.put("lng", String.valueOf(App.currentLongitude));
                        params.put("success", "true");
                        ParseAnalytics.trackEventInBackground("food/restaurants/search", params);

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(LOG_TAG, "failure" + error.getUrl() + error.getMessage());
                        mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new ErrorAdapter()));
                        Map<String, String> params = new HashMap<>();
                        params.put("lat", String.valueOf(App.currentLatitude));
                        params.put("lng", String.valueOf(App.currentLongitude));
                        params.put("success", "true");
                        ParseAnalytics.trackEventInBackground("food/restaurants/search", params);
                    }
                }
            );
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(
                R.id.recyclerview_food_restaurant);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new LoaderAdapter()));
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

    }

    public void updateRestaurants(List<Restaurant> restaurants) {
        mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new RestaurantAdapter()));
    }

    public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            CardView v = (CardView) LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.cardview_restaurant_list_item, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
            viewHolder.restaurantName.setText(restaurantList.get(i).getName());
            viewHolder.distance.setText(Utils.friendlyDistance(String.valueOf(restaurantList.get(i).getDistance())));
            viewHolder.address.setText(restaurantList.get(i).getAddress());
            viewHolder.location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<lat>,<long>?q="+ restaurantList.get(i).getLat()+","+ restaurantList.get(i).getLng()+"("+restaurantList.get(i).getName()+")"));

                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return restaurantList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView restaurantName;
            TextView distance;
            TextView address;
            ImageButton location;

            public ViewHolder(CardView v) {
                super(v);
                restaurantName = (TextView) v.findViewById(R.id.restaurant_name);
                distance = (TextView) v.findViewById(R.id.rest_distance);
                address = (TextView) v.findViewById(R.id.rest_address);
                location = (ImageButton) v.findViewById(R.id.restaurant_location);
            }
        }
    }


}
