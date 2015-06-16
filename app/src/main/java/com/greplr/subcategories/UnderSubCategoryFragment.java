package com.greplr.subcategories;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by championswimmer on 15/6/15.
 */
public abstract class UnderSubCategoryFragment extends Fragment {


    public abstract int getFragmentIcon();
    public abstract String getPageTitle();
    public abstract int getBackgroundResId();


}
