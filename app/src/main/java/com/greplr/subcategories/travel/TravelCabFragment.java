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

package com.greplr.subcategories.travel;


import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.greplr.App;
import com.greplr.R;
import com.greplr.adapters.ErrorAdapter;
import com.greplr.adapters.LoaderAdapter;
import com.greplr.api.Api;
import com.greplr.models.travel.Cab;
import com.greplr.subcategories.UnderSubCategoryFragment;
import com.parse.ParseAnalytics;

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
        return inflater.inflate(R.layout.fragment_travel_cab, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(
                R.id.recyclerview_cab);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new LoaderAdapter()));
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
        final Api apiHandler = ((App) getActivity().getApplication()).getApiHandler();
        apiHandler.getTravelCabs(
                String.valueOf(App.currentLatitude),
                String.valueOf(App.currentLongitude),
                new Callback<List<Cab>>() {
                    @Override
                    public void success(List<Cab> cabs, Response response) {
                        Log.d(LOG_TAG, "success" + response.getUrl() + response.getStatus());
                        cabList = cabs;
                        mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new CabAdapter()));
                        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
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
                        mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new ErrorAdapter()));
                        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
                        //Parse Analytics
                        Map<String, String> params = new HashMap<>();
                        params.put("lat", String.valueOf(App.currentLatitude));
                        params.put("lng", String.valueOf(App.currentLongitude));
                        params.put("success", "false");
                        ParseAnalytics.trackEventInBackground("travel/cabs/search", params);

                    }
                }
        );
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
            if (viewHolder.displayName.getText().toString().equalsIgnoreCase("ubergo") || viewHolder.displayName.getText().toString().equalsIgnoreCase("hatchback")) {
                viewHolder.cabType.setImageResource(R.drawable.ic_cab_hatchback);
            } else if (viewHolder.displayName.getText().toString().equalsIgnoreCase("uberx") || viewHolder.displayName.getText().toString().equalsIgnoreCase("sedan")) {
                viewHolder.cabType.setImageResource(R.drawable.ic_cab_sedan);
            } else if (viewHolder.displayName.getText().toString().equalsIgnoreCase("uberblack")) {
                viewHolder.cabType.setImageResource(R.drawable.ic_cab_minivan);
            }
            viewHolder.minPrice.setText("Minimum price : \u20b9" + cabList.get(i).getMin_price());
            viewHolder.timeOfArrival.setText("ETA :  " + cabList.get(i).getTime_of_arrival() + " min");
            viewHolder.prizePerKM.setText("₹" + cabList.get(i).getPrice_per_km() + " /Km");
            viewHolder.provider.setText(cabList.get(i).getProvider());
            if (viewHolder.provider.getText().toString().equalsIgnoreCase("uber")) {
                viewHolder.icon.setBackgroundResource(R.drawable.ic_brand_uber);
                viewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, String> params = new HashMap<>();
                        params.put("lat", String.valueOf(App.currentLatitude));
                        params.put("lng", String.valueOf(App.currentLongitude));
                        params.put("travel/cab cab searched", cabList.get(i).getDisplay_name());
                        params.put("success", "true");
                        ParseAnalytics.trackEventInBackground("travel/cabs/search", params);
                        PackageManager pm = getActivity().getPackageManager();
                        try {
                            params.put("lat", String.valueOf(App.currentLatitude));
                            params.put("lng", String.valueOf(App.currentLongitude));
                            params.put("Uber app found", "true");
                            params.put("success", "true");
                            ParseAnalytics.trackEventInBackground("travel/cabs/search", params);
                            pm.getPackageInfo("com.ubercab", PackageManager.GET_ACTIVITIES);
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(
                                    "uber://?action=setPickup&pickup=my_location&product_id=" + cabList.get(i).getProduct_id())));
                        } catch (PackageManager.NameNotFoundException e) {
                            // No Uber app!
                            params.put("lat", String.valueOf(App.currentLatitude));
                            params.put("lng", String.valueOf(App.currentLongitude));
                            params.put("Uber app found", "false");
                            params.put("success", "true");
                            ParseAnalytics.trackEventInBackground("travel/cabs/search", params);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setData(Uri.parse("market://details?id=" + "com.ubercab"));
                            getActivity().startActivity(intent);

                        }
                    }
                });
            } else if (viewHolder.provider.getText().toString().equalsIgnoreCase("taxiforsure")) {
                viewHolder.icon.setBackgroundResource(R.drawable.ic_brand_taxiforsure);
                viewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, String> params = new HashMap<>();
                        params.put("lat", String.valueOf(App.currentLatitude));
                        params.put("lng", String.valueOf(App.currentLongitude));
                        params.put("travel/cab cab searched", cabList.get(i).getDisplay_name());
                        params.put("success", "true");
                        ParseAnalytics.trackEventInBackground("travel/cabs/search", params);
                        Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.tfs.consumer");
                        if (intent != null) {
                            params.put("lat", String.valueOf(App.currentLatitude));
                            params.put("lng", String.valueOf(App.currentLongitude));
                            params.put("TFS app found", "true");
                            params.put("success", "true");
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getActivity().startActivity(intent);
                        } else {
                            //No TFS App
                            params.put("lat", String.valueOf(App.currentLatitude));
                            params.put("lng", String.valueOf(App.currentLongitude));
                            params.put("TFS app found", "false");
                            params.put("success", "true");
                            ParseAnalytics.trackEventInBackground("travel/cabs/search", params);
                            intent = new Intent(Intent.ACTION_VIEW);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setData(Uri.parse("market://details?id=" + "com.tfs.consumer"));
                            getActivity().startActivity(intent);
                        }

                    }
                });
            } else
                viewHolder.icon.setImageResource(R.drawable.placeholder_cab);
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
            ImageView cabType;
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
                cabType = (ImageView) v.findViewById(R.id.cab_type);
            }
        }
    }

}
