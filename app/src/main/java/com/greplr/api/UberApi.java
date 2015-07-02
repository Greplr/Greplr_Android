package com.greplr.api;

import com.greplr.models.travel.UberAuth;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by prempal on 2/7/15.
 */
public interface UberApi {

    String BASE_URL = "https://login.uber.com/oauth";

    @FormUrlEncoded
    @POST("/token")
    void getUberAuth(
            @Field("client_secret") String clientSecret,
            @Field("client_id") String clientId,
            @Field("grant_type") String grantType,
            @Field("redirect_uri") String redirectUri,
            @Field("code") String code,
            Callback<UberAuth> callback);
}
