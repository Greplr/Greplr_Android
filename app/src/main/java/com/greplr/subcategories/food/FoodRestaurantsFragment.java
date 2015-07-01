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

import android.content.Context;
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
import com.google.gson.Gson;
import com.greplr.App;
import com.greplr.MainActivity;
import com.greplr.R;
import com.greplr.Utils;
import com.greplr.adapters.NumberedAdapter;
import com.greplr.api.Api;
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
//        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
//        long time = sharedPref.getLong("food/restaurants/time", System.currentTimeMillis());
//        if (time == System.currentTimeMillis() || System.currentTimeMillis() - time > 300000) {
            Api apiHandler = ((MainActivity) getActivity()).getApiHandler();
            apiHandler.getFoodRestaurants(
                    String.valueOf(App.currentLatitude),
                    String.valueOf(App.currentLongitude),
                    new Callback<List<Restaurant>>() {
                        @Override
                        public void success(List<Restaurant> restaurants, Response response) {
                            Log.d(LOG_TAG, "success" + response.getUrl() + response.getStatus());
                            restaurantList = restaurants;
                            updateRestaurants(restaurantList);
                            Gson gson = new Gson();
                            String json = gson.toJson(restaurants);
                            Utils.writeJSONFile(json, getActivity(), "foodRestaurantsJSON.json");
                            sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                            editor = sharedPref.edit();
                            editor.putLong("food/restaurants/time", System.currentTimeMillis());
                            editor.commit();
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
                            params.put("success", "false");
                            ParseAnalytics.trackEventInBackground("food/restaurants/search", params);
                        }
                    }
            );
//        } else {
//            //TODO show cached data
//            Log.d(LOG_TAG, "Show cached data");
//            Log.d(LOG_TAG, Utils.readJSONFile(getActivity(), "foodRestaurantsJSON.json"));
//            Type listType = new TypeToken<List<Restaurant>>() {
//            }.getType();
//            List<Restaurant> restaurants = new Gson().fromJson(Utils.readJSONFile(getActivity(), "foodRestaurantsJSON.json"), listType);
//            Log.d(LOG_TAG, restaurants.get(0).getName());
//            restaurantList = restaurants;
//            updateRestaurants(restaurantList);
//        }

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

    public void updateRestaurants(List<Restaurant> restaurants) {
        mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new RestaurantAdapter()));
    }

    public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            CardView v = (CardView) LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.restaurant_cardview_list_item, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
            viewHolder.restaurantName.setText(restaurantList.get(i).getName());
            viewHolder.distance.setText(String.valueOf(restaurantList.get(i).getDistance()) + " meter");
            viewHolder.address.setText(restaurantList.get(i).getAddress());
            viewHolder.location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<lat>,<long>?q="+ restaurantList.get(i).getLat()+","+ restaurantList.get(i).getLng()+"("+restaurantList.get(i).getName()+")"));

                    startActivity(intent);
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
            return restaurantList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView restaurantName;
            TextView distance;
            TextView address;
            ImageButton location;

            public ViewHolder(CardView v) {
                super(v);
                restaurantName = (TextView) v.findViewById(R.id.rest_name);
                distance = (TextView) v.findViewById(R.id.rest_distance);
                address = (TextView) v.findViewById(R.id.rest_address);
                location = (ImageButton) v.findViewById(R.id.location_restaurant);
            }
        }
    }


}
