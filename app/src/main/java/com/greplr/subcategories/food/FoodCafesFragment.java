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
import com.greplr.models.food.Cafe;
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
public class FoodCafesFragment extends UnderSubCategoryFragment {

    public static final String LOG_TAG = "Greplr/Food/Cafes";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
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
        Log.d(LOG_TAG, "FoodCafesFragment onCreateView");

        View rootView = inflater.inflate(R.layout.fragment_food_cafe, container, false);
//        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
//        long time = sharedPref.getLong("food/cafes/time", System.currentTimeMillis());
//        if(time == System.currentTimeMillis() || System.currentTimeMillis() - time > 30000) {
            Api apiHandler = ((MainActivity) getActivity()).getApiHandler();
            apiHandler.getFoodCafes(
                    String.valueOf(App.currentLatitude),
                    String.valueOf(App.currentLongitude),
                    new Callback<List<Cafe>>() {
                        @Override
                        public void success(List<Cafe> cafes, Response response) {
                            Log.d(LOG_TAG, "success" + response.getUrl() + response.getStatus());
                            cafeList = cafes;
                            updateCafes(cafeList);
                            Gson gson = new Gson();
                            String json = gson.toJson(cafes);
                            Utils.writeJSONFile(json, getActivity(), "foodCafesJSON.json");
                            sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                            editor = sharedPref.edit();
                            editor.putLong("food/cafes/time", System.currentTimeMillis());
                            editor.commit();
                            Map<String, String> params = new HashMap<>();
                            params.put("lat", String.valueOf(App.currentLatitude));
                            params.put("lng", String.valueOf(App.currentLongitude));
                            params.put("success", "true");
                            ParseAnalytics.trackEventInBackground("food/cafes/search", params);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.d(LOG_TAG, "failure" + error.getUrl() + error.getMessage());
                            Map<String, String> params = new HashMap<>();
                            params.put("lat", String.valueOf(App.currentLatitude));
                            params.put("lng", String.valueOf(App.currentLongitude));
                            params.put("success", "false");
                            ParseAnalytics.trackEventInBackground("food/cafes/search", params);
                        }
                    }
            );
//        } else {
//            //TODO show cached data
//            Log.d(LOG_TAG, "Show cached data");
//            Log.d(LOG_TAG, Utils.readJSONFile(getActivity(), "foodCafesJSON.json"));
//            Type listType = new TypeToken<List<Cafe>>() {}.getType();
//            List<Cafe> cafes = new Gson().fromJson(Utils.readJSONFile(getActivity(), "foodCafesJSON.json"), listType);
//            Log.d(LOG_TAG,cafes.get(0).getName());
//            cafeList = cafes;
//            updateCafes(cafes);
//        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(
                R.id.recyclerview_food_cafe);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerViewMaterialAdapter(new NumberedAdapter(0));
        mRecyclerView.setAdapter(mAdapter);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

    }

    public void updateCafes(List<Cafe> cafes) {
        mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new CafeAdapter()));
    }

    public class CafeAdapter extends RecyclerView.Adapter<CafeAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            CardView v = (CardView) LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.cafe_cardview_list_item, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
            viewHolder.restaurantName.setText(cafeList.get(i).getName());
            viewHolder.distance.setText(String.valueOf(cafeList.get(i).getDistance()) + " meter");
            viewHolder.address.setText(cafeList.get(i).getAddress());
            viewHolder.location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<lat>,<long>?q="+cafeList.get(i).getLat()+","+cafeList.get(i).getLng()+"("+cafeList.get(i).getName()+")"));

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
            return cafeList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView restaurantName;
            TextView distance;
            TextView address;
            ImageButton location;

            public ViewHolder(CardView v) {
                super(v);
                restaurantName = (TextView) v.findViewById(R.id.cafe_name);
                distance = (TextView) v.findViewById(R.id.cafe_distance);
                address = (TextView) v.findViewById(R.id.cafe_address);
                location = (ImageButton) v.findViewById(R.id.location_cafe);
            }
        }
    }


}
