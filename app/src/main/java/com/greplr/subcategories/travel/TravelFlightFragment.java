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

package com.greplr.subcategories.travel;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.greplr.App;
import com.greplr.R;
import com.greplr.adapters.ErrorAdapter;
import com.greplr.adapters.LoaderAdapter;
import com.greplr.api.Api;
import com.greplr.common.ui.MaterialAutoCompleteTextView;
import com.greplr.common.ui.MaterialEditText;
import com.greplr.common.utils.ColorUtils;
import com.greplr.common.utils.DateTimeUtils;
import com.greplr.common.utils.DecimalUtils;
import com.greplr.common.utils.Utils;
import com.greplr.models.travel.Flight;
import com.greplr.subcategories.SubCategoryFragment;
import com.greplr.subcategories.UnderSubCategoryFragment;
import com.parse.ParseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
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
    private List<Flight> flightList;
    private RecyclerView mRecyclerView;
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
        return inflater.inflate(R.layout.fragment_travel_flight, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        airportList = new ArrayList<>();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_flight);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        try {
            JSONArray jsonArray = new JSONArray(Utils.loadJSONFromAsset(getActivity(), "airports.json"));
            for (int i = 0; i < jsonArray.length(); i++) {
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

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.select_dialog_item, airportList);

                CardView busCardView = (CardView) customDialog.findViewById(R.id.travel_flight_card_dialog);
                busCardView.setCardBackgroundColor(ColorUtils.getDarkerColor(getResources().getColor(R.color.travel_dialog_backgroundColor)));

                final MaterialAutoCompleteTextView origin = (MaterialAutoCompleteTextView) customDialog.findViewById(R.id.et_origin);
                final MaterialAutoCompleteTextView destination = (MaterialAutoCompleteTextView) customDialog.findViewById(R.id.et_destination);
                origin.setThreshold(1);
                destination.setThreshold(1);
                origin.setAdapter(adapter);
                destination.setAdapter(adapter);

                final MaterialEditText date = (MaterialEditText) customDialog.findViewById(R.id.et_date);
                DateTimeUtils.dateFormatter(date);
                final MaterialEditText adults = (MaterialEditText) customDialog.findViewById(R.id.et_adults);

                adults.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId,
                                                  KeyEvent event) {
                        boolean handled = false;
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            departureLocation = origin.getText().toString();
                            arrivalLocation = destination.getText().toString();
                            travelDate = date.getText().toString();
                            numOfAdults = adults.getText().toString();
                            if (!departureLocation.equalsIgnoreCase("") && !arrivalLocation.equals("") && !travelDate.equalsIgnoreCase("") && !numOfAdults.equalsIgnoreCase("")) {
                                departureLocation = origin.getText().toString().split("-")[1].trim();
                                arrivalLocation = destination.getText().toString().split("-")[1].trim();
                                customDialog.dismiss();
                                mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new LoaderAdapter()));
                                MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
                                fetchFlights();
                                in.hideSoftInputFromWindow(date
                                                .getApplicationWindowToken(),
                                        InputMethodManager.HIDE_NOT_ALWAYS);
                                handled = true;
                            } else {
                                if (departureLocation.equalsIgnoreCase(""))
                                    origin.setError("Enter Origin Location");
                                if (arrivalLocation.equalsIgnoreCase(""))
                                    destination.setError("Enter Destination Location");
                                if (travelDate.equalsIgnoreCase(""))
                                    date.setError("Enter Your Travel Date");
                                if (numOfAdults.equalsIgnoreCase(""))
                                    adults.setError("Enter The Number of adults");
                            }
                        }
                        return handled;
                    }
                });
                AppCompatButton buttonDone = (AppCompatButton) customDialog.findViewById(R.id.ok_button);
                buttonDone.setTextColor(ColorUtils.getDarkerColor(getResources().getColor(R.color.travel_dialog_backgroundColor)));
                buttonDone.setSupportBackgroundTintList(getResources().getColorStateList(android.R.color.white));
                buttonDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        departureLocation = origin.getText().toString();
                        arrivalLocation = destination.getText().toString();
                        travelDate = date.getText().toString();
                        numOfAdults = adults.getText().toString();
                        if (!departureLocation.equalsIgnoreCase("") && !arrivalLocation.equals("") && !travelDate.equalsIgnoreCase("") && !numOfAdults.equalsIgnoreCase("")) {
                            departureLocation = origin.getText().toString().split("-")[1].trim();
                            arrivalLocation = destination.getText().toString().split("-")[1].trim();
                            customDialog.dismiss();
                            mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new LoaderAdapter()));
                            MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
                            fetchFlights();
                        } else {
                            if (departureLocation.equalsIgnoreCase(""))
                                origin.setError("Enter Origin Location");
                            if (arrivalLocation.equalsIgnoreCase(""))
                                destination.setError("Enter Destination Location");
                            if (travelDate.equalsIgnoreCase(""))
                                date.setError("Enter Your Travel Date");
                            if (numOfAdults.equalsIgnoreCase(""))
                                adults.setError("Enter The Number of adults");
                        }
                    }
                });
                customDialog.show();
            }
        };

    }

    private void fetchFlights() {
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
                        mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new FlightAdapter()));
                        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
                        ((SubCategoryFragment) getParentFragment()).getSearchFab().attachToRecyclerView(mRecyclerView);
                        sendParseAnalytics("true");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(LOG_TAG, "failure" + error.getUrl() + error.getMessage());
                        mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new ErrorAdapter()));
                        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
                        sendParseAnalytics("false");
                    }
                }
        );
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getParentFragment() != null) {
            if (isVisibleToUser) {
                ((SubCategoryFragment) getParentFragment()).getSearchFab().setVisibility(View.VISIBLE);
                ((SubCategoryFragment) getParentFragment()).getSearchFab().setOnClickListener(onSearchFABListener);
            } else {
                ((SubCategoryFragment) getParentFragment()).getSearchFab().setVisibility(View.GONE);
            }
        }
    }

    private void sendParseAnalytics(String success) {
        Map<String, String> params = new HashMap<>();
        params.put("departure", departureLocation);
        params.put("arrival", arrivalLocation);
        params.put("travelDate", travelDate);
        params.put("numberOfAdults", numOfAdults);
        params.put("success", success);
        ParseAnalytics.trackEventInBackground("travel/flight/search", params);
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
            Calendar depdate = null;
            Calendar arrdate = null;
            try {
                depdate = DateTimeUtils.flightTimeToCalendar(flightList.get(i).getDepdate());
                arrdate = DateTimeUtils.flightTimeToCalendar(flightList.get(i).getArrdate());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (depdate != null) {
                viewHolder.depdate.setText(
                        DateTimeUtils.intToHrString(depdate.get(Calendar.HOUR_OF_DAY), true)
                                + ":"
                                + DateTimeUtils.intToMinString(depdate.get(Calendar.MINUTE)));
                viewHolder.depdate.setTypeface(null, Typeface.BOLD_ITALIC);
            } else {
                viewHolder.depdate.setText("");
            }

            if (arrdate != null) {
                viewHolder.arrdate.setText(
                        DateTimeUtils.intToHrString(arrdate.get(Calendar.HOUR_OF_DAY), true)
                                + ":"
                                + DateTimeUtils.intToMinString(arrdate.get(Calendar.MINUTE)));
                viewHolder.arrdate.setTypeface(null, Typeface.ITALIC);
            } else {
                viewHolder.arrdate.setText("");
            }

            viewHolder.airline.setText(flightList.get(i).getAirline());
            viewHolder.flightnum.setText("Flight No. : " + flightList.get(i).getFlightnum());
            if (flightList.get(i).getSeatingclass().toString().equalsIgnoreCase("e")) {
                viewHolder.seatingclass.setText("Travel Class : Economy");
            } else if (flightList.get(i).getSeatingclass().toString().equalsIgnoreCase("b")) {
                viewHolder.seatingclass.setText("Travel Class : Business");
            } else {
                viewHolder.seatingclass.setText("Travel Class : Economy");
            }
//            viewHolder.seatingclass.setText(flightList.get(i).getSeatingclass());
            viewHolder.flight_fare.setText("₹ " + DecimalUtils.adjustFlightFarePrecision(flightList.get(i).getFare()));

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
