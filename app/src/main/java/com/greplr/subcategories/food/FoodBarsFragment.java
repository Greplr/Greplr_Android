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
import com.google.gson.reflect.TypeToken;
import com.greplr.App;
import com.greplr.MainActivity;
import com.greplr.R;
import com.greplr.adapters.NumberedAdapter;
import com.greplr.api.Api;
import com.greplr.models.food.Bar;
import com.greplr.subcategories.UnderSubCategoryFragment;
import com.parse.ParseAnalytics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
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
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        long time = sharedPref.getLong("food/bars/time", System.currentTimeMillis());
        if(time == System.currentTimeMillis() || System.currentTimeMillis() - time > 300000) {
            Api apiHandler = ((MainActivity) getActivity()).getApiHandler();
            apiHandler.getFoodBars(
                    String.valueOf(App.currentLatitude),
                    String.valueOf(App.currentLongitude),
                    new Callback<List<Bar>>() {
                        @Override
                        public void success(List<Bar> bars, Response response) {
                            Log.d(LOG_TAG, "success" + response.getUrl());
                            Log.d(LOG_TAG, "lat " + App.currentLatitude);
                            Log.d(LOG_TAG, "lng " + App.currentLongitude);
                            Log.d(LOG_TAG, "response " + response.getStatus());
                            barList = bars;
                            updateBars(barList);
                            Gson gson = new Gson();
                            String json = gson.toJson(bars);
                            writeJSONFile(json);
                            sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                            editor = sharedPref.edit();
                            editor.putLong("food/bars/time", System.currentTimeMillis());
                            editor.commit();
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
        } else {
            //TODO show cached data
            Log.d(LOG_TAG, "Show cached data");
            Log.d(LOG_TAG, readJSONFile());
            Type listType = new TypeToken<List<Bar>>() {}.getType();
            List<Bar> bars = new Gson().fromJson(readJSONFile(), listType);
            Log.d(LOG_TAG,bars.get(0).getName());
            barList = bars;
//            updateBars(bars);
        }

        return rootView;
    }

    private String readJSONFile(){
        String ret = "";

        try {
            InputStream inputStream = getActivity().openFileInput("foodBarsJSON.json");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e(LOG_TAG, "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e(LOG_TAG, "Can not read file: " + e.toString());
        }

        return ret;
    }

    private void writeJSONFile(String jsonString) {
        File jsonFile = new File(getActivity().getFilesDir(), "foodBarsJSON.json");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(jsonFile);
            fos.write(jsonString.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                    .inflate(R.layout.bar_cardview_list_item, viewGroup, false);
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
