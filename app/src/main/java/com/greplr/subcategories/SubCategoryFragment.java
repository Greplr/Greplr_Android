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

package com.greplr.subcategories;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.greplr.MainActivity;
import com.greplr.R;
import com.greplr.Route;
import com.greplr.common.utils.ColorUtils;
import com.greplr.topcategories.Topcategories;
import com.melnykov.fab.FloatingActionButton;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class SubCategoryFragment extends Fragment {

    public static final String LOG_TAG = "Greplr/SubCategory";

    public int topCatNo = 0;

    private ActionBar mActionBar;
    private ImageView backgroundImage;
    private ImageView headerLogo;
    private FloatingActionButton searchFab;
    private View backgroundOverlay;

    public SubCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_subcategories, container, false);

        try {
            ((MainActivity) getActivity()).getSupportActionBar().hide();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        final MaterialViewPager matViewPager = (MaterialViewPager) rootView.findViewById(R.id.subcategory_viewpager);
        searchFab = (FloatingActionButton) rootView.findViewById(R.id.search_fab);
        searchFab.setColorNormal(getCategoryColorDark());
        searchFab.setColorPressed(getCategoryColor());
        searchFab.setColorRipple(Color.GRAY);
        Toolbar toolbar = matViewPager.getToolbar();

        if (toolbar != null) {
            ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        }
        mActionBar = ((MainActivity) getActivity()).getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(false);
            mActionBar.setDisplayUseLogoEnabled(false);
            mActionBar.setHomeButtonEnabled(false);

            mActionBar.setTitle("");
            mActionBar.hide();
        }

        backgroundImage = ((MainActivity) getActivity()).getBackgroundImage();
        backgroundOverlay = rootView.findViewById(R.id.subcategory_background_overlay);
        backgroundOverlay.setBackgroundColor(
                ColorUtils.adjustAlpha(getCategoryColorDark(), 0.5f)
        );


        final SubCategoryPagerAdapter pagerAdapter = new SubCategoryPagerAdapter(getChildFragmentManager());
        matViewPager.getViewPager().setAdapter(pagerAdapter);
        matViewPager.getViewPager().setOffscreenPageLimit(matViewPager.getViewPager().getAdapter().getCount());
        matViewPager.getPagerTitleStrip().setViewPager(matViewPager.getViewPager());
        matViewPager.setColor(ColorUtils.adjustAlpha(getCategoryColor(), 0.3f), 500);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(getCategoryColor());
            getActivity().getWindow().setNavigationBarColor(getCategoryColor());
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

        if (((MainActivity) getActivity()).deepLinkUrl.contains("greplr.com")) {
            int deepSubCat = Route.
                    getSubcategoryPositionByRoute
                            (((MainActivity) getActivity()).deepLinkUrl);
            matViewPager.getViewPager().setCurrentItem(deepSubCat);
            ((MainActivity) getActivity()).deepLinkUrl = "";
        }

        return rootView;
    }

    public abstract UnderSubCategory[] getUnderSubCategories();

    public int getCatNum() {
        return topCatNo;
    }


    public int getCategoryColor() {
        return getResources().getColor(Topcategories.getTopCategories().get(getCatNum()).cardColor);
    }

    public abstract int getCategoryColorDark();

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
