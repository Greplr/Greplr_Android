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

package com.greplr.subcategories.news;

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
import com.greplr.api.NewsApi;
import com.greplr.models.news.Feed;
import com.greplr.subcategories.UnderSubCategoryFragment;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by prempalsingh on 29/6/15.
 */
public class NewsFeedFragment extends UnderSubCategoryFragment {

    public static final String LOG_TAG = "Greplr/News";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    public static NewsFeedFragment newInstance(String title, int background, int icon) {
        NewsFeedFragment fragment = new NewsFeedFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("background", background);
        args.putInt("icon", icon);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getFragmentIcon() {
        return getArguments().getInt("icon");
    }

    @Override
    public String getPageTitle() {
        return getArguments().getString("title");
    }

    @Override
    public int getBackgroundResId() {
        return getArguments().getInt("background");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Greplr", "NewsFeedFragment onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(
                R.id.recyclerview_news);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerViewMaterialAdapter(new NumberedAdapter(10));
        mRecyclerView.setAdapter(mAdapter);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

        NewsApi newsApiHandler = ((MainActivity) getActivity()).getNewsApiHandler();
        newsApiHandler.getNewsForTopic(
                "topic/India",
                20,
                null,
                null,
                new Callback<Feed>() {
                    @Override
                    public void success(Feed feed, Response response) {
                        Log.d(LOG_TAG, "success" + response.getUrl() + response.getStatus());
                        Log.d(LOG_TAG, feed.getId());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(LOG_TAG, "failure" + error.getUrl() + error.getMessage());

                    }
                }
        );


    }
}
