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

package com.greplr.subcategories.shopping;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.greplr.App;
import com.greplr.R;
import com.greplr.adapters.ErrorAdapter;
import com.greplr.adapters.LoaderAdapter;
import com.greplr.api.Api;
import com.greplr.models.shopping.Offers;
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
 * Created by raghav on 01/07/15.
 */
public class ShoppingOffersFragment extends UnderSubCategoryFragment {

    public static final String LOG_TAG = "Greplr/Shopping/Offers";

    private List<Offers> offerList;
    private RecyclerView mRecyclerView;

    public static ShoppingOffersFragment newInstance() {
        return new ShoppingOffersFragment();
    }

    @Override
    public int getFragmentIcon() {
        return R.drawable.cardicon_shopping_offers;
    }

    @Override
    public String getPageTitle() {
        return "Offers";
    }

    @Override
    public int getBackgroundResId() {
        return R.drawable.background_shopping_offers;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(LOG_TAG, "ShoppingOffersFragment onCreateView");
        return inflater.inflate(R.layout.fragment_shopping_offer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_offers);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new LoaderAdapter()));
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

        Api apiHandler = ((App) getActivity().getApplication()).getApiHandler();
        apiHandler.getShoppingOffers(
                new Callback<List<Offers>>() {
                    @Override
                    public void success(List<Offers> offers, Response response) {
                        offerList = offers;
                        mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new ShoppingAdapter()));
                        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
//                        Log.d(LOG_TAG, offerList.get(0).getUrl() + "  " + offerList.get(0).getAvailability() + "  " + offerList.get(0).getDescription() + "  " + offerList.get(0).getTitle() + "  " + offerList.get(0).getImageUrls().get(0).getUrl());
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

    private void sendParseAnalytics(String success) {
        Map<String, String> params = new HashMap<>();
        params.put("success", success);
        ParseAnalytics.trackEventInBackground("shopping/offers", params);
    }

    public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            CardView v = (CardView) LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.cardview_shopping_offer_list_item, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
            viewHolder.offerName.setText(offerList.get(i).getTitle());
            viewHolder.availability.setText(offerList.get(i).getAvailability());
            viewHolder.offerDescription.setText(offerList.get(i).getDescription());

//            if(Boolean.valueOf(searchList.get(i).getCodAvailable()))
//                viewHolder.cod.setText("COD Available : Yes");
//            else
//                viewHolder.cod.setText("COD Available : No");

            Picasso.with(getActivity()).load(offerList.get(i).getImageUrls().get(0).getUrl()).fit().centerCrop().into(viewHolder.icon);
            viewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String, String> params = new HashMap<>();
                    params.put("success", "true");
                    params.put("offer name clicked", offerList.get(i).getTitle());
                    ParseAnalytics.trackEventInBackground("shopping/search clicked", params);
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(offerList.get(i).getUrl()));
                    startActivity(browserIntent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return offerList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView offerName;
            TextView availability;
            TextView offerDescription;
            ImageView icon;
            View view;

            public ViewHolder(CardView v) {
                super(v);
                view = v;
                offerName = (TextView) v.findViewById(R.id.offer_name);
                availability = (TextView) v.findViewById(R.id.availability);
                offerDescription = (TextView) v.findViewById(R.id.offer_description);
                icon = (ImageView) v.findViewById(R.id.app_icon);
            }
        }
    }
}
