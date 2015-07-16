package com.greplr.api;

import com.greplr.models.shopping.Search;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by prempal on 16/7/15.
 */
public interface ShoppingApi {

    String BASE_URL = "http://mobileapi.flipkart.net/2/discover";

    @GET("/getSearch")
    void getShoppingResults(
            @Query("q") String query,
            @Query("store") String store,
            @Query("start") String start,
            @Query("count") String count,
            @Query("disableMultipleImage") String disableMultipleImage,
            Callback<Response> searchCallback);

}
