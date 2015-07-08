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

package com.greplr.subcategories.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greplr.MainActivity;
import com.greplr.R;
import com.greplr.subcategories.SubCategoryFragment;
import com.greplr.subcategories.UnderSubCategory;

/**
 * Created by prempalsingh on 29/6/15.
 */
public class NewsFragment extends SubCategoryFragment {

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }


    @Override
    public UnderSubCategory[] getUnderSubCategories() {
        return new UnderSubCategory[]{
                new UnderSubCategory(
                        "India",
                        R.drawable.background_travel_cab,
                        R.drawable.cardicon_news_india,
                        NewsFeedFragment.newInstance("India",
                                R.drawable.background_news_india,
                                R.drawable.cardicon_news_india,
                                "india")
                ),
                new UnderSubCategory(
                        "World",
                        R.drawable.background_travel_bus,
                        R.drawable.cardicon_news_world,
                        NewsFeedFragment.newInstance("World",
                                R.drawable.background_news_world,
                                R.drawable.cardicon_news_world,
                                "world")
                ),
                new UnderSubCategory(
                        "Technology",
                        R.drawable.background_travel_flight,
                        R.drawable.cardicon_news_technology,
                        NewsFeedFragment.newInstance("Technology",
                                R.drawable.background_news_technology,
                                R.drawable.cardicon_news_technology,
                                "tech")
                ),
                new UnderSubCategory(
                        "Sports",
                        R.drawable.background_travel_flight,
                        R.drawable.cardicon_news_sports,
                        NewsFeedFragment.newInstance("Sports",
                                R.drawable.background_news_sports,
                                R.drawable.cardicon_news_sports,
                                "sports")
                ),
                new UnderSubCategory(
                        "Bollywood",
                        R.drawable.background_travel_flight,
                        R.drawable.cardicon_news_bollywood,
                        NewsFeedFragment.newInstance("Bollywood",
                                R.drawable.background_news_bollywood,
                                R.drawable.cardicon_news_bollywood,
                                "bollywood")
                ),
                new UnderSubCategory(
                        "Business",
                        R.drawable.background_travel_flight,
                        R.drawable.cardicon_news_business,
                        NewsFeedFragment.newInstance("Business",
                                R.drawable.background_news_business,
                                R.drawable.cardicon_news_business,
                                "business+finance")
                )
        };
    }

    @Override
    public int topCatNo() {
        return 4;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).hideSlidePanel();
    }
}
