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

package com.greplr;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import android.widget.Toast;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.greplr.common.utils.Utils;
import com.greplr.models.location.GeoCodingLocationData;
import com.greplr.topcategories.TopcategoriesFragment;
import com.parse.ParseAnalytics;
import com.parse.ParseUser;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ai.wit.sdk.IWitListener;
import ai.wit.sdk.Wit;
import ai.wit.sdk.model.WitOutcome;

public class MainActivity
        extends AppCompatActivity
        implements FragmentManager.OnBackStackChangedListener,
        com.google.android.gms.location.LocationListener,
        ResultCallback<LocationSettingsResult>,
        IWitListener {

    public static final String LOG_TAG = "Greplr/MainActivity";
    private static FragmentManager fragmentManager;
    private final int REQUEST_CHECK_SETTINGS = 0x1;
    String geoLocation;
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
    private EditText search;

    private Wit wit;


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

            wit = new Wit("F7VYYKKXBOHM3R6TYCRRPZFQGO3WF5ZR", this);
            wit.enableContextLocation(getApplicationContext());

            toolbar = (Toolbar) findViewById(R.id.main_toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.main_toolbarColor)));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
            }

            search = (EditText) toolbar.findViewById(R.id.greplr_search);

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

            toolbar = (Toolbar) findViewById(R.id.main_toolbar);
            setSupportActionBar(toolbar);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));

            bottomSliderLayout = (LinearLayout) findViewById(R.id.bottom_slider_layout);
            slideFrame = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);

            ((App) getApplication()).getApiHandler();

            if (getIntent().getData() != null) {
                String deepLinkUri = getIntent().getDataString();
                Log.d(LOG_TAG, "Starting from Uri : " + deepLinkUri);
                switchFragment(Route.getTopcategoryFragByRoute(deepLinkUri), false);
            }

        }

        backgroundImage = (ImageView) findViewById(R.id.main_background_image);
    }

    public void showSlidePanel() {
        //bottomSliderLayout.setVisibility(View.VISIBLE);
        //slideFrame.setPanelHeight(getResources().getDimensionPixelOffset(R.dimen.sliding_panel_height));

        // For now always hide
        hideSlidePanel();
    }

    public void hideSlidePanel() {
        //bottomSliderLayout.setVisibility(View.GONE);
        slideFrame.setPanelHeight(0);
    }

    public ImageView getBackgroundImage() {
        return backgroundImage;
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
//
//        params.put("total time", timeFormat((System.currentTimeMillis()-activityStartTime)/1000));
//        ParseAnalytics.trackEventInBackground("application close", params);

        long timeElapsed = System.nanoTime() - activityStartTime;
        timeElapsed = timeElapsed / 1000000000;//Convert to seconds;
        params.put("spent/min", String.valueOf((timeElapsed / 60)));
        params.put("spent/sec", String.valueOf((timeElapsed % 60)));
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
            if (search.getText().toString().length() != 0) {
                wit.captureTextIntent(search.getText().toString());
            } else {
                Toast.makeText(this, "Please enter search query", Toast.LENGTH_SHORT).show();
            }
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
    public void onLocationChanged(Location loc) {
        Log.d(LOG_TAG, "onLocationChanged");
        Location location = Utils.adjustLocationDecimalPrecision(loc);
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

        if (fragmentManager.getBackStackEntryCount() == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
                getWindow().setNavigationBarColor(getResources().getColor(android.R.color.black));
            }
            setSupportActionBar(toolbar);
            try {
                getSupportActionBar().show();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            showSlidePanel();
        }

    }

    @Override
    public void witDidGraspIntent(ArrayList<WitOutcome> witOutcomes, String s, Error error) {
        if (error != null) {
            Log.d(LOG_TAG, error.getLocalizedMessage());
        } else {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonOutput = gson.toJson(witOutcomes);
            Log.d(LOG_TAG, jsonOutput);
            try {
                Log.d(LOG_TAG, Utils.processWitOutcome(jsonOutput));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void witDidStartListening() {
        Log.d(LOG_TAG, "witDidStartListening()");
    }

    @Override
    public void witDidStopListening() {
        Log.d(LOG_TAG, "witDidStopListening()");
    }

    @Override
    public void witActivityDetectorStarted() {
        Log.d(LOG_TAG, "witActivityDetectorStarted()");
    }

    @Override
    public String witGenerateMessageId() {
        return null;
    }
}
