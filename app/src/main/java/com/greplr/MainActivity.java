package com.greplr;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.greplr.api.Api;
import com.greplr.models.location.GeoCodingLocationData;
import com.greplr.topcategories.TopcategoriesFragment;
import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchResult;

import retrofit.RestAdapter;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private static FragmentManager fragmentManager;

    private RestAdapter restAdapter;
    private Api apiHandler;
    private SearchBox search;
    private Toolbar toolbar;

    private LocationManager locationManager;
    private LocationListener locationListener;
    String latitudes, longitudes;
    boolean gpsEnabled = false;
    boolean networkEnabled = false;
    String geoLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        search = (SearchBox) findViewById(R.id.searchbox);
        search.enableVoiceRecognition(this);
        search.setLogoTextColor(getResources().getColor(android.R.color.white));

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, new TopcategoriesFragment()).commit();
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                openSearch();
                return true;
            }
        });

        getApiHandler();


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

    @Override
    protected void onStart() {
        super.onStart();
        try{
            gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            Log.d("gpsEnabled = ", gpsEnabled+"");
        } catch (Exception e){

        }

        try{
            networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            Log.d("gpsEnabled = ", networkEnabled+"");
        } catch (Exception e){

        }

        if(!gpsEnabled && !networkEnabled){
            //alert the user
            Log.d("raghav", "Reached here");
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Location is disabled, please enable it.");
            dialog.setCancelable(false);
            dialog.setPositiveButton("Enable", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    MainActivity.this.startActivity(intent);

                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog, int which) {
                    dialog.dismiss();
                    final Dialog customDialog = new Dialog(MainActivity.this);
                    LayoutInflater dialogLayout = getLayoutInflater();



                    customDialog.setContentView(R.layout.dialogbox_take_location);
                    customDialog.setTitle("Enter Your Location");
                    customDialog.setCancelable(false);
                    Button buttonDone = (Button) customDialog.findViewById(R.id.button_done);
                    buttonDone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditText locationEdTxt = (EditText) customDialog.findViewById(R.id.dialog_edittext);
                            String location = locationEdTxt.getText().toString();
                            if(location.equals("")){
                                locationEdTxt.setError("Please Enter");
                            } else {
                                customDialog.dismiss();
                                geoLocation = location;
                                Log.d("Location:", geoLocation + "");
                                GeoCodingLocationData.fetchData(geoLocation);

                            }


                        }
                    });
                    customDialog.show();

                }
            });
            dialog.show();


        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();


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

    public void openSearch() {
        toolbar.setTitle("");
        toolbar.hideOverflowMenu();
        getSupportActionBar().hide();
        search.revealFromMenuItem(R.id.action_search, this);
        for (int x = 0; x < 5; x++) {
            SearchResult option = new SearchResult("Result "
                    + Integer.toString(x), getResources().getDrawable(
                    R.drawable.ic_history));
            search.addSearchable(option);
        }
        search.setMenuListener(new SearchBox.MenuListener() {

            @Override
            public void onMenuClick() {
                // Hamburger has been clicked
                Toast.makeText(MainActivity.this, "Menu click",
                        Toast.LENGTH_LONG).show();
            }

        });
        search.setSearchListener(new SearchBox.SearchListener() {

            @Override
            public void onSearchOpened() {
                // Use this to tint the screen

            }

            @Override
            public void onSearchClosed() {
                // Use this to un-tint the screen
                closeSearch();
            }

            @Override
            public void onSearchTermChanged() {
                // React to the search term changing
                // Called after it has updated results
            }

            @Override
            public void onSearch(String searchTerm) {
                Toast.makeText(MainActivity.this, searchTerm + " Searched",
                        Toast.LENGTH_LONG).show();
                toolbar.setTitle(searchTerm);

            }

            @Override
            public void onSearchCleared() {

            }

        });

    }

    protected void closeSearch() {
        toolbar.showOverflowMenu();
        getSupportActionBar().show();
        search.hideCircularly(this);
        if(search.getSearchText().isEmpty())toolbar.setTitle("Greplr");
    }

    public static void switchFragment(Fragment frag, View view, String name) {
        //fragmentManager.beginTransaction().addSharedElement(view, name);
        fragmentManager.beginTransaction().replace(R.id.container, frag).addToBackStack("main").commit();
    }

    @Override
    public void onLocationChanged(Location location) {

//        Log.d("Latitudes = ", location.getLatitude()+"");
//        Log.d("Longitude = ", location.getLongitude()+"");
        ApplicationWrapper.currentLatitude = location.getLatitude();
        ApplicationWrapper.currentLongitude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}
