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

package com.greplr.subcategories;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.greplr.MainActivity;
import com.greplr.R;
import com.greplr.common.utils.ColorUtils;
import com.greplr.topcategories.Topcategories;
import com.melnykov.fab.FloatingActionButton;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class SubCategoryFragment extends Fragment {

    public static final String LOG_TAG = "Greplr/SubCategory";

    private ActionBar mActionBar;
    private ImageView backgroundImage;
    private ImageView headerLogo;
    private FloatingActionButton searchFab;

    public SubCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_subcategories, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().hide();



        final MaterialViewPager matViewPager = (MaterialViewPager) rootView.findViewById(R.id.subcategory_viewpager);
        searchFab = (FloatingActionButton) rootView.findViewById(R.id.search_fab);
        Toolbar toolbar = matViewPager.getToolbar();

        if (toolbar != null) {
            ((MainActivity) getActivity()).setSupportActionBar(toolbar);

            mActionBar = ((MainActivity) getActivity()).getSupportActionBar();
            mActionBar.setDisplayHomeAsUpEnabled(false);
            mActionBar.setDisplayUseLogoEnabled(false);
            mActionBar.setHomeButtonEnabled(false);
        }
        mActionBar.setTitle("");
        mActionBar.hide();
        //backgroundImage = (ImageView) rootView.findViewById(R.id.subcategory_background);
        backgroundImage = ((MainActivity) getActivity()).getBackgroundImage();

        final SubCategoryPagerAdapter pagerAdapter = new SubCategoryPagerAdapter(getChildFragmentManager());
        matViewPager.getViewPager().setAdapter(pagerAdapter);
        matViewPager.getViewPager().setOffscreenPageLimit(matViewPager.getViewPager().getAdapter().getCount());
        matViewPager.getPagerTitleStrip().setViewPager(matViewPager.getViewPager());
        matViewPager.setColor(ColorUtils.adjustAlpha(getResources().getColor(getToolbarColorResId()), 0.3f), 500);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(getResources().getColor(getToolbarColorResId()));
            getActivity().getWindow().setNavigationBarColor(getResources().getColor(getToolbarColorResId()));
        }

        headerLogo = (ImageView) matViewPager.findViewById(R.id.logoContainer).findViewById(R.id.subcategory_logo);

        matViewPager.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Log.v(LOG_TAG, "position="+position+" positionOffset="+positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                //Log.d("Greplr", "onPageSelected");
                int backImgRes = ((UnderSubCategoryFragment) pagerAdapter.getItem(position)).getBackgroundResId();
                Picasso.with(getActivity()).load(backImgRes).fit().centerCrop().into(backgroundImage);
                headerLogo.setImageResource(((UnderSubCategoryFragment) pagerAdapter.getItem(position)).getFragmentIcon());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Log.v(LOG_TAG, "state="+state);
            }
        });

        //Set the first under-sub fragment's background image by default
        int backImgRes = ((UnderSubCategoryFragment) pagerAdapter.getItem(0)).getBackgroundResId();
        Picasso.with(getActivity()).load(backImgRes).fit().centerCrop().into(backgroundImage);

        headerLogo.setImageResource(((UnderSubCategoryFragment) pagerAdapter.getItem(0)).getFragmentIcon());


        return rootView;
    }

    public abstract UnderSubCategory[] getUnderSubCategories();

    public abstract int topCatNo();

    public int getToolbarColorResId() {
        return Topcategories.getTopCategories().get(topCatNo()).cardColor;
    }

    public int getUnderSubFragCount() {
        return getUnderSubCategories().length;
    }

    public Fragment getUnderSubFragment(int pos) {
        return getUnderSubCategories()[pos].frag;
    }

    private class SubCategoryPagerAdapter extends FragmentPagerAdapter {

        public SubCategoryPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return getUnderSubFragment(position);
        }

        @Override
        public int getCount() {
            return getUnderSubFragCount();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return ((UnderSubCategoryFragment) getItem(position)).getPageTitle();
        }
    }

    public FloatingActionButton getSearchFab() {
        return searchFab;
    }

}
