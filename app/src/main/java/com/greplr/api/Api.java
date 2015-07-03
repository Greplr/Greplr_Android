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

package com.greplr.api;

import com.greplr.models.Feedback;
import com.greplr.models.events.Movies;
import com.greplr.models.events.Plays;
import com.greplr.models.food.Bar;
import com.greplr.models.food.Cafe;
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

    @FormUrlEncoded
    @GET("/api/travel/cabs")
    void getTravelCabs(
            @Query("lat") String latitude,
            @Query("lng") String longitude,
            Callback<List<Cab>> cabs);

    @FormUrlEncoded
    @GET("/api/travel/flight")
    void getTravelFlights(
            @Query("src") String src,
            @Query("dest") String dest,
            @Query("date") String date,
            @Query("adults") int adults,
            Callback<List<Flight>> callback);

    @FormUrlEncoded
    @GET("/api/travel/bus")
    void getTravelBus(
            @Query("src") String source,
            @Query("dest") String destination,
            @Query("date") String date,
            Callback<List<Bus>> callback);

    @FormUrlEncoded
    @GET("/api/food/restaurants")
    void getFoodRestaurants(
            @Query("lat") String latitude,
            @Query("lng") String longitude,
            Callback<List<Restaurant>> callback);

    @FormUrlEncoded
    @GET("/api/food/cafe")
    void getFoodCafes(
            @Query("lat") String latitude,
            @Query("lng") String longitude,
            Callback<List<Cafe>> callback);

    @FormUrlEncoded
    @GET("/api/food/bar")
    void getFoodBars(
            @Query("lat") String latitude,
            @Query("lng") String longitude,
            Callback<List<Bar>> callback);

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


}
