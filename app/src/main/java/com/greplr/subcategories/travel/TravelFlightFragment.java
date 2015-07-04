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
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.greplr.App;
import com.greplr.R;
import com.greplr.adapters.NumberedAdapter;
import com.greplr.api.Api;
import com.greplr.common.utils.Utils;
import com.greplr.models.travel.Flight;
import com.greplr.subcategories.SubCategoryFragment;
import com.greplr.subcategories.UnderSubCategoryFragment;
import com.parse.ParseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by championswimmer on 15/6/15.
 */
public class TravelFlightFragment extends UnderSubCategoryFragment {

    public static final String LOG_TAG = "Greplr/Travel/Flight";
    Api apiHandler;
    private List<Flight> flightList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private String arrivalLocation, departureLocation, travelDate, numOfAdults;
    private View.OnClickListener onSearchFABListener;
    private ArrayList<String> airportList;

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
        Log.d(LOG_TAG, "TravelFlightFragment onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_travel_flight, container, false);
        airportList = new ArrayList<>();

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

        try {
            JSONArray jsonArray = new JSONArray(Utils.loadJSONFromAsset(getActivity(), "airports.json"));
            for(int i=0;i<jsonArray.length();i++){
                airportList.add(jsonArray.getJSONObject(i).getString("city") + "," +
                        jsonArray.getJSONObject(i).getString("country") + " - " +
                        jsonArray.getJSONObject(i).getString("code"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        onSearchFABListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog customDialog = new Dialog(getActivity(), R.style.AppTheme_SearchDialog);
                customDialog.setContentView(R.layout.searchdialog_travel_flight);
                customDialog.setTitle("Enter Your Details");
                customDialog.setCancelable(true);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>
                        (getActivity(), android.R.layout.select_dialog_item, airportList);
                final AutoCompleteTextView origin = (AutoCompleteTextView) customDialog.findViewById(R.id.et_origin);
                final AutoCompleteTextView destination = (AutoCompleteTextView) customDialog.findViewById(R.id.et_destination);
                origin.setThreshold(1);
                destination.setThreshold(1);
                origin.setAdapter(adapter);
                destination.setAdapter(adapter);
                final EditText date = (EditText) customDialog.findViewById(R.id.et_date);
                Utils.dateFormater(date);
                final EditText adults = (EditText) customDialog.findViewById(R.id.et_adults);
                AppCompatButton buttonDone = (AppCompatButton) customDialog.findViewById(R.id.ok_button);
                buttonDone.setSupportBackgroundTintList(getResources().getColorStateList(R.color.travel_cardColor));
                buttonDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(LOG_TAG, origin.getText().toString().split("-")[1].trim());
                        Log.d(LOG_TAG, destination.getText().toString().split("-")[1].trim());
                        departureLocation = origin.getText().toString().split("-")[1].trim();
                        arrivalLocation = destination.getText().toString().split("-")[1].trim();
                        travelDate = date.getText().toString();
                        numOfAdults = adults.getText().toString();
                        if (!departureLocation.equalsIgnoreCase("") && !arrivalLocation.equals("") && !travelDate.equalsIgnoreCase("") && !numOfAdults.equalsIgnoreCase("")) {
                            customDialog.dismiss();
                            Api apiHandler = ((App) getActivity().getApplication()).getApiHandler();
                            apiHandler.getTravelFlights(
                                    departureLocation,
                                    arrivalLocation,
                                    travelDate,
                                    Integer.valueOf(numOfAdults),
                                    new Callback<List<Flight>>() {
                                        @Override
                                        public void success(List<Flight> flights, Response response) {
                                            Log.d(LOG_TAG, "success" + response.getUrl() + response.getStatus());
                                            flightList = flights;
                                            updateFlight(flightList);
                                            Map<String, String> params = new HashMap<>();
                                            params.put("departure", departureLocation);
                                            params.put("arrival", arrivalLocation);
                                            params.put("travelDate", travelDate);
                                            params.put("numberOfAdults", numOfAdults);
                                            params.put("success", "true");
                                            ParseAnalytics.trackEventInBackground("travel/flight/search", params);
                                        }

                                        @Override
                                        public void failure(RetrofitError error) {
                                            Log.d(LOG_TAG, "failure" + error.getUrl() + error.getMessage());
                                            Map<String, String> params = new HashMap<>();
                                            params.put("departure", departureLocation);
                                            params.put("arrival", arrivalLocation);
                                            params.put("travelDate", travelDate);
                                            params.put("numberOfAdults", numOfAdults);
                                            params.put("success", "false");
                                            ParseAnalytics.trackEventInBackground("travel/flight/search", params);
                                        }
                                    }
                            );
                        } else {
                            if(departureLocation.equalsIgnoreCase(""))
                                origin.setError("Enter Origin Location");
                            if(arrivalLocation.equalsIgnoreCase(""))
                                destination.setError("Enter Destination Location");
                            if(travelDate.equalsIgnoreCase(""))
                                date.setError("Enter Your Travel Date");
                            if(numOfAdults.equalsIgnoreCase(""))
                                adults.setError("Enter The Number of adults");
                        }
                    }
                });
                customDialog.show();
            }
        };

    }

    public void updateFlight(List<Flight> flights) {
        mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new FlightAdapter()));
        ((SubCategoryFragment) getParentFragment()).getSearchFab().attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getParentFragment()!=null) {
            if (isVisibleToUser) {
                ((SubCategoryFragment) getParentFragment()).getSearchFab().setVisibility(View.VISIBLE);
                ((SubCategoryFragment) getParentFragment()).getSearchFab().setOnClickListener(onSearchFABListener);
            } else {
                ((SubCategoryFragment) getParentFragment()).getSearchFab().setVisibility(View.GONE);
            }
        }
    }

    public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            CardView v = (CardView) LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.cardview_flight_list_item, viewGroup, false);
            return new ViewHolder(v);

        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

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
