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
