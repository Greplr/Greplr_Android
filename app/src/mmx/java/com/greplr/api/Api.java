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

package com.greplr.api;

import com.greplr.models.Feedback;
import com.greplr.models.events.Movies;
import com.greplr.models.events.Plays;
import com.greplr.models.food.Order;
import com.greplr.models.food.Restaurant;
import com.greplr.models.shopping.offers.Offers;
import com.greplr.models.shopping.search.Search;
import com.greplr.models.travel.Bus;
import com.greplr.models.travel.Cab;
import com.greplr.models.travel.Flight;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by championswimmer on 20/6/15.
 */
public interface Api {

    String BASE_URL = "http://128.199.128.227:8080";

    @GET("/api/travel/cabs")
    void getTravelCabs(
            @Query("lat") String latitude,
            @Query("lng") String longitude,
            Callback<List<Cab>> cabs);

    @GET("/api/travel/flight")
    void getTravelFlights(
            @Query("src") String src,
            @Query("dest") String dest,
            @Query("date") String date,
            @Query("adults") int adults,
            Callback<List<Flight>> callback);

    @GET("/api/travel/bus")
    void getTravelBus(
            @Query("src") String source,
            @Query("dest") String destination,
            @Query("date") String date,
            Callback<List<Bus>> callback);


    @GET("/api/foodmmx/nearme")
    void getFoodRestaurants(
            @Query("lat") String latitude,
            @Query("lng") String longitude,
            Callback<Restaurant> callback);

    @FormUrlEncoded
    @POST("/api/feedback")
    void giveFeedBack(
            @Field("feedback") String feedback,
            @Field("field") String field,
            Callback<List<Feedback>> callback);


    @GET("/api/events/movies")
    void getEventMovies(
            //@Query("mid") String param1,
            Callback<List<Movies>> callback);

    @GET("/api/events/plays")
    void getEventPlays(
            //@Query("mid") String param1,
            Callback<List<Plays>> callback);

    @GET("/api/events/events")
    void getEventCultural(
            //@Query("mid") String param1,
            Callback<List<Plays>> callback);
    @GET("/api/shop/offers")
    void getShoppingOffers(
            Callback<List<Offers>> callback);

    @GET("/api/shop/search")
    void getShoppingResult(
            @Query("q") String search,
            Callback<List<Search>> callback);

    @GET("/api/food/order")
    void getOrderFood(
            @Query("lat") String latitude,
            @Query("lng") String longitude,
            Callback<Order> callback);
}
