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

package com.greplr.subcategories.events;

import com.greplr.R;
import com.greplr.subcategories.SubCategoryFragment;
import com.greplr.subcategories.UnderSubCategory;

/**
 * Created by raghav on 21/06/15.
 * <p/>
 * The parent fragment of Events (Plays, Movies etc)
 */
public class EventFragment extends SubCategoryFragment {


    public static EventFragment newInstance(int num) {
        EventFragment ef = new EventFragment();
        ef.topCatNo = num;
        return ef;
    }


    @Override
    public UnderSubCategory[] getUnderSubCategories() {
        return new UnderSubCategory[]{
                new UnderSubCategory(
                        "Movies",
                        R.drawable.background_events_movies,
                        R.drawable.cardicon_events_movies,
                        EventMovieFragment.newInstance()
                ),
                new UnderSubCategory(
                        "Plays",
                        R.drawable.background_events_play,
                        R.drawable.cardicon_events_plays,
                        EventPlayFragment.newInstance()
                )
        };
    }

    @Override
    public int getCategoryColorDark() {
        return getResources().getColor(R.color.events_color_dark);
    }

}
