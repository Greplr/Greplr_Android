package com.greplr.api;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by raghav on 20/06/15.
 */
public interface GeoCoding {
    @FormUrlEncoded
    @POST("/api/travel")
    void findLocation(@Field("location")String location, Callback<Object> callback);
}
