package com.greplr.subcategories.shopping;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.greplr.R;
import com.greplr.adapters.NumberedAdapter;
import com.greplr.models.shopping.Search;
import com.greplr.subcategories.UnderSubCategoryFragment;

import java.util.List;

/**
 * Created by raghav on 01/07/15.
 */
public class ShoppingSearchFragment extends UnderSubCategoryFragment {
    public static final String LOG_TAG = "Greplr/Shopping/Search";

    private List<Search> searchList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    public static ShoppingSearchFragment newInstance() {
        return new ShoppingSearchFragment();
    }

    @Override
    public int getFragmentIcon() {
        return R.drawable.cardicon_travel_taxi;
    }

    @Override
    public String getPageTitle() {
        return "Search";
    }

    @Override
    public int getBackgroundResId() {
        return R.drawable.background_travel_cab;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(LOG_TAG, "ShoppingOffersFragment onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_travel_cab, container, false);

//        Api apiHandler = ((MainActivity) getActivity()).getApiHandler();
//        apiHandler.getShoppingOffers(
//                new Callback<List<Offers>>() {
//                    @Override
//                    public void success(List<Offers> offers, Response response) {
//
////                        updateOffers(offerList);
//                        //Parse Analytics
//                        Map<String, String> params = new HashMap<>();
//
//                        params.put("success", "true");
//                        ParseAnalytics.trackEventInBackground("shopping/offers", params);
//                    }
//
//                    @Override
//                    public void failure(RetrofitError error) {
//                        Log.d(LOG_TAG, "failure" + error.getUrl() + error.getMessage());
//                        //Parse Analytics
//                        Map<String, String> params = new HashMap<>();
//                        params.put("success", "false");
//                        ParseAnalytics.trackEventInBackground("shopping/offers", params);
//                    }
//                }
//        );

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

}
