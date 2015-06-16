package com.greplr.subcategories;


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

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.greplr.MainActivity;
import com.greplr.R;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class SubCategoryFragment extends Fragment {

    private ActionBar mActionBar;


    public SubCategoryFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.subcategories_fragment, container, false);

        ((MainActivity)getActivity()).getSupportActionBar().hide();

        MaterialViewPager matViewPager = (MaterialViewPager) rootView.findViewById(R.id.subcategory_viewpager);

        Toolbar toolbar = matViewPager.getToolbar();

        if (toolbar != null) {
            ((MainActivity)getActivity()).setSupportActionBar(toolbar);

            mActionBar= ((MainActivity)getActivity()).getSupportActionBar();
            mActionBar.setDisplayHomeAsUpEnabled(false);
            mActionBar.setDisplayUseLogoEnabled(false);
            mActionBar.setHomeButtonEnabled(false);
        }
        mActionBar.setTitle("");
        SubCategoryPagerAdapter pagerAdapter = new SubCategoryPagerAdapter(getChildFragmentManager());
        matViewPager.getViewPager().setAdapter(pagerAdapter);
        matViewPager.getViewPager().setOffscreenPageLimit(matViewPager.getViewPager().getAdapter().getCount());
        matViewPager.getPagerTitleStrip().setViewPager(matViewPager.getViewPager());

        return rootView;
    }

    private class SubCategoryPagerAdapter extends FragmentPagerAdapter {

        public SubCategoryPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return getUnderSubFragments(position);
        }

        @Override
        public int getCount() {
            return getUnderSubFragCount();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return ((UnderSubCategoryFragment)getItem(position)).getPageTitle();
        }
    }

    public abstract Fragment getUnderSubFragments (int pos);
    public abstract int getUnderSubFragCount();


}
