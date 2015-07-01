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
import com.greplr.MainActivity;
import com.greplr.R;
import com.greplr.adapters.NumberedAdapter;
import com.greplr.api.Api;
import com.greplr.models.shopping.search.Search;
import com.greplr.subcategories.UnderSubCategoryFragment;
import com.parse.ParseAnalytics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
        Log.d(LOG_TAG, "ShoppingSearchFragment onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_travel_cab, container, false);

        Api apiHandler = ((MainActivity) getActivity()).getApiHandler();
        apiHandler.getShoppingResult(
                "Laptop",
                new Callback<List<Search>>() {
                    @Override
                    public void success(List<Search> search, Response response) {

                        searchList = search;
                        Log.d(LOG_TAG, search.get(0).getOffset() + "  " + search.get(0).getProductBaseInfo().getProductAttributes() + "  " + search.get(0).getProductBaseInfo().getProductIdentifier().getCategoryPaths() + "  " + search.get(0).getProductBaseInfo().getProductIdentifier().getProductId() + "  " + search.get(0).getProductShippingBaseInfo().getShippingOptions() + "  " + search.get(0).getProductBaseInfo().getProductAttributes().getCashBack() + "  " + search.get(0).getProductBaseInfo().getProductAttributes().getCodAvailable() + "  " + search.get(0).getProductBaseInfo().getProductAttributes().getColor() + "  " + search.get(0).getProductBaseInfo().getProductAttributes().getColorVariants() + "  " + search.get(0).getProductBaseInfo().getProductAttributes().getDiscountPercentage() + "  " + search.get(0).getProductBaseInfo().getProductAttributes().getEmiAvailable() + "  " + search.get(0).getProductBaseInfo().getProductAttributes().getImageUrls().get_100x100() + "  " + search.get(0).getProductBaseInfo().getProductAttributes().getImageUrls().get_1100x1100() + "  " + search.get(0).getProductBaseInfo().getProductAttributes().getImageUrls().get_125x125() + "  " + search.get(0).getProductBaseInfo().getProductAttributes().getImageUrls().get_200x200() + "  " + search.get(0).getProductBaseInfo().getProductAttributes().getImageUrls().get_275x275() + "  " + search.get(0).getProductBaseInfo().getProductAttributes().getImageUrls().get_400x400() + "  " + search.get(0).getProductBaseInfo().getProductAttributes().getImageUrls().get_40x40() + "  " + search.get(0).getProductBaseInfo().getProductAttributes().getImageUrls().get_75x75() + "  " + search.get(0).getProductBaseInfo().getProductAttributes().getImageUrls().getUnknown() + "  " + search.get(0).getProductBaseInfo().getProductAttributes().getInStock() + "  " + search.get(0).getProductBaseInfo().getProductAttributes().getMaximumRetailPrice().getAmount() + "  " + search.get(0).getProductBaseInfo().getProductAttributes().getMaximumRetailPrice().getCurrency() + "  " + search.get(0).getProductBaseInfo().getProductAttributes().getOffers().get(0) + "  " + search.get(0).getProductBaseInfo().getProductAttributes().getProductBrand() + "  " + search.get(0).getProductBaseInfo().getProductAttributes().getProductDescription() + "  " + search.get(0).getProductBaseInfo().getProductAttributes().getProductUrl() + "  " + search.get(0).getProductBaseInfo().getProductAttributes().getSellingPrice().getAmount() + "  " + search.get(0).getProductBaseInfo().getProductAttributes().getSellingPrice().getCurrency() + "  " + search.get(0).getProductBaseInfo().getProductAttributes().getTitle() + "  " + search.get(0).getProductBaseInfo().getProductAttributes().getSizeVariants() + "  " + search.get(0).getProductBaseInfo().getProductAttributes().getSize() + "  " + search.get(0).getProductBaseInfo().getProductAttributes().getSizeUnit());
//                        updateOffers(offerList);
                        //Parse Analytics
                        Map<String, String> params = new HashMap<>();
                        params.put("success", "true");
                        ParseAnalytics.trackEventInBackground("shopping/search", params);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(LOG_TAG, "failure" + error.getUrl() + error.getMessage());
                        //Parse Analytics
                        Map<String, String> params = new HashMap<>();
                        params.put("success", "false");
                        ParseAnalytics.trackEventInBackground("shopping/search", params);
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

}
