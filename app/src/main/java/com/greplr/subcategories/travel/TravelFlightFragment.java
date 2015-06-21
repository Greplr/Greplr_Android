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


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            CardView v = (CardView) LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.cardview_flight_list_item, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
            viewHolder.airline.setText(flightList.get(i).getAirline());
            viewHolder.flightnum.setText(flightList.get(i).getFlightnum());
            viewHolder.depdate.setText(flightList.get(i).getDepdate());
            viewHolder.arrdate.setText(flightList.get(i).getArrdate());
            viewHolder.seatingclass.setText(flightList.get(i).getSeatingclass());
            viewHolder.flight_fare.setText(flightList.get(i).getFare());

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
