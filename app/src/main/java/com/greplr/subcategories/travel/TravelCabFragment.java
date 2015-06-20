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
        View rootView = inflater.inflate(R.layout.travel_cab_fragment, container, false);

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
                    .inflate(R.layout.cab_cardview_list_item, viewGroup, false);
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
                viewHolder.icon.setBackgroundDrawable(getResources().getDrawable(R.drawable.ub_ic_launcher));
            } else if (viewHolder.provider.getText().toString().equalsIgnoreCase("taxiforsure")) {
                viewHolder.icon.setBackgroundDrawable(getResources().getDrawable(R.drawable.taxi_for_sure_icon));
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

            public ViewHolder(CardView v) {
                super(v);
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
