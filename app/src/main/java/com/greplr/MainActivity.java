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
import android.app.SearchManager;
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
import android.support.v7.widget.SearchView;
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
import com.google.android.gms.location.LocationListener;
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
        LocationListener,
        ResultCallback<LocationSettingsResult>,
        IWitListener, SearchView.OnQueryTextListener {

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
    public String deepLinkUrl = "";

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
                String dataString = getIntent().getDataString();
                Log.d(LOG_TAG, "Starting from Uri : " + dataString);
                doDeepLinking(dataString);
            }

        }

        backgroundImage = (ImageView) findViewById(R.id.main_background_image);
    }

    public void doDeepLinking (String dataStr) {
        deepLinkUrl = dataStr.toLowerCase();
        try {
            switchFragment(Route.getTopcategoryFragByRoute(deepLinkUrl), false);
        } catch (NullPointerException ne) {
            ne.printStackTrace();
        }
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

        SearchManager searchManager =
                (SearchManager) getSystemService(SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(MainActivity.this);

        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
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
                String witOutcomeUrl = Utils.processWitOutcome(jsonOutput);
                Log.d(LOG_TAG, witOutcomeUrl);
                doDeepLinking(witOutcomeUrl);
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
