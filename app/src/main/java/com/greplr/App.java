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

package com.greplr;

import android.app.Application;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.greplr.api.Api;
import com.greplr.api.NewsApi;
import com.greplr.api.UberApi;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by championswimmer on 17/6/15.
 */
public class App extends Application
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static final String LOG_TAG = "Greplr/App";

    public static boolean DARK_CARDS = false;

    public static double currentLatitude = 28.6328;
    public static double currentLongitude = 77.2197;
    public static Location currentLocation;

    private GoogleApiClient mGoogleApiClient;

    private Api apiHandler;
    private NewsApi newsApiHandler;
    private UberApi uberApiHandler;
    private RestAdapter restAdapter;
    private OkHttpClient ok;



    public static boolean locationInitialised = false;

    @Override
    public void onCreate() {
        SharedPreferences sPref = getSharedPreferences("location", MODE_PRIVATE);
        currentLatitude = Double.valueOf(sPref.getString("lastLatitude", "28.6328"));
        currentLongitude = Double.valueOf(sPref.getString("lastLongitude", "77.2197"));
        Parse.initialize(this, "tA24P8ZHIuY6T6GCePmlwS94r7DKYIl0DrYee41g", "1pJVaZcK43AXO6wAtDzqPs1GMkA1E3jHtXTNCzKc");
        ParseFacebookUtils.initialize(this);
        ParseTwitterUtils.initialize("PdLB1zaNjktVXpBqPddVMWENt", "ND4JkQj7lucz1uQO0URF3mBfQFORIzSKY2oHUPZGtJgbaDzZLY");
        buildGoogleApiClient();
        super.onCreate();

    }

    @Override
    public void onTerminate() {
        SharedPreferences sPref = getSharedPreferences("location", MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString("lastLatitude", String.valueOf(currentLatitude));
        editor.putString("lastLongitude", String.valueOf(currentLongitude));
        editor.apply();
        super.onTerminate();
    }

    protected synchronized void buildGoogleApiClient() {
        Log.i(LOG_TAG, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    protected GoogleApiClient getGoogleApiClient () {
        return mGoogleApiClient;
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (currentLocation == null) {
            currentLocation = LocationServices.FusedLocationApi.getLastLocation(getGoogleApiClient());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public OkHttpClient getOkHttpClient(){
        if(ok == null){
            ok = new OkHttpClient();
            try {
                Cache responseCache = new Cache(getCacheDir(), 1024 * 1024 * 10);
                ok.setCache(responseCache);
            } catch (Exception e) {
                Log.d(LOG_TAG, "Unable to set http cache", e);
            }
            ok.setReadTimeout(30, TimeUnit.SECONDS);
            ok.setConnectTimeout(30, TimeUnit.SECONDS);
        }
        return ok;
    }

    public Api getApiHandler() {
        //Make sure we just have one instance
        if (apiHandler == null) {
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(Api.BASE_URL)
                    .setClient(new OkClient(getOkHttpClient()))
                    .build();
            apiHandler = restAdapter.create(Api.class);
        }
        return apiHandler;
    }
    public NewsApi getNewsApiHandler() {
        //Make sure we just have one instance
        if (newsApiHandler == null) {
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(NewsApi.BASE_URL)
                    .setClient(new OkClient(getOkHttpClient()))
                    .build();
            newsApiHandler = restAdapter.create(NewsApi.class);
        }
        return newsApiHandler;
    }

    public UberApi getUberApiHandler(){
        //Make sure we just have one instance
        if(uberApiHandler == null){
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(UberApi.BASE_URL)
                    .setClient(new OkClient(getOkHttpClient()))
                    .build();
            uberApiHandler = restAdapter.create(UberApi.class);
        }
        return uberApiHandler;
    }
}
