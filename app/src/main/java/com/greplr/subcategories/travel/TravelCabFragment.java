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

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
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
import com.greplr.ApplicationWrapper;
import com.greplr.MainActivity;
import com.greplr.R;
import com.greplr.adapters.NumberedAdapter;
import com.greplr.api.Api;
import com.greplr.models.travel.Cab;
import com.greplr.subcategories.UnderSubCategoryFragment;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by championswimmer on 15/6/15.
 */
public class TravelCabFragment extends UnderSubCategoryFragment {

    private List<Cab> cabList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

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
        Log.d("Greplr", "TravelCabFragment onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_travel_cab, container, false);

        Api apiHandler = ((MainActivity) getActivity()).getApiHandler();
        apiHandler.getTravelCabs(
                String.valueOf(ApplicationWrapper.currentLatitude),
                String.valueOf(ApplicationWrapper.currentLongitude),
                new Callback<List<Cab>>() {
                    @Override
                    public void success(List<Cab> cabs, Response response) {
                        Log.d("Greplr", "success" + response.getUrl() + response.getStatus());
                        cabList = cabs;
                        updateCabs(cabList);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("Greplr", "failure" + error.getUrl() + error.getMessage());

                    }
                }
        );

        return rootView;
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

}
