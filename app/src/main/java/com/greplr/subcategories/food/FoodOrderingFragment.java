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

package com.greplr.subcategories.food;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.greplr.App;
import com.greplr.R;
import com.greplr.adapters.NumberedAdapter;
import com.greplr.api.Api;
import com.greplr.models.food.Order;
import com.greplr.subcategories.UnderSubCategoryFragment;
import com.parse.ParseAnalytics;
import com.squareup.picasso.Picasso;

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
    private List<Order.OrderRestaurants> restaurantList;
    private String area_id;

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
                new Callback<Order>() {
                    @Override
                    public void success(Order orders, Response response) {
                        Log.d(LOG_TAG, "success" + response.getUrl() + response.getStatus());
                        area_id = orders.getArea_id();
                        restaurantList = orders.getRestaurants();
                        updateOrders(restaurantList);
                        Log.d(LOG_TAG, restaurantList.get(0).getAddress()  + "  " + restaurantList.get(0).getCode()  + "  " + restaurantList.get(0).getDescription() + "  " + restaurantList.get(0).getId() + "  " + restaurantList.get(0).getLatitude() + "  " + restaurantList.get(0).getLogo() + "  " + restaurantList.get(0).getLongitude() + "  " + restaurantList.get(0).getMetadata() + "  " + restaurantList.get(0).getChain().getName());
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
    public void updateOrders(List<Order.OrderRestaurants> restaurantList){
        mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new OrderAdapter()));
    }

    public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            CardView v = (CardView) LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.cardview_order_item, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
            viewHolder.restaurantName.setText(restaurantList.get(i).getName());
            viewHolder.rating.setText(restaurantList.get(i).getRating() +"/5");
            viewHolder.minOrder.setText("Minimum Order : \u20b9"+restaurantList.get(i).getMinimum_order_amount());
            viewHolder.address.setText(restaurantList.get(i).getAddress());
            viewHolder.location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<lat>,<long>?q=" + restaurantList.get(i).getLatitude() + "," + restaurantList.get(i).getLongitude() + "(" + restaurantList.get(i).getName() + ")"));
                    startActivity(intent);
                }
            });
            Picasso.with(getActivity()).load(restaurantList.get(i).getLogo()).fit().centerCrop().into(viewHolder.logo);
            viewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PackageManager pm = getActivity().getPackageManager();
                    try
                    {
                        pm.getPackageInfo("com.global.foodpanda.android", PackageManager.GET_ACTIVITIES);
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(
                                "foodpanda://?&c=IN&s=c&a="+area_id+"&v="+restaurantList.get(i).getId())));
                    }
                    catch (PackageManager.NameNotFoundException e)
                    {
                        // No Foodpanda app!
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setData(Uri.parse("market://details?id=" + "com.global.foodpanda.android"));
                        getActivity().startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return restaurantList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView restaurantName;
            TextView rating;
            TextView address;
            ImageButton location;
            TextView minOrder;
            ImageView logo;
            View view;

            public ViewHolder(CardView v) {
                super(v);
                view = v;
                restaurantName = (TextView) v.findViewById(R.id.restaurant_order_name);
                rating = (TextView) v.findViewById(R.id.restaurant_order_rating);
                address = (TextView) v.findViewById(R.id.restaurant_order_address);
                location = (ImageButton) v.findViewById(R.id.location_restaurant_order);
                minOrder = (TextView) v.findViewById(R.id.restaurant_order_min_order);
                logo = (ImageView) v.findViewById(R.id.logo_restaurant_order);
            }
        }
    }

}
