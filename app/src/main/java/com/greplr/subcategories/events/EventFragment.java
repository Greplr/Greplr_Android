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

package com.greplr.subcategories.events;

import com.greplr.R;
import com.greplr.subcategories.SubCategoryFragment;
import com.greplr.subcategories.UnderSubCategory;

/**
 * Created by raghav on 21/06/15.
 *
 * The parent fragment of Events (Plays, Movies etc)
 */
public class EventFragment extends SubCategoryFragment {

    public static EventFragment newInstance() {
        return new EventFragment();
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
    public int topCatNo() {
        return 2;
    }
}
