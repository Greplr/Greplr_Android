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
                                R.drawable.background_travel_cab,
                                R.drawable.cardicon_news_india)
                ),
                new UnderSubCategory(
                        "Global",
                        R.drawable.background_travel_bus,
                        R.drawable.cardicon_news_global,
                        NewsFeedFragment.newInstance("Global",
                                R.drawable.background_travel_bus,
                                R.drawable.cardicon_news_global)
                ),
                new UnderSubCategory(
                        "Technology",
                        R.drawable.background_travel_flight,
                        R.drawable.cardicon_news_technology,
                        NewsFeedFragment.newInstance("Technology",
                                R.drawable.background_travel_flight,
                                R.drawable.cardicon_news_technology)
                ),
                new UnderSubCategory(
                        "Sports",
                        R.drawable.background_travel_flight,
                        R.drawable.cardicon_news_sports,
                        NewsFeedFragment.newInstance("Sports",
                                R.drawable.background_travel_flight,
                                R.drawable.cardicon_news_sports)
                ),
                new UnderSubCategory(
                        "Entertainment",
                        R.drawable.background_travel_flight,
                        R.drawable.cardicon_news_entertainment,
                        NewsFeedFragment.newInstance("Entertainment",
                                R.drawable.background_travel_flight,
                                R.drawable.cardicon_news_entertainment)
                ),
                new UnderSubCategory(
                        "Business",
                        R.drawable.background_travel_flight,
                        R.drawable.cardicon_news_business,
                        NewsFeedFragment.newInstance("Business",
                                R.drawable.background_travel_flight,
                                R.drawable.cardicon_news_business)
                )
        };
    }

    @Override
    public int topCatNo() {
        return 3;
    }
}