package com.greplr.api;

import com.greplr.models.travel.Bus;
import com.greplr.models.travel.Cab;
import com.greplr.models.travel.Flight;

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
public interface Api {

    String BASE_URL = "http://128.199.128.227:8080";

    @FormUrlEncoded
    @POST("/api/travel/cabs")
    void getTravelCabs(
            @Field("lat") String latitude,
            @Field("lng") String longitude,
            Callback<List<Cab>> cabs);

    @FormUrlEncoded
    @POST("/api/travel/flight")
    void getTravelFlights(
            @Field("src")String src,
            @Field("dest")String dest,
            @Field("date") String date,
            @Field("adults") int adults,
            Callback<List<Flight>> callback);

    @FormUrlEncoded
    @POST("/api/travel/bus")
    void getTravelBus(
            @Field("src")String source,
            @Field("dest")String destination,
            @Field("date")String date,
            Callback<List<Bus>> callback);



}
