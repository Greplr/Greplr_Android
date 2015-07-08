/*
 * Greplr : A super-aggregator. One app to rule them all.
 *     Copyright (C) 2015  Greplr Team
 *     Where Greplr Team consists of :
 *       Arnav Gupta, Abhinv Sinha, Raghav Apoorv,
 *       Shubham Dokania, Yogesh Balan
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.greplr.subcategories.food;

import android.content.ActivityNotFoundException;
import android.content.Intent;
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
import com.greplr.models.food.Restaurant;
import com.greplr.subcategories.UnderSubCategoryFragment;
import com.parse.ParseAnalytics;

import java.util.ArrayList;
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
    private List<Restaurant.RestaurantResult.RestaurantItem> restaurantList;

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

        restaurantList = new ArrayList<>();

        View rootView = inflater.inflate(R.layout.fragment_food_restaurant, container, false);
        Api apiHandler = ((App) getActivity().getApplication()).getApiHandler();
        apiHandler.getFoodRestaurants(
                String.valueOf(App.currentLatitude),
                String.valueOf(App.currentLongitude),
                new Callback<Restaurant>() {
                    @Override
                    public void success(Restaurant restaurants, Response response) {
                        Log.d(LOG_TAG, "success" + response.getUrl() + response.getStatus());
                        for (int i = 0; i < restaurants.getResults().size(); i++) {
                            restaurantList.add(restaurants.getResults().get(i).getResult());
                        }
                        mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new RestaurantAdapter()));
                        Map<String, String> params = new HashMap<>();
                        params.put("lat", String.valueOf(App.currentLatitude));
                        params.put("lng", String.valueOf(App.currentLongitude));
                        params.put("success", "true");
                        ParseAnalytics.trackEventInBackground("food/restaurants/search", params);

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(LOG_TAG, "failure" + error.getUrl() + error.getMessage());
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
        mAdapter = new RecyclerViewMaterialAdapter(new NumberedAdapter(0));
        mRecyclerView.setAdapter(mAdapter);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

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
            viewHolder.distance.setText(restaurantList.get(i).getDistance_friendly());
            viewHolder.address.setText(restaurantList.get(i).getAddress());
            //viewHolder.price.setText("\u20B9 " + restaurantList.get(i).getCost_for_two() + " for two");
            viewHolder.location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("geo:0,0?q=" + restaurantList.get(i).getLatitude() + "," + restaurantList.get(i).getLongitude() + "(" + restaurantList.get(i).getName() + ")"));
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://maps.google.com/maps?q=loc:" + restaurantList.get(i).getLatitude() + "," + restaurantList.get(i).getLongitude()+"("+ restaurantList.get(i).getName()  + ")&iwloc=A&hl=es")));
                    }
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
            TextView price;
            ImageButton location;

            public ViewHolder(CardView v) {
                super(v);
                restaurantName = (TextView) v.findViewById(R.id.rest_name);
                distance = (TextView) v.findViewById(R.id.rest_distance);
                address = (TextView) v.findViewById(R.id.rest_address);
                location = (ImageButton) v.findViewById(R.id.location_restaurant);
                //price = (TextView) v.findViewById(R.id.rest_price);
            }
        }
    }


}