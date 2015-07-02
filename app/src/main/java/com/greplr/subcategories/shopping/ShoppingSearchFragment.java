package com.greplr.subcategories.shopping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
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
import com.greplr.MainActivity;
import com.greplr.R;
import com.greplr.adapters.NumberedAdapter;
import com.greplr.api.Api;
import com.greplr.models.shopping.search.Search;
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
        return R.drawable.cardicon_shopping_search;
    }

    @Override
    public String getPageTitle() {
        return "Search";
    }

    @Override
    public int getBackgroundResId() {
        return R.drawable.background_shopping_search;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(LOG_TAG, "ShoppingSearchFragment onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_shopping_search, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().show();

        Api apiHandler = ((App) getActivity().getApplication()).getApiHandler();
        apiHandler.getShoppingResult(
                "Laptop",
                new Callback<List<Search>>() {
                    @Override
                    public void success(List<Search> search, Response response) {
                        searchList = search;
                        Log.d(LOG_TAG, search.get(0).getCashBack() + "  " + search.get(0).getTitle() + "  " + search.get(0).getCashBack() + "  " + search.get(0).getCodAvailable() + "  " + search.get(0).getColor() + "  " + search.get(0).getEmiAvailable());
                        updateShoppingSearch(searchList);
                        //Parse Analytics
                        Map<String, String> params = new HashMap<>();
                        params.put("success", "true");
                        params.put("product name", "Laptop");
                        ParseAnalytics.trackEventInBackground("shopping/search", params);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(LOG_TAG, "failure" + error.getUrl() + error.getMessage());
                        //Parse Analytics
                        Map<String, String> params = new HashMap<>();
                        params.put("success", "false");
                        params.put("product name", "Laptop");
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
                R.id.recyclerview_shoppingsearch);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new RecyclerViewMaterialAdapter(new NumberedAdapter(0));
        mRecyclerView.setAdapter(mAdapter);

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

    }

    public void updateShoppingSearch(List<Search> searchList){
        mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new ShoppingAdapter()));
    }

    public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            CardView v = (CardView) LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.cardview_shopping_search_list_item, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
            viewHolder.productName.setText(searchList.get(i).getTitle());
            viewHolder.minPrice.setText("\u20b9" + searchList.get(i).getSellingPrice().getAmount());
            viewHolder.mrp.setText("\u20b9 " + searchList.get(i).getMaximumRetailPrice().getAmount());
            viewHolder.mrp.setPaintFlags(viewHolder.mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            if(searchList.get(i).getProductDescription() == null)
                viewHolder.productDescription.setText(searchList.get(i).getColor());
            else
                viewHolder.productDescription.setText(searchList.get(i).getProductDescription());

            if(Boolean.valueOf(searchList.get(i).getCodAvailable()))
                viewHolder.cod.setText("COD Available : Yes");
            else
                viewHolder.cod.setText("COD Available : No");

            Picasso.with(getActivity()).load(searchList.get(i).getImageUrls().get_400x400()).fit().centerCrop().into(viewHolder.icon);
            Log.d("IMAGE URL : ", searchList.get(i).getImageUrls().get_400x400());

            viewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String, String> params = new HashMap<>();
                    params.put("success", "true");
                    params.put("product name clicked", searchList.get(i).getTitle());
                    ParseAnalytics.trackEventInBackground("shopping/search clicked", params);
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(searchList.get(i).getProductUrl()));
                    startActivity(browserIntent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return searchList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView productName;
            TextView minPrice;
            TextView cod;
            TextView mrp;
            TextView productDescription;
            ImageView icon;
            View view;

            public ViewHolder(CardView v) {
                super(v);
                view = v;
                productName = (TextView) v.findViewById(R.id.product_name);
                minPrice = (TextView) v.findViewById(R.id.min_price);
                cod = (TextView) v.findViewById(R.id.cod);
                mrp = (TextView) v.findViewById(R.id.mrp);
                productDescription = (TextView) v.findViewById(R.id.product_description);
                icon = (ImageView) v.findViewById(R.id.app_icon);
            }
        }
    }



}
