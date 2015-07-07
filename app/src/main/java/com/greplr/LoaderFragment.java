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
 *     You should immediately close your copy of code, and destory the file. You are not authorised to
 *     be in possession of this code or view or modify it or use it in any capacity.
 */

package com.greplr;


import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoaderFragment extends Fragment {


    public LoaderFragment() {
        // Required empty public constructor
    }

    public static LoaderFragment newInstance() {
        return new LoaderFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_loader, container, false);

        ImageView backgroundImage = (ImageView) rootView.findViewById(R.id.loader_container);
        ImageView loaderIcon = (ImageView) rootView.findViewById(R.id.loader_image);
//        int[] backImgs = {
//                R.drawable.main_background_1,
//                R.drawable.main_background_2,
//        };
//        Random rGen = new Random();
//        int backImageResource = backImgs[rGen.nextInt(backImgs.length)];
        Picasso.with(getActivity()).load(R.drawable.main_background).fit().centerCrop().into(backgroundImage);
        AnimationDrawable ad = (AnimationDrawable) ContextCompat.getDrawable(getActivity(),
                R.drawable.loader_icon);
        loaderIcon.setImageDrawable(ad);
        ad.start();


        return rootView;
    }


}
