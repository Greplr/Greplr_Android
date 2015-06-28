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

package com.greplr.subcategories.travel;


import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.greplr.App;
import com.greplr.MainActivity;
import com.greplr.R;
import com.greplr.adapters.NumberedAdapter;
import com.greplr.api.Api;
import com.greplr.models.Feedback;
import com.greplr.models.travel.Cab;
import com.greplr.subcategories.UnderSubCategoryFragment;
import com.parse.ParseAnalytics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by championswimmer on 15/6/15.
 */
public class TravelCabFragment extends UnderSubCategoryFragment {

    public static final String LOG_TAG = "Greplr/Travel/Cab";

    private List<Cab> cabList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    public static TravelCabFragment newInstance() {
        return new TravelCabFragment();
    }

    @Override
    public int getFragmentIcon() {
        return R.drawable.cardicon_travel_taxi;
    }

    @Override
    public String getPageTitle() {
        return "Cabs";
    }

    @Override
    public int getBackgroundResId() {
        return R.drawable.background_travel_cab;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(LOG_TAG, "TravelCabFragment onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_travel_cab, container, false);
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        long time = sharedPref.getLong("travel/cabs/time", System.currentTimeMillis());
        if(time == System.currentTimeMillis() || System.currentTimeMillis() - time > 300000) {
            Api apiHandler = ((MainActivity) getActivity()).getApiHandler();
            apiHandler.getTravelCabs(
                    String.valueOf(App.currentLatitude),
                    String.valueOf(App.currentLongitude),
                    new Callback<List<Cab>>() {
                        @Override
                        public void success(List<Cab> cabs, Response response) {
                            Log.d(LOG_TAG, "success" + response.getUrl() + response.getStatus());
                            Gson gson = new Gson();
                            String json = gson.toJson(cabs);
                            writeJSONFile(json);
                            sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                            editor = sharedPref.edit();
                            editor.putLong("travel/cabs/time", System.currentTimeMillis());
                            editor.commit();
                            cabList = cabs;
                            updateCabs(cabList);
                            //Parse Analytics
                            Map<String, String> params = new HashMap<>();
                            params.put("lat", String.valueOf(App.currentLatitude));
                            params.put("lng", String.valueOf(App.currentLongitude));
                            params.put("success", "true");
                            ParseAnalytics.trackEventInBackground("travel/cabs/search", params);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.d(LOG_TAG, "failure" + error.getUrl() + error.getMessage());
                            //Parse Analytics
                            Map<String, String> params = new HashMap<>();
                            params.put("lat", String.valueOf(App.currentLatitude));
                            params.put("lng", String.valueOf(App.currentLongitude));
                            params.put("success", "false");
                            ParseAnalytics.trackEventInBackground("travel/cabs/search", params);
                        }
                    }
            );
        } else {
            Log.d(LOG_TAG, "Show cached data");
            Log.d(LOG_TAG, readJSONFile());
            Type listType = new TypeToken<List<Cab>>() {}.getType();
            List<Cab> cabs = new Gson().fromJson(readJSONFile(), listType);
            Log.d(LOG_TAG,cabs.get(0).getDisplay_name());
            cabList =cabs;
//            updateCabs(cabs);
        }
        return rootView;
    }

    private String readJSONFile(){
        String ret = "";

        try {
            InputStream inputStream = getActivity().openFileInput("travelCabsJSON.json");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e(LOG_TAG, "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e(LOG_TAG, "Can not read file: " + e.toString());
        }

        return ret;
    }

    private void writeJSONFile(String jsonString) {
        File jsonFile = new File(getActivity().getFilesDir(), "travelCabsJSON.json");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(jsonFile);
            fos.write(jsonString.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(
                R.id.recyclerview_cab);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new RecyclerViewMaterialAdapter(new NumberedAdapter(0));
        mRecyclerView.setAdapter(mAdapter);

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

    }

    public void updateCabs(List<Cab> cabs) {
        mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new CabAdapter()));
    }

    public AlertDialog newCabHailDialog(final Context c, final Cab cab) {

        AlertDialog.Builder cabHailDialog = new AlertDialog.Builder(c);
        cabHailDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(c)
                                .setSmallIcon(R.drawable.ic_brand_uber)
                                .setContentTitle(cab.getDisplay_name() + "is on the way")
                                .setContentText("Your cab is " + cab.getTime_of_arrival() + " minutes away");
                NotificationManager mNotifyMgr =
                        (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
                mNotifyMgr.notify(1010, mBuilder.build());
            }
        });
        cabHailDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        cabHailDialog.setTitle("Book " + cab.getDisplay_name());
        cabHailDialog.setMessage("Are you sure you want to book\n" +
                cab.getDisplay_name() + " from " + cab.getProvider() + " ?\n" +
                "Minimum Fare : " + cab.getMin_price());
        return cabHailDialog.create();
    }

    public class CabAdapter extends RecyclerView.Adapter<CabAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            CardView v = (CardView) LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.cardview_cab_list_item, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
            viewHolder.displayName.setText(cabList.get(i).getDisplay_name());
            viewHolder.minPrice.setText("Minimum price : \u20b9" + cabList.get(i).getMin_price());
            viewHolder.timeOfArrival.setText("ETA :  " + cabList.get(i).getTime_of_arrival() + " min");
            viewHolder.prizePerKM.setText("₹" + cabList.get(i).getPrice_per_km() + " /Km");
            viewHolder.provider.setText(cabList.get(i).getProvider());
            if (viewHolder.provider.getText().toString().equalsIgnoreCase("uber")) {
                viewHolder.icon.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_brand_uber));
                viewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog cabDialog = newCabHailDialog(getActivity(), cabList.get(i));
                        cabDialog.show();
                    }
                });
            } else if (viewHolder.provider.getText().toString().equalsIgnoreCase("taxiforsure")) {
                viewHolder.icon.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_brand_taxiforsure));
            } else
                viewHolder.icon.setBackgroundDrawable(getResources().getDrawable(R.drawable.placeholder_cab));
            viewHolder.view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final Dialog customDialog = new Dialog(getActivity());
                    customDialog.setContentView(R.layout.dialogbox_take_location);
                    customDialog.setTitle("Give your feedback");
                    customDialog.setCancelable(false);
                    Button buttonDone = (Button) customDialog.findViewById(R.id.button_done);
                    buttonDone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditText locationEdTxt = (EditText) customDialog.findViewById(R.id.dialog_edittext);
                            String userFeedback = locationEdTxt.getText().toString();
                            if (userFeedback.equals("")) {
                                locationEdTxt.setError("Please Enter");
                            } else {
                                customDialog.dismiss();
                                Api apiHandler = ((MainActivity) getActivity()).getApiHandler();
                                apiHandler.giveFeedBack(
                                        userFeedback,
                                        "Travel",
                                        new Callback<List<Feedback>>() {
                                            @Override
                                            public void success(List<Feedback> feedbacks, Response response) {
                                                Log.d(LOG_TAG, "success" + response.getUrl() + response.getStatus());
                                            }

                                            @Override
                                            public void failure(RetrofitError error) {
                                                Log.d(LOG_TAG, "failure" + error.getUrl() + error.getMessage());

                                            }
                                        }
                                );
                            }


                        }
                    });
                    customDialog.show();


                    return true;
                }
            });
        }

        @Override
        public int getItemCount() {
            return cabList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView displayName;
            TextView minPrice;
            TextView timeOfArrival;
            TextView prizePerKM;
            TextView provider;
            ImageView icon;
            View view;

            public ViewHolder(CardView v) {
                super(v);
                view = v;
                displayName = (TextView) v.findViewById(R.id.cab_name);
                minPrice = (TextView) v.findViewById(R.id.min_price);
                timeOfArrival = (TextView) v.findViewById(R.id.time_of_arrival);
                prizePerKM = (TextView) v.findViewById(R.id.price_per_km);
                provider = (TextView) v.findViewById(R.id.provider);
                icon = (ImageView) v.findViewById(R.id.app_icon);
            }
        }
    }

}
