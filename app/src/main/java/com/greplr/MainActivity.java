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


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.greplr.api.Api;
import com.greplr.api.NewsApi;
import com.greplr.models.location.GeoCodingLocationData;
import com.greplr.topcategories.TopcategoriesFragment;
import com.parse.ParseAnalytics;
import com.parse.ParseUser;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.HashMap;
import java.util.Map;

import retrofit.RestAdapter;

public class MainActivity
        extends AppCompatActivity
        implements FragmentManager.OnBackStackChangedListener,
        com.google.android.gms.location.LocationListener,
        ResultCallback<LocationSettingsResult> {

    public static final String LOG_TAG = "Greplr/MainActivity";

    private static FragmentManager fragmentManager;
    private final int REQUEST_CHECK_SETTINGS = 0x1;
    String geoLocation;
    private RestAdapter restAdapter;
    private Api apiHandler;
    private NewsApi newsApiHandler;
    private Toolbar toolbar;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private Boolean mRequestingLocationUpdates;
    private ImageView backgroundImage;
    private Boolean locationFix = false;
    private LinearLayout bottomSliderLayout;
    private SlidingUpPanelLayout slideFrame;
    private Boolean isActivityRunning = true;
    private long activityStartTime;


    public static void switchFragment(Fragment frag, boolean addToBackStack) {
        if (addToBackStack) {
            fragmentManager.beginTransaction().replace(R.id.container, frag).addToBackStack("main").commit();
        } else {
            fragmentManager.beginTransaction().replace(R.id.container, frag).commit();
        }

    }

    public static void goToTopFragment() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack("main", 0);
        } else {
            fragmentManager.beginTransaction().replace(R.id.container, TopcategoriesFragment.newInstance()).commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseUser parseUser = ParseUser.getCurrentUser();
        if ((parseUser == null)) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        } else {
            mRequestingLocationUpdates = false;
            createLocationRequest();
            buildLocationSettingsRequest();
            checkLocationSettings();

            toolbar = (Toolbar) findViewById(R.id.main_toolbar);
            setSupportActionBar(toolbar);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
            }
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    return true;
                }
            });

            fragmentManager = getSupportFragmentManager();
            fragmentManager.addOnBackStackChangedListener(this);

            if (App.locationInitialised) {
                goToTopFragment();
            } else {
                fragmentManager.beginTransaction().replace(R.id.container, LoaderFragment.newInstance()).commit();
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!locationFix && isActivityRunning) {
                            App.locationInitialised = true;
                            fragmentManager.beginTransaction().replace(R.id.container, new TopcategoriesFragment()).commit();
                        }
                    }
                }, 5000);
            }


            bottomSliderLayout = (LinearLayout) findViewById(R.id.bottom_slider_layout);
            slideFrame = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);

            getApiHandler();
        }

        backgroundImage = (ImageView) findViewById(R.id.main_background_image);
    }

    public void showSlidePanel () {
        //bottomSliderLayout.setVisibility(View.VISIBLE);
        slideFrame.setPanelHeight(getResources().getDimensionPixelOffset(R.dimen.sliding_panel_height));
    }

    public void hideSlidePanel() {
        //bottomSliderLayout.setVisibility(View.GONE);
        slideFrame.setPanelHeight(0);
    }

    public ImageView getBackgroundImage() {
        return backgroundImage;
    }

    public Api getApiHandler() {
        if (apiHandler == null) {
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(Api.BASE_URL)
                    .build();
            apiHandler = restAdapter.create(Api.class);
        }
        return apiHandler;
    }
    public NewsApi getNewsApiHandler() {
        if (newsApiHandler == null) {
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(NewsApi.BASE_URL)
                    .build();
            newsApiHandler = restAdapter.create(NewsApi.class);
        }
        return newsApiHandler;
    }

    @Override
    protected void onStart() {
        super.onStart();
        ((App) getApplication()).getGoogleApiClient().connect();
        Map<String, String> params = new HashMap<>();
        activityStartTime = System.nanoTime();
        params.put("device/brand", Build.BRAND);
        params.put("device/model", Build.MODEL);
        ParseAnalytics.trackEventInBackground("mainactivity/open", params);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (((App) getApplication()).getGoogleApiClient().isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
        }
        isActivityRunning = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (((App) getApplication()).getGoogleApiClient().isConnected()) {
            stopLocationUpdates();
        }
        isActivityRunning = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        ((App) getApplication()).getGoogleApiClient().disconnect();
        Map<String, String> params = new HashMap<>();
        long timeElapsed = System.nanoTime() - activityStartTime;
        timeElapsed = timeElapsed/1000000000;//Convert to seconds;
        params.put("spent/min", String.valueOf((timeElapsed/60)));
        params.put("spent/sec", String.valueOf((timeElapsed%60)));
        ParseAnalytics.trackEventInBackground("mainactivity/close", params);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.i(LOG_TAG, "User agreed to make required location settings changes.");
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i(LOG_TAG, "User chose not to make required location settings changes.");
                        final Dialog customDialog = new Dialog(MainActivity.this);
                        customDialog.setContentView(R.layout.dialogbox_take_location);
                        customDialog.setTitle("Enter Your Location");
                        customDialog.setCancelable(false);
                        Button buttonDone = (Button) customDialog.findViewById(R.id.button_done);
                        buttonDone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                EditText locationEdTxt = (EditText) customDialog.findViewById(R.id.dialog_edittext);
                                String location = locationEdTxt.getText().toString();
                                if (location.equals("")) {
                                    locationEdTxt.setError("Please Enter");
                                } else {
                                    customDialog.dismiss();
                                    geoLocation = location;
                                    Log.d("Location:", geoLocation + "");
                                    GeoCodingLocationData.fetchData(geoLocation);
                                    if (!App.locationInitialised) {
                                        App.locationInitialised = true;
                                        locationFix = true;
                                        goToTopFragment();
                                    }
                                }
                            }
                        });
                        customDialog.show();
                        break;
                }
                break;
        }
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates.
        mLocationRequest.setInterval(10000);

        // Sets the fastest rate for active location updates.
        mLocationRequest.setFastestInterval(5000);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    protected void checkLocationSettings() {
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        ((App) getApplication()).getGoogleApiClient(),
                        mLocationSettingsRequest
                );
        result.setResultCallback(this);
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                ((App) getApplication()).getGoogleApiClient(),
                mLocationRequest,
                this
        ).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                mRequestingLocationUpdates = true;
            }
        });
    }

    protected void stopLocationUpdates() {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.
        LocationServices.FusedLocationApi.removeLocationUpdates(
                ((App) getApplication()).getGoogleApiClient(),
                this
        ).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                mRequestingLocationUpdates = false;
            }
        });
    }


    @Override
    public void onLocationChanged(Location location) {
        Log.d(LOG_TAG, "onLocationChanged");
        App.currentLocation = location;
        Log.d(LOG_TAG, "Latitudes = " + location.getLatitude() + "");
        Log.d(LOG_TAG, "Longitude = " + location.getLongitude() + "");
        App.currentLatitude = location.getLatitude();
        App.currentLongitude = location.getLongitude();

        if (!App.locationInitialised) {
            App.locationInitialised = true;
            locationFix = true;
            goToTopFragment();
        }
    }


    @Override
    public void onResult(LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                Log.i(LOG_TAG, "All location settings are satisfied.");
                startLocationUpdates();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                Log.i(LOG_TAG, "Location settings are not satisfied. Show the user a dialog to" +
                        "upgrade location settings ");

                try {
                    // Show the dialog by calling startResolutionForResult(), and check the result
                    // in onActivityResult().
                    status.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException e) {
                    Log.i(LOG_TAG, "PendingIntent unable to execute request.");
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                Log.i(LOG_TAG, "Location settings are inadequate, and cannot be fixed here. Dialog " +
                        "not created.");
                break;
        }
    }

    @Override
    public void onBackStackChanged() {

        if (fragmentManager.getBackStackEntryCount() == 0){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
                getWindow().setNavigationBarColor(getResources().getColor(android.R.color.black));
            }
            setSupportActionBar(toolbar);
            getSupportActionBar().show();
            showSlidePanel();
        }

    }
}
