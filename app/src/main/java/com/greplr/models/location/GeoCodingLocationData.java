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

package com.greplr.models.location;

import android.util.Log;

import com.greplr.App;
import com.greplr.api.GeoCoding;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by raghav on 20/06/15.
 */
public class GeoCodingLocationData {

    private static final String END_POINT = "http://128.199.128.227:8080";
    String location;

    public GeoCodingLocationData(String location) {
        this.location = location;
    }

    public static void fetchData(String location) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(END_POINT)
                .build();


        GeoCoding post = restAdapter.create(GeoCoding.class);

        post.findLocation(location, new Callback<List<GeoCodingLocation>>() {


            @Override
            public void success(List<GeoCodingLocation> geoCodingLocations, Response response) {
                Log.d("raghav", geoCodingLocations.get(0).getLat() + "  " + geoCodingLocations.get(0).getArea());
                App.currentLatitude = Double.valueOf(geoCodingLocations.get(0).getLat());
                App.currentLongitude = Double.valueOf(geoCodingLocations.get(0).getLng());
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d("Raghav", retrofitError.getMessage());
            }
        });
    }
}
