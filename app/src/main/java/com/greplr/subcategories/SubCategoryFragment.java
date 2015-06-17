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
import android.widget.ImageView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.greplr.MainActivity;
import com.greplr.R;
import com.greplr.topcategories.Topcategories;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class SubCategoryFragment extends Fragment {

    private ActionBar mActionBar;
    private KenBurnsView backgroundImage;
    private ImageView headerLogo;


    public SubCategoryFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.subcategories_fragment, container, false);

        ((MainActivity)getActivity()).getSupportActionBar().hide();

        final MaterialViewPager matViewPager = (MaterialViewPager) rootView.findViewById(R.id.subcategory_viewpager);

        Toolbar toolbar = matViewPager.getToolbar();

        if (toolbar != null) {
            ((MainActivity)getActivity()).setSupportActionBar(toolbar);

            mActionBar= ((MainActivity)getActivity()).getSupportActionBar();
            mActionBar.setDisplayHomeAsUpEnabled(false);
            mActionBar.setDisplayUseLogoEnabled(false);
            mActionBar.setHomeButtonEnabled(false);
        }
        mActionBar.setTitle("");
        mActionBar.hide();
        backgroundImage = (KenBurnsView) rootView.findViewById(R.id.subcategory_background);

        final SubCategoryPagerAdapter pagerAdapter = new SubCategoryPagerAdapter(getChildFragmentManager());
        matViewPager.getViewPager().setAdapter(pagerAdapter);
        matViewPager.getViewPager().setOffscreenPageLimit(matViewPager.getViewPager().getAdapter().getCount());
        matViewPager.getPagerTitleStrip().setViewPager(matViewPager.getViewPager());
        matViewPager.setColor(getResources().getColor(getToolbarColorResId()), 500);

        headerLogo = (ImageView) matViewPager.findViewById(R.id.logoContainer).findViewById(R.id.subcategory_logo);

        matViewPager.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //Log.d("Greplr", "onPageSelected");
                int backImgRes = ((UnderSubCategoryFragment)pagerAdapter.getItem(position)).getBackgroundResId();
                Picasso.with(getActivity()).load(backImgRes).fit().centerInside().into(backgroundImage);
                headerLogo.setImageResource(((UnderSubCategoryFragment)pagerAdapter.getItem(position)).getFragmentIcon());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //Set the first under-sub fragment's background image by default
        int backImgRes = ((UnderSubCategoryFragment)pagerAdapter.getItem(0)).getBackgroundResId();
        Picasso.with(getActivity()).load(backImgRes).fit().centerInside().into(backgroundImage);

        headerLogo.setImageResource(((UnderSubCategoryFragment)pagerAdapter.getItem(0)).getFragmentIcon());


        return rootView;
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
            return ((UnderSubCategoryFragment)getItem(position)).getPageTitle();
        }
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



}
