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
        int[] backImgs = {
                R.drawable.main_background_1,
                R.drawable.main_background_2,
        };
        Random rGen = new Random();
        int backImageResource = backImgs[rGen.nextInt(backImgs.length)];
        Picasso.with(getActivity()).load(backImageResource).fit().centerCrop().into(backgroundImage);
        AnimationDrawable ad = (AnimationDrawable) ContextCompat.getDrawable(getActivity(),
                R.drawable.loader_icon);
        loaderIcon.setImageDrawable(ad);
        ad.start();


        return rootView;
    }


}
