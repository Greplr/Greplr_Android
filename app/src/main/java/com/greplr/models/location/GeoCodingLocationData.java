package com.greplr.models.location;

import android.util.Log;

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

    String location;
    private static final String END_POINT = "http://128.199.128.227:8080";

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
                Log.d("raghav", geoCodingLocations.get(0).getLat());
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d("Raghav", retrofitError.getMessage());
            }
        });
    }
}
