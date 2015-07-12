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

package com.greplr.subcategories.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import com.greplr.api.NewsApi;
import com.greplr.models.news.Feed;
import com.greplr.subcategories.UnderSubCategoryFragment;
import com.parse.ParseAnalytics;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by prempalsingh on 29/6/15.
 */
public class NewsFeedFragment extends UnderSubCategoryFragment {

    public static final String LOG_TAG = "Greplr/News";

    private String newsTopics;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Feed.FeedItem> feedList;

    public static NewsFeedFragment newInstance(String title, int background, int icon, String topics) {
        NewsFeedFragment fragment = new NewsFeedFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("background", background);
        args.putInt("icon", icon);
        args.putString("topics", topics);
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

        ((MainActivity) getActivity()).hideSlidePanel();


        mRecyclerView = (RecyclerView) view.findViewById(
                R.id.recyclerview_news);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

        NewsApi newsApiHandler = ((App) getActivity().getApplication()).getNewsApiHandler();
        newsApiHandler.getNewsForTopic(
                "topic/" + getArguments().getString("topics"),
                20,
                null,
                null,
                new Callback<Feed>() {
                    @Override
                    public void success(Feed feed, Response response) {
                        Log.d(LOG_TAG, "success" + response.getUrl() + response.getStatus());
                        feedList = new ArrayList<>();
                        for (int i = 0; i < feed.getItems().size(); i++) {
                            if (feed.getItems().get(i).getVisual() != null &&
                                    feed.getItems().get(i).getSummary() != null &&
                                    feed.getItems().get(i).getContent() != null) {
                                feedList.add(feed.getItems().get(i));
                            }
                        }
                        Log.d(LOG_TAG, feedList.toString());
                        mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new FeedAdapter()));
                        Map<String, String> params = new HashMap<>();
                        params.put("success", "true");
                        ParseAnalytics.trackEventInBackground("news/search", params);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(LOG_TAG, "failure" + error.getUrl() + error.getMessage());
                        Map<String, String> params = new HashMap<>();
                        params.put("success", "false");
                        ParseAnalytics.trackEventInBackground("news/search", params);
                    }
                }
        );
    }

    public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            CardView v = (CardView) LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.cardview_news_list_item, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
            Picasso.with(getActivity()).load(feedList.get(i).getVisual().getUrl()).fit().centerCrop().into(viewHolder.newsVisual);
            String htmlString = feedList.get(i).getSummary().getContent();
            String htmlBody = htmlString.replaceAll("(<(/)img>)|(<img.+?>)", "");
            viewHolder.newsSummary.setText(Html.fromHtml(htmlBody));
            viewHolder.newsHeadline.setText(feedList.get(i).getTitle());
            viewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                    intent.putExtra("news", feedList.get(i).getContent().getContent());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return feedList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView newsHeadline;
            TextView newsSummary;
            ImageView newsVisual;
            View view;

            public ViewHolder(CardView v) {
                super(v);
                view = v;
                newsHeadline = (TextView) v.findViewById(R.id.news_headline);
                newsSummary = (TextView) v.findViewById(R.id.news_summary);
                newsVisual = (ImageView) v.findViewById(R.id.news_image);
            }
        }
    }
}
