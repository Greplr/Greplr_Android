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
 *     You should immediately close your copy of code, and destory the file. You are not authorised to
 *     be in possession of this code or view or modify it or use it in any capacity.
 */

package com.greplr.api;

import android.support.annotation.Nullable;

import com.greplr.models.news.Feed;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by championswimmer on 29/6/15.
 */
public interface NewsApi {

    String BASE_URL = "http://cloud.feedly.com/v3";

    @GET("/mixes/contents?locale=IN")
    void getNewsForTopic(
            @Query("streamId") String streamId,
            @Query("count") Integer count,
            @Nullable @Query("hour") Integer hour,
            @Nullable @Query("newerThan") Long newerThan,
            Callback<Feed> callback);

}
