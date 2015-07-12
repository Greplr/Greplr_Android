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

package com.greplr.topcategories;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.greplr.MainActivity;
import com.greplr.R;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class TopcategoriesFragment extends Fragment {

    public TopcategoriesFragment() {
    }

    public static TopcategoriesFragment newInstance() {
        return new TopcategoriesFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).getSupportActionBar().show();
        ((MainActivity) getActivity()).showSlidePanel();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).getSupportActionBar().show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.topcategories_fragment, container, false);
        RecyclerView categoryList = (RecyclerView) rootView.findViewById(R.id.recyclerview_main_categories);
        //KenBurnsView backgroundImage = (KenBurnsView) rootView.findViewById(R.id.topcategories_background);
        ImageView backgroundImage = ((MainActivity) getActivity()).getBackgroundImage();
//        int[] backImgs = {
//                R.drawable.main_background_1,
//                R.drawable.main_background_2,
//        };
//        Random rGen = new Random();
//        int backImageResource = backImgs[rGen.nextInt(backImgs.length)];
        Picasso.with(getActivity()).load(R.drawable.main_background).fit().centerCrop().into(backgroundImage);
        categoryList.setHasFixedSize(true);

        GridLayoutManager glm = new GridLayoutManager(getActivity(), 2);
        Point size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x;
        glm.setSpanCount(screenWidth / (getResources().getDimensionPixelOffset(R.dimen.column_width_main_recyclerview)));
        categoryList.setLayoutManager(glm);

        categoryList.setAdapter(new TopcategoriesAdapter(getActivity()));
        return rootView;
    }
}
