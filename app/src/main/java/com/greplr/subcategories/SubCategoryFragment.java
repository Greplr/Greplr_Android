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
import com.greplr.MainActivity;
import com.greplr.R;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class SubCategoryFragment extends Fragment {


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

            ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeButtonEnabled(true);
        }
        ViewPager viewPager = matViewPager.getViewPager();
        SubCategoryPagerAdapter pagerAdapter = new SubCategoryPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        matViewPager.getPagerTitleStrip().setViewPager(viewPager);


        return rootView;
    }

    private class SubCategoryPagerAdapter extends FragmentPagerAdapter {

        public SubCategoryPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return getUnderSubFragments()[position];
        }

        @Override
        public int getCount() {
            return getUnderSubFragments().length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return ((UnderSubCategoryFragment)getUnderSubFragments()[position]).getPageTitle();
        }
    }

    public abstract Fragment[] getUnderSubFragments ();


}
