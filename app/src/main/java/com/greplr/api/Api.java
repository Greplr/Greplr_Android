package com.greplr.api;

import com.greplr.models.travel.Cab;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by championswimmer on 20/6/15.
 */
public interface Api <T> {

    String BASE_URL = "http://128.199.128.227:8080";


    @FormUrlEncoded
    @POST("/api/travel/cabs")
    void getTravelCabs(
            @Field("lat") String latitude,
            @Field("lng") String longitude,
            Callback<List<Cab>> cabs);

}
