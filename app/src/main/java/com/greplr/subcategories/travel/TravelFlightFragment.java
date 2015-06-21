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

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.greplr.MainActivity;
import com.greplr.R;
import com.greplr.adapters.NumberedAdapter;
import com.greplr.api.Api;
import com.greplr.models.travel.Flight;
import com.greplr.subcategories.UnderSubCategoryFragment;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by championswimmer on 15/6/15.
 */
public class TravelFlightFragment extends UnderSubCategoryFragment {

    private List<Flight> flightList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    public static TravelFlightFragment newInstance() {
        return new TravelFlightFragment();
    }

    @Override
    public int getFragmentIcon() {
        return R.drawable.cardicon_travel_flight;
    }

    @Override
    public String getPageTitle() {
        return "Flights";
    }

    @Override
    public int getBackgroundResId() {
        return R.drawable.background_travel_flight;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Greplr", "TravelFlightFragment onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_travel_flight, container, false);

        Api apiHandler = ((MainActivity) getActivity()).getApiHandler();
        apiHandler.getTravelFlights(
                "DEL",
                "BOM",
                "20150627",
                1,
                new Callback<List<Flight>>() {
                    @Override
                    public void success(List<Flight> flights, Response response) {
                        Log.d("Greplr", "success" + response.getUrl() + response.getStatus());
                        flightList = flights;
                        UpdateFlight(flightList);
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
                R.id.recyclerview_flight);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerViewMaterialAdapter(new NumberedAdapter(0));
        mRecyclerView.setAdapter(mAdapter);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

    }

    public void UpdateFlight(List<Flight> flights) {
        mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new FlightAdapter()));
    }

    public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.ViewHolder> {

        static final int TYPE_HEADER = 0;
        static final int TYPE_CELL = 1;

        @Override
        public int getItemViewType(int position) {
            switch (position) {
                case 0:
                    return TYPE_HEADER;
                default:
                    return TYPE_CELL;
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            CardView v = null;

            switch (i) {
                case TYPE_HEADER: {
                    v = (CardView) LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.tools_list_item_card_big_app, viewGroup, false);
                    return new ViewHolder(v);
                }
                case TYPE_CELL: {
                    v = (CardView) LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.cardview_flight_list_item, viewGroup, false);
                    return new ViewHolder(v);
                }
            }
            return null;
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
            switch (getItemViewType(i)) {
                case TYPE_HEADER:
                    break;
                case TYPE_CELL:
                    break;
            }
            if(getItemViewType(i)==TYPE_CELL) {
                viewHolder.airline.setText(flightList.get(i).getAirline());
                viewHolder.flightnum.setText("Flight No. : " + flightList.get(i).getFlightnum());
                viewHolder.depdate.setText(flightList.get(i).getDepdate());
                viewHolder.arrdate.setText(flightList.get(i).getArrdate());
                viewHolder.seatingclass.setText(flightList.get(i).getSeatingclass());
                viewHolder.flight_fare.setText("₹" + flightList.get(i).getFare());

                if (viewHolder.airline.getText().toString().equalsIgnoreCase("spicejet")) {
                    viewHolder.icon.setBackgroundResource(R.drawable.ic_brand_spicejet);
                }
                if (viewHolder.airline.getText().toString().equalsIgnoreCase("IndiGo")) {
                    viewHolder.icon.setBackgroundResource(R.drawable.ic_brand_indigo);
                }
                if (viewHolder.airline.getText().toString().equalsIgnoreCase("Vistara")) {
                    viewHolder.icon.setBackgroundResource(R.drawable.ic_brand_vistara);
                }
                if (viewHolder.airline.getText().toString().equalsIgnoreCase("Jet Airways")) {
                    viewHolder.icon.setBackgroundResource(R.drawable.ic_brand_jet_airways);
                }
            }

        }

        @Override
        public int getItemCount() {
            return flightList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView airline;
            TextView flightnum;
            TextView depdate;
            TextView arrdate;
            TextView seatingclass;
            TextView flight_fare;
            ImageView icon;

            public ViewHolder(CardView v) {
                super(v);
                airline = (TextView) v.findViewById(R.id.airline);
                flightnum = (TextView) v.findViewById(R.id.flight_no);
                depdate = (TextView) v.findViewById(R.id.dept_time_flight);
                arrdate = (TextView) v.findViewById(R.id.arr_time_flight);
                seatingclass = (TextView) v.findViewById(R.id.seating_class);
                flight_fare = (TextView) v.findViewById(R.id.flight_fare);
                icon = (ImageView) v.findViewById(R.id.app_icon);
            }
        }
    }

}
