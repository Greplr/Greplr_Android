package com.greplr.subcategories.events;

import com.greplr.R;
import com.greplr.subcategories.SubCategoryFragment;
import com.greplr.subcategories.UnderSubCategory;

/**
 * Created by raghav on 21/06/15.
 */
public class EventFragment extends SubCategoryFragment {

    public static EventFragment newInstance() {
        return new EventFragment();
    }


    @Override
    public UnderSubCategory[] getUnderSubCategories() {
        return new UnderSubCategory[] {
                new UnderSubCategory(
                        "Movies",
                        R.drawable.background_travel_cab,
                        R.drawable.cardicon_travel_taxi,
                        EventMovieFragment.newInstance()
                )};
    }

    @Override
    public int topCatNo() {
        return 0;
    }
}
