package com.greplr.models.location;

import android.util.Log;

import com.google.gson.Gson;
import com.greplr.api.GeoCoding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

    public GeoCodingLocationData (String location){
        this.location = location;
    }

    public static ArrayList<GeoCodingLocation> fetchData(String location){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(END_POINT)
                .build();



        GeoCoding post = restAdapter.create(GeoCoding.class);
        final ArrayList<GeoCodingLocation> geoCodingLocationArrayList = new ArrayList<GeoCodingLocation>();
        post.findLocation(location, new Callback<Object>() {


            @Override
            public void success(Object object, Response response) {
                Gson gson = new Gson();
                // convert java object to JSON format,
                // and returned as JSON formatted string
                String json = gson.toJson(object);
                Log.d("JSON:", json);
                try {

                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");

                    int i = 0;
                    Log.d("Array Length = ", ""+jsonArray.length()+ "  " + jsonArray.getJSONObject(0).getInt("lng"));

                    while(i < jsonArray.length()) {
                        GeoCodingLocation g = new GeoCodingLocation();
                        g.longitude = jsonArray.getJSONObject(i).getString("lng");
                        g.latitude = jsonArray.getJSONObject(i).getString("lat");
                        geoCodingLocationArrayList.add(g);
                        i++;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("raghav", ""+geoCodingLocationArrayList.get(0).longitude);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d("Raghav",retrofitError.getMessage());
            }
        });
        return geoCodingLocationArrayList;
    }
}
