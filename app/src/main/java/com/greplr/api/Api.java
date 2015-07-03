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

import com.greplr.models.events.Movies;
import com.greplr.models.events.Plays;
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
    @POST("/api/travel/cabs")
    void getTravelCabs(
            @Field("lat") String latitude,
            @Field("lng") String longitude,
            Callback<List<Cab>> cabs);

    @FormUrlEncoded
    @POST("/api/travel/flight")
    void getTravelFlights(
            @Field("src") String src,
            @Field("dest") String dest,
            @Field("date") String date,
            @Field("adults") int adults,
            Callback<List<Flight>> callback);

    @FormUrlEncoded
    @POST("/api/travel/bus")
    void getTravelBus(
            @Field("src") String source,
            @Field("dest") String destination,
            @Field("date") String date,
            Callback<List<Bus>> callback);

    @GET("/api/foodmmx/nearme")
    void getFoodRestaurants(
            @Query("lat") String latitude,
            @Query("lng") String longitude,
            Callback<List<Restaurant>> callback);

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
