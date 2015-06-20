package com.greplr.api;

import com.greplr.models.location.GeoCodingLocation;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by raghav on 20/06/15.
 */
public interface GeoCoding {
    @FormUrlEncoded
    @POST("/geo")
    void findLocation(@Field("location")String location, Callback<List<GeoCodingLocation>> geoCodingLocation);
}
