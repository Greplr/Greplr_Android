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

package com.greplr.subcategories.food;

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
import com.greplr.App;
import com.greplr.R;
import com.greplr.adapters.NumberedAdapter;
import com.greplr.api.Api;
import com.greplr.models.food.Order;
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
public class FoodOrderingFragment extends UnderSubCategoryFragment {

    public static final String LOG_TAG = "Greplr/Food/Order";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Order> orderList;

    public static FoodOrderingFragment newInstance() {
        return new FoodOrderingFragment();
    }

    @Override
    public int getFragmentIcon() {
        return R.drawable.cardicon_food_order;
    }

    @Override
    public String getPageTitle() {
        return "Order";
    }

    @Override
    public int getBackgroundResId() {
        return R.drawable.background_food_order;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Greplr", "FoodOrderingFragment onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_food_order, container, false);

        Api apiHandler = ((App) getActivity().getApplication()).getApiHandler();
        apiHandler.getOrderFood(
                String.valueOf(App.currentLatitude),
                String.valueOf(App.currentLongitude),
                new Callback<List<Order>>() {
                    @Override
                    public void success(List<Order> orders, Response response) {
                        Log.d(LOG_TAG, "success" + response.getUrl() + response.getStatus());
                        orderList = orders;
//                        updateOrders(orderList);
                        Log.d(LOG_TAG, orderList.get(0).getAddress() + "  " + orderList.get(0).getAddressLine2() + "  " + orderList.get(0).getCode() + "  " + orderList.get(0).getCustomerPhone() + "  " + orderList.get(0).getCustomLocationUrl() + "  " + orderList.get(0).getDescription() + "  " + orderList.get(0).getId() + "  " + orderList.get(0).getLatitude() + "  " + orderList.get(0).getLogo() + "  " + orderList.get(0).getLongitude() + "  " + orderList.get(0).getMetadata() + "  " + orderList.get(0).getMinimumDeliveryFee() + "  " + orderList.get(0).getMinimumDeliveryTime() + "  " + orderList.get(0).getMinimumOrderAmount() + "  " + orderList.get(0).getCustomLocationUrl() + "  " + orderList.get(0).getChain().getName());
                        Map<String, String> params = new HashMap<>();
                        params.put("lat", String.valueOf(App.currentLatitude));
                        params.put("lng", String.valueOf(App.currentLongitude));
                        params.put("success", "true");
                        ParseAnalytics.trackEventInBackground("food/orders/search", params);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(LOG_TAG, "failure" + error.getUrl() + error.getMessage());
                        Map<String, String> params = new HashMap<>();
                        params.put("lat", String.valueOf(App.currentLatitude));
                        params.put("lng", String.valueOf(App.currentLongitude));
                        params.put("success", "false");
                        ParseAnalytics.trackEventInBackground("food/orders/search", params);

                    }
                }
        );
        return rootView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(
                R.id.recyclerview_food_order);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerViewMaterialAdapter(new NumberedAdapter(10));
        mRecyclerView.setAdapter(mAdapter);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

    }

}
