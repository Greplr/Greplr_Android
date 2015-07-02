package com.greplr.subcategories.shopping;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.greplr.adapters.NumberedAdapter;
import com.greplr.api.Api;
import com.greplr.models.shopping.offers.Offers;
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
    private RecyclerView.Adapter mAdapter;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    public static ShoppingOffersFragment newInstance() {
        return new ShoppingOffersFragment();
    }

    @Override
    public int getFragmentIcon() {
        return R.drawable.cardicon_travel_taxi;
    }

    @Override
    public String getPageTitle() {
        return "Offers";
    }

    @Override
    public int getBackgroundResId() {
        return R.drawable.background_travel_cab;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(LOG_TAG, "ShoppingOffersFragment onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_shopping_offer, container, false);

        Api apiHandler = ((App) getActivity().getApplication()).getApiHandler();
        apiHandler.getShoppingOffers(
                new Callback<List<Offers>>() {
                    @Override
                    public void success(List<Offers> offers, Response response) {
                        offerList = offers;
                        updateOffers(offerList);
                        //Parse Analytics
                        Map<String, String> params = new HashMap<>();
                        Log.d(LOG_TAG, offerList.get(0).getUrl() + "  " + offerList.get(0).getAvailability()+ "  " + offerList.get(0).getDescription() + "  " + offerList.get(0).getTitle() + "  " + offerList.get(0).getImageUrls().get(0).getUrl());
                        params.put("success", "true");
                        ParseAnalytics.trackEventInBackground("shopping/offers", params);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(LOG_TAG, "failure" + error.getUrl() + error.getMessage());
                        //Parse Analytics
                        Map<String, String> params = new HashMap<>();
                        params.put("success", "false");
                        ParseAnalytics.trackEventInBackground("shopping/offers", params);
                    }
                }
        );

        return rootView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(
                R.id.recyclerview_shopping_offer);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new RecyclerViewMaterialAdapter(new NumberedAdapter(0));
        mRecyclerView.setAdapter(mAdapter);

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

    }
    public void updateOffers(List<Offers> offersList){
        mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new ShoppingAdapter()));
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
