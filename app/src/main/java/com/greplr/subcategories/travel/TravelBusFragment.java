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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.greplr.App;
import com.greplr.MainActivity;
import com.greplr.R;
import com.greplr.adapters.NumberedAdapter;
import com.greplr.api.Api;
import com.greplr.models.travel.Bus;
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
public class TravelBusFragment extends UnderSubCategoryFragment {

    public static final String LOG_TAG = "Greplr/Travel/Bus";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private String arrivalLocation, departureLocation, travelDate;
    private List<Bus> busList;

    public static TravelBusFragment newInstance() {
        return new TravelBusFragment();
    }

    @Override
    public int getFragmentIcon() {
        return R.drawable.cardicon_travel_bus;
    }

    @Override
    public String getPageTitle() {
        return "Bus";
    }

    @Override
    public int getBackgroundResId() {
        return R.drawable.background_travel_bus;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(LOG_TAG, "TravelBusFragment onCreateView");
        departureLocation = "Delhi";
        arrivalLocation = "Amritsar";
        travelDate = "20150710";
        View rootView = inflater.inflate(R.layout.fragment_travel_bus, container, false);

        Api apiHandler = ((App) getActivity().getApplication()).getApiHandler();
        apiHandler.getTravelBus(
                departureLocation,
                arrivalLocation,
                travelDate,
                new Callback<List<Bus>>() {
                    @Override
                    public void success(List<Bus> buses, Response response) {
                        Log.d(LOG_TAG, "success" + response.getUrl() + response.getStatus());
                        busList = buses;
                        updateBus(busList);
//                        Map<String, String> params = new HashMap<>();
//                        params.put("departure", departureLocation);
//                        params.put("arrival", arrivalLocation);
//                        params.put("travelDate", travelDate);
//                        params.put("success", "true");
//                        ParseAnalytics.trackEventInBackground("travel/bus/search", params);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(LOG_TAG, "failure" + error.getUrl() + error.getMessage());
//                        Map<String, String> params = new HashMap<>();
//                        params.put("departure", departureLocation);
//                        params.put("arrival", arrivalLocation);
//                        params.put("travelDate", travelDate);
//                        params.put("success", "false");
//                        ParseAnalytics.trackEventInBackground("travel/bus/search", params);
                    }
                }
        );


        return rootView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(
                R.id.recyclerview_bus);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerViewMaterialAdapter(new NumberedAdapter(0));
        mRecyclerView.setAdapter(mAdapter);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

    }

    public void updateBus(List<Bus> cabs) {
        mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new BusAdapter()));
    }

    public class BusAdapter extends RecyclerView.Adapter<BusAdapter.ViewHolder> {

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
                    Button okButton = (Button) v.findViewById(R.id.ok_button);
                    final EditText orig = (EditText) v.findViewById(R.id.et_origin);
                    final EditText dest = (EditText) v.findViewById(R.id.et_destination);
                    final EditText date = (EditText) v.findViewById(R.id.et_date);
                    okButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            departureLocation = orig.getText().toString();
                            arrivalLocation = dest.getText().toString();
                            travelDate = date.getText().toString();
                            Api apiHandler = ((App) getActivity().getApplication()).getApiHandler();
                            apiHandler.getTravelBus(
                                    departureLocation,
                                    arrivalLocation,
                                    travelDate,
                                    new Callback<List<Bus>>() {
                                        @Override
                                        public void success(List<Bus> buses, Response response) {
                                            Log.d(LOG_TAG, "success" + response.getUrl() + response.getStatus());
                                            busList = buses;
                                            updateBus(busList);
                                            Map<String, String> params = new HashMap<>();
                                            params.put("departure", departureLocation);
                                            params.put("arrival", arrivalLocation);
                                            params.put("travelDate", travelDate);
                                            params.put("success", "true");
                                            ParseAnalytics.trackEventInBackground("travel/bus/search", params);
                                        }

                                        @Override
                                        public void failure(RetrofitError error) {
                                            Log.d(LOG_TAG, "failure" + error.getUrl() + error.getMessage());
                                            Map<String, String> params = new HashMap<>();
                                            params.put("departure", departureLocation);
                                            params.put("arrival", arrivalLocation);
                                            params.put("travelDate", travelDate);
                                            params.put("success", "false");
                                            ParseAnalytics.trackEventInBackground("travel/bus/search", params);
                                        }
                                    }
                            );

                        }
                    });
                    return new ViewHolder(v);
                }
                case TYPE_CELL: {
                    v = (CardView) LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.cardview_bus_list_item, viewGroup, false);
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
            if (getItemViewType(i) == TYPE_CELL) {
                viewHolder.travelagency.setText(busList.get(i).getTravelagency());
                viewHolder.bustype.setText(busList.get(i).getBustype());
                viewHolder.seat.setText(busList.get(i).getSeat());
                viewHolder.depdate.setText(busList.get(i).getDepdate());
                viewHolder.arrdate.setText(busList.get(i).getArrdate());
                viewHolder.fare.setText(busList.get(i).getFare());
            }
        }

        @Override
        public int getItemCount() {
            return busList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView travelagency;
            TextView bustype;
            TextView seat;
            TextView depdate;
            TextView arrdate;
            TextView fare;
            TextView condition;

            public ViewHolder(CardView v) {
                super(v);
                travelagency = (TextView) v.findViewById(R.id.travel_agency_name);
                bustype = (TextView) v.findViewById(R.id.bus_type);
                seat = (TextView) v.findViewById(R.id.seat_type);
                depdate = (TextView) v.findViewById(R.id.dept_time);
                arrdate = (TextView) v.findViewById(R.id.arr_time);
                fare = (TextView) v.findViewById(R.id.fare);
            }
        }
    }

}
