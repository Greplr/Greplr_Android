package com.greplr.subcategories.travel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.greplr.MainActivity;
import com.greplr.R;
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
                "28.6328",
                "77.2197",
                new Callback<List<Cab>>() {
                    @Override
                    public void success(List<Cab> cabs, Response response) {
                        Log.d("Greplr", "success" + response.getUrl() + response.getStatus());
                        cabList = cabs;
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

        ObservableScrollView osv = (ObservableScrollView) view.findViewById(R.id.travel_cab_scrollview);
        MaterialViewPagerHelper.registerScrollView(getActivity(), osv, null);

    }
}
