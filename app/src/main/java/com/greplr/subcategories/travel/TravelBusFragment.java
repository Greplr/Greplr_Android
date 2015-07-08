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

import android.app.Dialog;
import android.graphics.Typeface;
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
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.greplr.App;
import com.greplr.R;
import com.greplr.adapters.NumberedAdapter;
import com.greplr.api.Api;
import com.greplr.common.utils.DateTimeUtils;
import com.greplr.common.utils.Utils;
import com.greplr.models.travel.Bus;
import com.greplr.subcategories.SubCategoryFragment;
import com.greplr.subcategories.UnderSubCategoryFragment;
import com.parse.ParseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    private View.OnClickListener onSearchFABListener;
    private ArrayList<String> cityList;

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
        View rootView = inflater.inflate(R.layout.fragment_travel_bus, container, false);
        cityList = new ArrayList<>();

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

        try {
            JSONArray jsonArray = new JSONArray(Utils.loadJSONFromAsset(getActivity(), "cities.json"));
            for(int i=0;i<jsonArray.length();i++){
                cityList.add(jsonArray.getJSONObject(i).getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Attach search dialog to search FAB
        onSearchFABListener = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                final Dialog customDialog = new Dialog(getActivity(), R.style.AppTheme_SearchDialog);
                customDialog.setContentView(R.layout.searchdialog_travel_bus);
                customDialog.setTitle("Enter Your Details");
                customDialog.setCancelable(true);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>
                        (getActivity(), android.R.layout.select_dialog_item, cityList);
                final AutoCompleteTextView origin = (AutoCompleteTextView) customDialog.findViewById(R.id.et_origin);
                final AutoCompleteTextView destination = (AutoCompleteTextView) customDialog.findViewById(R.id.et_destination);
                origin.setThreshold(1);
                destination.setThreshold(1);
                origin.setAdapter(adapter);
                destination.setAdapter(adapter);

                final EditText date = (EditText) customDialog.findViewById(R.id.et_date);
                Utils.dateFormatter(date);
                AppCompatButton buttonDone = (AppCompatButton) customDialog.findViewById(R.id.ok_button);
                buttonDone.setSupportBackgroundTintList(getResources().getColorStateList(R.color.travel_color_primary));
                buttonDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        departureLocation = origin.getText().toString();
                        arrivalLocation = destination.getText().toString();
                        travelDate = date.getText().toString();
                        if (!departureLocation.equalsIgnoreCase("") && !arrivalLocation.equals("") && !travelDate.equalsIgnoreCase("")) {
                            customDialog.dismiss();
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
                        } else {
                            if(departureLocation.equalsIgnoreCase(""))
                                origin.setError("Enter Origin Location");
                            if(arrivalLocation.equalsIgnoreCase(""))
                                destination.setError("Enter Destination Location");
                            if(travelDate.equalsIgnoreCase(""))
                                date.setError("Enter Your Travel Date");
                        }
                    }
                });
                customDialog.show();
            }
        };

    }

    public void updateBus(List<Bus> bus) {
        mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new BusAdapter()));
        ((SubCategoryFragment) getParentFragment()).getSearchFab().attachToRecyclerView(mRecyclerView);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getParentFragment()!=null) {
            if (isVisibleToUser) {
                ((SubCategoryFragment) getParentFragment()).getSearchFab().setVisibility(View.VISIBLE);
                ((SubCategoryFragment) getParentFragment()).getSearchFab().setOnClickListener(onSearchFABListener);
                ((SubCategoryFragment) getParentFragment()).getSearchFab().setColorNormal(getActivity().getResources().getColor(R.color.travel_color_dark));
            } else {
                ((SubCategoryFragment) getParentFragment()).getSearchFab().setVisibility(View.GONE);
            }
        }
    }

    public class BusAdapter extends RecyclerView.Adapter<BusAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            CardView v = (CardView) LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.cardview_bus_list_item, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
            Calendar depdate = Calendar.getInstance();
            Calendar arrdate = Calendar.getInstance();
            try {
                depdate = DateTimeUtils.busTimeToCalendar(busList.get(i).getDepdate());
                arrdate = DateTimeUtils.busTimeToCalendar(busList.get(i).getArrdate());
//                Log.d("time", String.valueOf(arrHour));
//                Log.d("time", String.valueOf(arrMin));
            } catch (ParseException e) {
                e.printStackTrace();
            }


            viewHolder.travelagency.setText(busList.get(i).getTravelagency());
            viewHolder.bustype.setText(busList.get(i).getBustype());
//            viewHolder.seat.setText(busList.get(i).getSeat());
            viewHolder.depdate.setText(
                            DateTimeUtils.intToHrString(depdate.get(Calendar.HOUR), true)
                            + ":"
                            + DateTimeUtils.intToMinString(depdate.get(Calendar.MINUTE)));
            viewHolder.depdate.setTypeface(null, Typeface.BOLD_ITALIC);
            viewHolder.arrdate.setText(
                    DateTimeUtils.intToHrString(arrdate.get(Calendar.HOUR), true)
                            + ":"
                            + DateTimeUtils.intToMinString(arrdate.get(Calendar.MINUTE)));
            viewHolder.arrdate.setTypeface(null, Typeface.ITALIC);
            viewHolder.fare.setText(busList.get(i).getFare());
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
//                seat = (TextView) v.findViewById(R.id.seat_type);
                depdate = (TextView) v.findViewById(R.id.dept_time);
                arrdate = (TextView) v.findViewById(R.id.arr_time);
                fare = (TextView) v.findViewById(R.id.fare);
            }
        }
    }

}
