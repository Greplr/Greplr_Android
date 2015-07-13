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

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.greplr.App;
import com.greplr.R;
import com.greplr.adapters.ErrorAdapter;
import com.greplr.adapters.LoaderAdapter;
import com.greplr.api.Api;
import com.greplr.common.ui.MaterialEditText;
import com.greplr.common.utils.ColorUtils;
import com.greplr.models.shopping.Search;
import com.greplr.subcategories.SubCategoryFragment;
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
    private View.OnClickListener onSearchFABListener;
    private String productName;

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
        return inflater.inflate(R.layout.fragment_shopping_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_shoppingsearch);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        onSearchFABListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog customDialog = new Dialog(getActivity(), R.style.AppTheme_SearchDialog);
                customDialog.setContentView(R.layout.searchdialog_shopping_search);
                customDialog.setTitle("What are you looking for?");
                customDialog.setCancelable(true);

                CardView shoppingCardView = (CardView) customDialog.findViewById(R.id.shopping_search_card_dialog);
                shoppingCardView.setCardBackgroundColor((ColorUtils.getDarkerColor(getResources().getColor(R.color.shopping_color_dark))));

                final MaterialEditText product = (MaterialEditText) customDialog.findViewById(R.id.et_product_name);
                AppCompatButton buttonDone = (AppCompatButton) customDialog.findViewById(R.id.ok_button);
                buttonDone.setSupportBackgroundTintList(getResources().getColorStateList(android.R.color.white));
                product.setOnEditorActionListener(new TextView.OnEditorActionListener() {

                    @Override
                    public boolean onEditorAction(TextView v, int actionId,
                                                  KeyEvent event) {
                        boolean handled = false;
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            productName = product.getText().toString();
                            if (!productName.equalsIgnoreCase("")) {
                                customDialog.dismiss();
                                mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new LoaderAdapter()));
                                MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
                                fetchSearch();
                                handled = true;
                            } else {
                                product.setError("Please Enter the Product Name");
                                handled = false;
                            }
                        }
                        return handled;
                    }
                });
                buttonDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        productName = product.getText().toString();
                        if (!productName.equalsIgnoreCase("")) {
                            customDialog.dismiss();
                            mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new LoaderAdapter()));
                            MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
                            fetchSearch();
                        } else {
                            product.setError("Please Enter the Product Name");
                        }
                    }
                });
                customDialog.show();
            }
        };

    }

    private void fetchSearch() {
        Api apiHandler = ((App) getActivity().getApplication()).getApiHandler();
        apiHandler.getShoppingResult(
                productName,
                new Callback<List<Search>>() {
                    @Override
                    public void success(List<Search> search, Response response) {
                        searchList = search;
                        Log.d(LOG_TAG, search.get(0).getCashBack() + "  " + search.get(0).getTitle() + "  " + search.get(0).getCashBack() + "  " + search.get(0).getCodAvailable() + "  " + search.get(0).getColor() + "  " + search.get(0).getEmiAvailable());
                        mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new ShoppingAdapter()));
                        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
                        ((SubCategoryFragment) getParentFragment()).getSearchFab().attachToRecyclerView(mRecyclerView);
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getParentFragment() != null) {
            if (isVisibleToUser) {
                ((SubCategoryFragment) getParentFragment()).getSearchFab().setVisibility(View.VISIBLE);
                ((SubCategoryFragment) getParentFragment()).getSearchFab().setOnClickListener(onSearchFABListener);
            } else {
                ((SubCategoryFragment) getParentFragment()).getSearchFab().setVisibility(View.GONE);
            }
        }
    }

    private void sendParseAnalytics(String success) {
        Map<String, String> params = new HashMap<>();
        params.put("success", success);
        params.put("product name", productName);
        ParseAnalytics.trackEventInBackground("shopping/search", params);
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

            if (searchList.get(i).getProductDescription() == null)
                viewHolder.productDescription.setText(searchList.get(i).getColor());
            else
                viewHolder.productDescription.setText(searchList.get(i).getProductDescription());

            if (Boolean.valueOf(searchList.get(i).getCodAvailable()))
                viewHolder.cod.setText("COD Available : Yes");
            else
                viewHolder.cod.setText("COD Available : No");

            Picasso.with(getActivity()).load(searchList.get(i).getImageUrls().get_200x200()).fit().centerCrop().into(viewHolder.icon);
//            Log.d("IMAGE URL : ", searchList.get(i).getImageUrls().get_400x400());

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
